package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.Validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.data.client.source.AbstractDataSource;
import com.gwtao.ui.location.client.IParameterConverter;
import com.gwtao.ui.task.client.events.TaskExecEvent;
import com.gwtao.ui.task.client.events.TaskRefreshEvent;
import com.gwtao.ui.task.client.events.TaskStartEvent;
import com.gwtao.ui.task.client.i18n.DataConstants;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.mask.IWaitMask;
import com.gwtao.ui.util.client.mask.WaitMask;

public abstract class AbstractTaskController<P, M> extends AbstractDataSource<M> implements ITaskController<P, M> {
  private final static DataConstants c = GWT.create(DataConstants.class);

  protected abstract class Callback<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
      onServiceFailure(caught);
    }
  }

  public static enum State {
    INIT,
    LOADING,
    PERFORMING,
    REFRESHING,
    VIEW,
    EDIT,
    FAILED
  }

  private State state;
  private State initialState;
  private Validator validator;
  private P params;
  private M model;
  private IWaitMask mask;
  private ITaskView view;
  private IParameterConverter<P, M> converter;
  private IAsyncDataReader<P, M> reader;
  private IAsyncTaskPerformer<M> performer;

  protected AbstractTaskController(IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    this.converter = converter;
    this.reader = reader;
    this.performer = performer;
    this.initialState = State.EDIT;
    this.state = State.INIT;
  }

  public void setEditOnStart(boolean editOnStart) {
    Validate.isTrue(this.state == State.INIT);
    this.initialState = editOnStart ? State.EDIT : State.VIEW;
  }

  public void initValidator(javax.validation.Validator validator) {
    Validate.isTrue(this.state == State.INIT);
    this.validator = validator;
  }

  public void initView(ITaskView view) {
    Validate.isTrue(this.state == State.INIT);
    this.view = view;
  }

  @Override
  public Validator getValidator() {
    return validator;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public abstract boolean isDirty();

  private void mask(String string) {
    if (mask == null)
      mask = new WaitMask(view.asWidget());
    mask.show(string);
  }

  private void unmask() {
    if (mask != null) {
      mask.hide();
      mask = null;
    }
  }

  @Override
  public M getData() {
    return this.model;
  }

  @Override
  public void start(P param) {
    Validate.isTrue(this.state == State.INIT);
    this.state = State.LOADING;
    this.params = param;
    fireEvent(new TaskStartEvent(true));
    mask(AbstractTaskController.c.loading());
    reader.read(params, new Callback<M>() {
      @Override
      public void onSuccess(M result) {
        unmask();
        state = initialState;
        edit(result);
        fireEvent(new TaskStartEvent(false));
      }
    });
  }

  protected abstract M flush();

  protected abstract boolean hasErrors();

  @Override
  public boolean validateAndFlush() {
    if (isDirty()) {
      model = flush();
      if (!hasErrors() && validate(model)) {
        return true;
      }
      else {
        handleDriverErrors();
        view.alert(AbstractTaskController.c.save(), AbstractTaskController.c.validateErrors(), AsyncOKAnswere.OK);
        return false;
      }
    }
    else {
      return true;
    }
  }

  @Override
  public void perform() {
    perform(performer);
  }

  public void perform(IAsyncTaskPerformer<M> performer) {
    Validate.isTrue(this.state == State.EDIT);
    if (isDirty()) {
      fireEvent(new TaskExecEvent(true));
      model = flush();
      if (!hasErrors() && validate(model)) {
        this.state = State.PERFORMING;
        mask(performer.getWaitMessage());
        AsyncCallback<M> callback = new Callback<M>() {
          @Override
          public void onSuccess(M result) {
            unmask();
            edit(result);
            state = State.EDIT;
            fireEvent(new TaskExecEvent(false));
          }

          @Override
          public void onFailure(Throwable caught) {
            if (caught instanceof ConstraintViolationException) {
              ConstraintViolationException ex = (ConstraintViolationException) caught;
              setConstraintViolations(ex.getConstraintViolations());
              handleDriverErrors();
              state = initialState;
              fireEvent(new TaskExecEvent(false));
            }
            else
              super.onFailure(caught);
          }
        };
        performer.perform(model, callback);
      }
      else {
        handleDriverErrors();
        fireEvent(new TaskExecEvent(false));
        view.alert(performer.getDisplayTitle(), AbstractTaskController.c.validateErrors(), AsyncOKAnswere.OK);
      }
    }
    else
      view.alert(performer.getDisplayTitle(), AbstractTaskController.c.nothingChanged(), AsyncOKAnswere.OK);
  }

  protected abstract void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations);

  @Override
  public void refresh() {
    if (isDirty()) {
      view.confirm(AbstractTaskController.c.refresh(), AbstractTaskController.c.refreshQuestion(), new AsyncYESNOAnswere() {
        @Override
        public void onYes() {
          doRefresh();
        }
      });
    }
    else
      doRefresh();
  }

  private void doRefresh() {
    fireEvent(new TaskRefreshEvent(true));
    mask(AbstractTaskController.c.reverting());
    final State old = state;
    state = State.REFRESHING;
    reader.read(params, new Callback<M>() {
      @Override
      public void onSuccess(M result) {
        unmask();
        edit(result);
        state = old;
        fireEvent(new TaskRefreshEvent(false));
      }
    });
  }

  @SuppressWarnings({
      "rawtypes",
      "unchecked" })
  private boolean validate(M model) {
    if (validator != null) {
      Set<ConstraintViolation<M>> violations = validator.validate(model);
      if (!violations.isEmpty()) {
        setConstraintViolations((Set) violations);
        return false;
      }
    }
    return true;
  }

  public void edit(M model) {
    this.model = model;
    this.params = converter.extract(model);
    onEdit();
    notifyChange();
  }

  protected abstract void onEdit();

  public void onServiceFailure(Throwable caught) {
    unmask();
    state = State.FAILED;
    view.alert("Service Failure", caught.getMessage(), AsyncOKAnswere.OK);
    notifyChange();
  }

  protected void handleDriverErrors() {
    // TODO
  }
}
