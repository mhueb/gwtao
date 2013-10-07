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
package com.gwtao.ui.dialog.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.widgets.client.ToolPanel;

public class ModalDialog {
  private DialogBox dialogBox;

  private VerticalPanel back;

  private ToolPanel buttons;

  private IPopupHandler ph = new IPopupHandler() {
    @Override
    public void show() {
      dialogBox.show();
    }

    @Override
    public void hide() {
      dialogBox.hide();
    }

    @Override
    public String canHide() {
      return null;
    }
  };

  public ModalDialog() {
    dialogBox = new DialogBox();
    dialogBox.ensureDebugId("cwDialogBox");
    dialogBox.setGlassEnabled(true);
    // dialogBox.setModal(true);

    back = new VerticalPanel();
    dialogBox.setWidget(back);

    buttons = new ToolPanel();
    back.add(buttons);

    dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {

      @Override
      public void onClose(CloseEvent<PopupPanel> event) {
        PopupManager.get().removeHandler(ph);
      }
    });

    PopupManager.get().addHandler(ph);
  }

  public void setTitle(String title) {
    dialogBox.setText(title);
  }

  public void setWidget(IsWidget content) {
    if (dialogBox.isShowing())
      throw new IllegalStateException("not allowed while showing");
    back.insert(content, 0);
  }

  public void addAction(IAction action) {
    if (dialogBox.isShowing())
      throw new IllegalStateException("not allowed while showing");
    buttons.add(action);
  }

  public void addAction(IAction action, IDataSource<?> source) {
    if (dialogBox.isShowing())
      throw new IllegalStateException("not allowed while showing");
    buttons.add(action, source);
  }

  public void show() {
    if (!dialogBox.isShowing()) {
      dialogBox.center();
      dialogBox.show();
    }
  }

  public void hide() {
    dialogBox.hide();
  }
}
