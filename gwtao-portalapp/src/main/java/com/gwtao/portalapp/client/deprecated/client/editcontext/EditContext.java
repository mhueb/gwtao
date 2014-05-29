/* 
 * Copyright 2012 GWTAO
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
package com.gwtao.portalapp.client.deprecated.client.editcontext;

import org.shu4j.utils.exception.MessageException;
import org.shu4j.utils.message.IMessageReceiver;
import org.shu4j.utils.message.IMessageSource;
import org.shu4j.utils.message.Message;
import org.shu4j.utils.message.MessageLevel;
import org.shu4j.utils.message.MessageList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.portalapp.client.deprecated.client.i18n.ContextConstants;
import com.gwtao.ui.dialog.client.AsyncOkAnswere;
import com.gwtao.ui.dialog.client.AsyncOkCancelAnswere;
import com.gwtao.ui.dialog.client.MessageDialog;

/**
 * 
 * 
 * @param <T> Model this edit context works on
 */
public abstract class EditContext<T> extends AbstractEditContext<T> {
  private IEditContextOwner editor;
  private MessageList messages;
  private MessageList serverMessages;
  private boolean validate = true;

  protected abstract class AsyncEditCallback implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
      unmask();
      onServiceFailure(caught);
    }
  }

  /**
   * @param editor The editor ({@link IEditContextOwner}) this edit context is used in
   */
  public EditContext(IEditContextOwner editor) {
    this.editor = editor;
    this.messages = new MessageList();

    addStateProvider(new IEditStateProvider() {
      public boolean isDirty() {
        return false;
      }

      public boolean isValid() {
        if (wantValidateOnChange()) {
          validate(false);
          return !MessageLevel.isError(messages.getWorstLevel());
        }
        return true;
      }
    });
  }

  public void onServiceFailure(Throwable caught) {
    if (caught instanceof MessageException) {
      MessageException validateException = (MessageException) caught;
      serverMessages = new MessageList(validateException.getMessages());

      if (serverMessages != null && serverMessages.getWorstLevel() == MessageLevel.FATAL)
        setState(State.FAILURE, true);
      getStateListener().callOnServiceFailed();
      MessageDialog.alert(ContextConstants.c.serverValidateMessage(), validateException.getMessages(), AsyncOkAnswere.OK);
    }
    else {
      setState(State.FAILURE, true);
      serverMessages = new MessageList();
      serverMessages.add(new Message(ContextConstants.c.editorFailure(), MessageLevel.FATAL));
      getStateListener().callOnServiceFailed();
      UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
      if (uncaughtExceptionHandler != null)
        uncaughtExceptionHandler.onUncaughtException(caught);
    }

    fireDataChanged();
  }

  /**
   * Masks underlying panels while e.g. loading the model data for this edit context This is called for the
   * {@link #checkIn}, {@link #checkOut}, {@link #doRefresh} and {@link #doRevert} automatically
   * 
   * @see {@link #getMaskPanel}
   * @see {@link #unmask}
   * 
   * @param msg The message to show during masking the underlying panels
   */
  protected void mask(String msg) {
    this.editor.getMask().setMessage(SafeHtmlUtils.fromString(msg));
    this.editor.getMask().show();
  }

  /**
   * @see {@link #getMaskPanel}
   * @see {@link #mask}
   */
  protected void unmask() {
    this.editor.getMask().hide();
  }

  /**
   * Calls the {@link #asyncStart} with the given parameter
   */
  public void start(Object parameter) {
    // WaitCursor.show();
    serverMessages = null;
    getStateListener().callOnStartPre();
    asyncStart(parameter, new AsyncEditCallback() {
      public void onSuccess(T result) {
        setDataSilent(result);
        setState(getStartState());
        onSetData();
        getStateListener().callOnStartPost();
        // WaitCursor.unshow();
      }

      @Override
      public void onFailure(Throwable arg0) {
        super.onFailure(arg0);
        // WaitCursor.unshow();
      }
    });
  }

  protected State getStartState() {
    return supportsEditState() && (isNew() || !wantExplicitCheckOut()) ? State.EDIT : State.VIEW;
  }

  /**
   * Called from {@link #start}
   * 
   * @param parameter The parameter given from the start call
   * @param editCallback The callback that handles the success or failure state of the start call
   */
  protected abstract void asyncStart(Object parameter, AsyncCallback<T> editCallback);

  /**
   * Calls the async checkin callback of your edit context implementation.
   */
  public void checkIn() {
    updateState();
    if (isDirty()) {
      boolean valid = isValid();
      IMessageSource msgs = validate(true);
      getStateListener().callOnValidatePost(msgs);
      valid &= !MessageLevel.isError(msgs.getWorstLevel());
      if (valid)
        doCheckIn();
      else
        MessageDialog.alert(ContextConstants.c.save(), ContextConstants.c.validateErrorsOnSave(), AsyncOkAnswere.OK);
    }
    else
      MessageDialog.alert(ContextConstants.c.save(), ContextConstants.c.nothingToSave(), AsyncOkAnswere.OK);
  }

  private void doCheckIn() {
    mask(ContextConstants.c.saving());
    serverMessages = null;
    getStateListener().callCheckInPre();
    asyncCheckIn(new AsyncEditCallback() {
      public void onSuccess(T result) {
        unmask();
        setDataSilent(result);
        resetFlags();
        setState(getDefaultState());
        onSetData();
        getStateListener().callCheckInPost();
      }
    });
  }

  public IMessageSource getMessages() {
    return messages;
  }

  public IMessageSource validate(boolean checkin) {
    if (validate) {
      try {
        validate = false;
        this.messages.clear();
        doValidate(this.messages, checkin);
        updateState();
      }
      finally {
        validate = true;
      }
    }
    return this.messages;
  }

  /**
   * Validiert die in diesem Kontext enthaltenen Daten. Fügt man zu dem übergebenen {@link IMessageReceiver}
   * eine {@link Message} mit dem Level {@link MessageLevel#ERROR} oder {@link MessageLevel#FATAL} (siehe dazu
   * auch {@link MessageLevel#isError}) hinzu, so wird der Speicherablauf unterbrochen und das
   * {@link #checkIn} nicht weiter ausgeführt. Warnungen und Hinweise werden beim Speichern nur abgezeigt,
   * brechen aber den Vorgang nicht ab
   * 
   * @param recv {@link IMessageReceiver} der die Ergebnisse der Validierung in Form von Nachrichten mit
   *          zugewiesenen {@link MessageLevel}n erwartet.
   * @param checkin
   */
  protected void doValidate(IMessageReceiver recv, boolean checkin) {
  }

  public IMessageSource getServerMessages() {
    return serverMessages;
  }

  protected State getDefaultState() {
    return wantExplicitCheckOut() ? State.VIEW : State.EDIT;
  }

  protected abstract void asyncCheckIn(AsyncCallback<T> editCallback);

  public void checkOut() {
    mask(ContextConstants.c.loading());
    serverMessages = null;
    getStateListener().callCheckOutPre();
    asyncCheckOut(new AsyncEditCallback() {
      public void onSuccess(T result) {
        unmask();
        setDataSilent(result);
        setState(wantExplicitCheckOut() ? State.EDIT : State.VIEW);
        onSetData();
        getStateListener().callCheckOutPost();
      }
    });
  }

  protected abstract void asyncCheckOut(AsyncCallback<T> editCallback);

  public void revert() {
    if (isDirty()) {
      MessageDialog.confirm(ContextConstants.c.revert(), ContextConstants.c.revertQuestion(), new AsyncOkCancelAnswere() {
        @Override
        public void onOk() {
          doRevert();
        }
      });
    }
    else {
      doRevert();
    }
  }

  protected void doRevert() {
    mask(ContextConstants.c.reverting());
    serverMessages = null;
    getStateListener().callRevertPre();
    asyncRevert(new AsyncEditCallback() {
      public void onSuccess(T result) {
        unmask();
        setDataSilent(result);
        setState(getDefaultState());
        onSetData();
        getStateListener().callRevertPost();
      }
    });
  }

  protected void asyncRevert(AsyncCallback<T> editCallback) {
    asyncRefresh(editCallback);
  }

  public void refresh() {
    if (isDirty()) {
      MessageDialog.confirm(ContextConstants.c.refresh(), ContextConstants.c.refreshQuestion(), new AsyncOkCancelAnswere() {
        @Override
        public void onOk() {
          doRefresh();
        }
      });
    }
    else
      doRefresh();
  }

  public void doRefresh() {
    mask(ContextConstants.c.refreshing());
    serverMessages = null;
    getStateListener().callRefreshPre();
    asyncRefresh(new AsyncEditCallback() {
      public void onSuccess(T result) {
        unmask();
        setData(result);
        getStateListener().callRefreshPost();
      }
    });
  }

  protected void asyncRefresh(AsyncCallback<T> editCallback) {
    asyncCheckOut(editCallback);
  }
}
