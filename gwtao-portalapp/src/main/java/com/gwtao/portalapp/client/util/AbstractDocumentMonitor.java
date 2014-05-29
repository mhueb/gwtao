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
package com.gwtao.portalapp.client.util;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.gwtao.portalapp.client.deprecated.client.editcontext.EditContextListenerAdapter;
import com.gwtao.portalapp.client.deprecated.client.editcontext.IEditContextListener;
import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.document.IDocumentEditor;
import com.gwtao.portalapp.client.document.IDocumentSelector;
import com.gwtao.ui.data.client.selection.IDataSelection;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;

public abstract class AbstractDocumentMonitor {
  private final Timer selectTimer = new Timer() {
    @Override
    public void run() {
      if (lastDoc instanceof IDocumentSelector) {
        IDataSelection selectionContext = ((IDocumentSelector) lastDoc).getSelectionContext();
        if (selectionContext.getData().length == 1) {
          Object data = selectionContext.getData()[0];
          handleDataSwitch(data);
        }
        else
          handleNoData(true);
      }
      else if (lastDoc instanceof IDocumentEditor) {
        IDocumentEditor edtdoc = (IDocumentEditor) lastDoc;
        Object data = edtdoc.getEditContext().getData();
        handleDataSwitch(data);
      }
      else {
        handleNoData(false);
      }
    }
  };

  private final DataChangedEvent.Handler selectorListener = new DataChangedEvent.Handler() {
    @Override
    public void onDataChanged() {
      selectTimer.cancel();
      selectTimer.schedule(250);
    }
  };

  private final IEditContextListener editorListener = new EditContextListenerAdapter() {
    @Override
    public void onSetData() {
      selectTimer.cancel();
      selectTimer.schedule(250);
    }
  };

  private IDocument lastDoc;

  private HandlerRegistration addHandler;

  public void onDocumentSwitch(IDocument doc) {
    if (lastDoc != null) {
      if (lastDoc instanceof IDocumentSelector) {
        if (addHandler != null) {
          addHandler.removeHandler();
          addHandler = null;
        }
      }
      else if (lastDoc instanceof IDocumentEditor)
        ((IDocumentEditor) lastDoc).getEditContext().removeStateListener(editorListener);
    }
    lastDoc = doc;
    if (lastDoc != null && doc instanceof IDocumentEditor) {
      IDocumentEditor edtdoc = (IDocumentEditor) doc;
      edtdoc.getEditContext().addStateListener(editorListener);
      editorListener.onSetData();
    }
    else if (lastDoc instanceof IDocumentSelector) {
      IDocumentSelector seldoc = (IDocumentSelector) lastDoc;
      addHandler = seldoc.getSelectionContext().addHandler(DataChangedEvent.TYPE, selectorListener);
      selectorListener.onDataChanged();
    }
    else
      handleNoData(false);
  }

  protected abstract void handleNoData(boolean selector);

  protected abstract void handleDataSwitch(Object data);
}
