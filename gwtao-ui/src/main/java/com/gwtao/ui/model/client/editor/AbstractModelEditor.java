package com.gwtao.ui.model.client.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.Validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.model.client.editor.events.EditorRefreshEvent;
import com.gwtao.ui.model.client.editor.events.EditorRemoveEvent;
import com.gwtao.ui.model.client.editor.events.EditorRevertEvent;
import com.gwtao.ui.model.client.editor.events.EditorSaveEvent;
import com.gwtao.ui.model.client.editor.events.EditorStartEvent;
import com.gwtao.ui.model.client.i18n.ModelConstants;
import com.gwtao.ui.model.client.source.AbstractModelSource;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.GlassPane;
import com.gwtao.ui.util.client.ParameterList;

public abstract class AbstractModelEditor<M> extends AbstractModelSource<M> implements IModelEditor<M> {
  private final static ModelConstants c =   GWT.create(ModelConstants.class);

  
  protected abstract class Callback<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
      onServiceFailure(caught);
    }
  }

  public static enum State {
    INIT,
    LOADING,
    SAVING,
    REMOVING,
    REVERTING,
    REFRESHING,
    VIEW,
    EDIT,
    REMOVED,
    FAILED
  }

  private State state;
  private State initialState;
  private Validator validator;
  private ParameterList params;
  private M model;
  private GlassPane glass;
  private final IServiceAdapter<M> service;
  private IEditorView view;

  protected AbstractModelEditor(IServiceAdapter<M> service) {
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

  public void initView(IEditorView view) {
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
  public boolean isCreate() {
    return service.isCreate(params);
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
  public M getModel() {
    return this.model;
  }

  @Override
  public void start(ParameterList param) {
    Validate.isTrue(this.state == State.INIT);
    this.state = State.LOADING;
    this.params = param;
    fireEvent(new EditorStartEvent(true));
    mask(AbstractModelEditor.c.loading());
    service.read(params, new Callback<M>() {
      @Override
      public void onSuccess(M result) {
        unmask();
        state = initialState;
        edit(result);
        fireEvent(new EditorStartEvent(false));
      }
    });
  }

  protected abstract M flush();

  protected abstract boolean hasErrors();

  @Override
  public void save() {
    Validate.isTrue(this.state == State.EDIT);
    if (isDirty()) {
      fireEvent(new EditorSaveEvent(true));
      model = flush();
      if (!hasErrors() && validate(model)) {
        this.state = State.SAVING;
        mask(AbstractModelEditor.c.saving());
        AsyncCallback<M> callback = new Callback<M>() {
          @Override
          public void onSuccess(M result) {
            unmask();
            edit(result);
            state = State.EDIT;
            fireEvent(new EditorSaveEvent(false));
          }

          @Override
          public void onFailure(Throwable caught) {
            if (caught instanceof ConstraintViolationException) {
              ConstraintViolationException ex = (ConstraintViolationException) caught;
              setConstraintViolations(ex.getConstraintViolations());
              handleDriverErrors();
              state = initialState;
              fireEvent(new EditorSaveEvent(false));
            }
            else
              super.onFailure(caught);
          }
        };
        if (isCreate())
          service.create(model, callback);
        else
          service.update(model, callback);
      }
      else {
        handleDriverErrors();
        fireEvent(new EditorSaveEvent(false));
        view.alert(AbstractModelEditor.c.save(), AbstractModelEditor.c.validateErrorsOnSave(), AsyncOKAnswere.OK);
      }
    }
    else
      view.alert(AbstractModelEditor.c.save(), AbstractModelEditor.c.nothingToSave(), AsyncOKAnswere.OK);
  }

  protected abstract void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations);

  @Override
  public void refresh() {
    if (isDirty()) {
      view.confirm(AbstractModelEditor.c.refresh(), AbstractModelEditor.c.refreshQuestion(), new AsyncYESNOAnswere() {
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
    fireEvent(new EditorRefreshEvent(true));
    mask(AbstractModelEditor.c.reverting());
    final State old = state;
    state = State.REFRESHING;
    service.read(params, new Callback<M>() {
      @Override
      public void onSuccess(M result) {
        unmask();
        edit(result);
        state = old;
        fireEvent(new EditorRefreshEvent(false));
      }
    });
  }

  @Override
  public void revert() {
    Validate.isTrue(this.state == State.EDIT);
    if (isDirty()) {
      view.confirm(AbstractModelEditor.c.revert(), AbstractModelEditor.c.revertQuestion(), new AsyncYESNOAnswere() {
        @Override
        public void onYes() {
          state = State.REVERTING;
          fireEvent(new EditorRevertEvent(true));
          mask(AbstractModelEditor.c.reverting());
          service.read(params, new Callback<M>() {
            @Override
            public void onSuccess(M result) {
              unmask();
              edit(result);
              state = initialState;
              fireEvent(new EditorRevertEvent(false));
            }
          });
        }
      });
    }
    else {
      this.state = initialState;
      fireEvent(new EditorRevertEvent(true));
    }

  }

  @Override
  public void remove() {
    Validate.isTrue(this.state == State.VIEW || this.state == State.EDIT);
    if (isDirty()) {
      view.confirm(AbstractModelEditor.c.remove(), AbstractModelEditor.c.removeQuestion(), new AsyncYESNOAnswere() {
        @Override
        public void onYes() {
          doRemove();
        }
      });
    }
    else {
      doRemove();
    }

  }

  private void doRemove() {
    state = State.REMOVING;
    fireEvent(new EditorRemoveEvent(true));
    mask(AbstractModelEditor.c.reverting());
    service.remove(model, new Callback<Void>() {
      @Override
      public void onSuccess(Void result) {
        unmask();
        state = State.REMOVED;
        fireEvent(new EditorRemoveEvent(false));
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
