/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.ui.portal.client.util;

import com.google.gwt.user.client.Timer;
import com.gwtao.ui.context.client.datacontext.IDataChangeListener;
import com.gwtao.ui.context.client.editcontext.EditContextListenerAdapter;
import com.gwtao.ui.context.client.editcontext.IEditContextListener;
import com.gwtao.ui.context.client.selectioncontext.ISelectionContext;
import com.gwtao.ui.portal.client.document.IDocument;
import com.gwtao.ui.portal.client.document.IDocumentEditor;
import com.gwtao.ui.portal.client.document.IDocumentSelector;

public abstract class AbstractDocumentMonitor {
  private final Timer selectTimer = new Timer() {
    @Override
    public void run() {
      if (lastDoc instanceof IDocumentSelector) {
        ISelectionContext selectionContext = ((IDocumentSelector) lastDoc).getSelectionContext();
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

  private final IDataChangeListener selectorListener = new IDataChangeListener() {
    @Override
    public void onDataChange() {
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

  public void onDocumentSwitch(IDocument doc) {
    if (lastDoc != null) {
      if (lastDoc instanceof IDocumentSelector)
        ((IDocumentSelector) lastDoc).getSelectionContext().removeChangeListener(selectorListener);
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
      seldoc.getSelectionContext().addChangeListener(selectorListener);
      selectorListener.onDataChange();
    }
    else
      handleNoData(false);
  }

  protected abstract void handleNoData(boolean selector);

  protected abstract void handleDataSwitch(Object data);
}
