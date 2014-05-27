/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.Validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.data.client.source.AbstractDataSource;
import com.gwtao.ui.dialog.client.AsyncOkAnswere;
import com.gwtao.ui.dialog.client.AsyncOkCancelAnswere;
import com.gwtao.ui.place.client.IParameterConverter;
import com.gwtao.ui.task.client.events.TaskExecEvent;
import com.gwtao.ui.task.client.events.TaskRefreshEvent;
import com.gwtao.ui.task.client.events.TaskStartEvent;
import com.gwtao.ui.task.client.i18n.DataConstants;
import com.gwtao.ui.util.client.displayable.IDisplayableItem;
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

  public IDisplayableItem getPerformerTitle() {
    return performer;
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

    mask.setMessage(SafeHtmlUtils.fromString(string));
    mask.show();
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
        view.alert(AbstractTaskController.c.save(), AbstractTaskController.c.validateErrors(), AsyncOkAnswere.OK);
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
        view.alert(performer.getDisplayText(), AbstractTaskController.c.validateErrors(), AsyncOkAnswere.OK);
      }
    }
    else
      view.alert(performer.getDisplayText(), AbstractTaskController.c.nothingChanged(), AsyncOkAnswere.OK);
  }

  protected abstract void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations);

  @Override
  public void refresh() {
    if (isDirty()) {
      view.confirm(AbstractTaskController.c.refresh(), AbstractTaskController.c.refreshQuestion(), new AsyncOkCancelAnswere() {
        @Override
        public void onOk() {
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
    fireDataChanged();
  }

  protected abstract void onEdit();

  public void onServiceFailure(Throwable caught) {
    unmask();
    state = State.FAILED;
    view.alert("Service Failure", caught.getMessage(), AsyncOkAnswere.OK);
    fireDataChanged();
  }

  protected void handleDriverErrors() {
    // TODO
  }
}
