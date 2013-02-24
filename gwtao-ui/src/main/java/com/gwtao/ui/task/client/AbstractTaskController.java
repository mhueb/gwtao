package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.Validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.data.client.source.AbstractDataSource;
import com.gwtao.ui.task.client.events.TaskExecEvent;
import com.gwtao.ui.task.client.events.TaskRefreshEvent;
import com.gwtao.ui.task.client.events.TaskStartEvent;
import com.gwtao.ui.task.client.i18n.DataConstants;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.GlassPane;
import com.gwtao.ui.util.client.ParameterList;

public abstract class AbstractTaskController<M> extends AbstractDataSource<M> implements ITaskController<M> {
  private final static DataConstants c = GWT.create(DataConstants.class);

  protected abstract class Callback<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
      onServiceFailure(caught);
    }
  }

  public static enum State {
    INIT,
    LOADING,
    EXECUTING,
    REFRESHING,
    VIEW,
    EDIT,
    FAILED
  }

  private State state;
  private State initialState;
  private Validator validator;
  private ParameterList params;
  private M model;
  private GlassPane glass;
  private final IServiceAdapter<M> service;
  private ITaskView view;

  protected AbstractTaskController(IServiceAdapter<M> service) {
    this.service = service;
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
    if (glass == null)
      glass = GlassPane.create(view.asWidget());
  }

  private void unmask() {
    if (glass != null) {
      glass.remove();
      glass = null;
    }
  }

  @Override
  public M getData() {
    return this.model;
  }

  @Override
  public void start(ParameterList param) {
    Validate.isTrue(this.state == State.INIT);
    this.state = State.LOADING;
    this.params = param;
    fireEvent(new TaskStartEvent(true));
    mask(AbstractTaskController.c.loading());
    service.read(params, new Callback<M>() {
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
  public void execute() {
    Validate.isTrue(this.state == State.EDIT);
    if (isDirty()) {
      fireEvent(new TaskExecEvent(true));
      model = flush();
      if (!hasErrors() && validate(model)) {
        this.state = State.EXECUTING;
        mask(AbstractTaskController.c.saving());
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
        service.execute(model, callback);
      }
      else {
        handleDriverErrors();
        fireEvent(new TaskExecEvent(false));
        view.alert(AbstractTaskController.c.save(), AbstractTaskController.c.validateErrorsOnSave(), AsyncOKAnswere.OK);
      }
    }
    else
      view.alert(AbstractTaskController.c.save(), AbstractTaskController.c.nothingToSave(), AsyncOKAnswere.OK);
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
    service.read(params, new Callback<M>() {
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

  private void edit(M model) {
    this.model = model;
    this.params = service.toParam(model);
    onEdit();
    notifyChange();
  }

  protected abstract void onEdit();

  public void onServiceFailure(Throwable caught) {
    unmask();
    state = State.FAILED;
  }

  protected void handleDriverErrors() {
    // TODO
  }
}
