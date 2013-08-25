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

import org.shu4j.utils.message.IMessageSource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.dialog.client.i18n.DialogConstants;
import com.gwtao.ui.util.client.DisplayableItem;
import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;

public class MessageDialog {

  private static final DialogConstants TEXTS = GWT.create(DialogConstants.class);

  private MessageDialog() {
  }

  public static void confirm(String title, String message, final AsyncOkCancelAnswere answere) {
    confirm(title, message, new DisplayableItem(TEXTS.ok()), answere);
  }

  public static void confirm(String title, String message, IDisplayableItem okInfo, final AsyncOkCancelAnswere asyncCallback) {
    ModalDialog dialog = new ModalDialog();
    dialog.setTitle(title);
    dialog.setWidget(makeWidget(message));
    dialog.addAction(createOkAction(dialog, okInfo, asyncCallback));
    dialog.addAction(createCancelAction(dialog, asyncCallback));
    dialog.show();
  }

  private static IsWidget makeWidget(String message) {
    // HorizontalPanel info = new HorizontalPanel();
    // info.setSpacing(4);
    // Image image = new Image(Showcase.images.jimmy());
    // dialogContents.add(image);
    // dialogContents.setCellHorizontalAlignment(image,
    // HasHorizontalAlignment.ALIGN_CENTER);
    return new HTML(message);
  }

  public static void alert(String title, String message, final AsyncOkAnswere answere) {
    ModalDialog dialog = new ModalDialog();
    dialog.setTitle(title);
    dialog.setWidget(makeWidget(message));
    dialog.addAction(createOkAction(dialog, new DisplayableItem(TEXTS.ok()), answere));
    dialog.show();
  }

  private static IAction createCancelAction(final ModalDialog dialog, final AsyncOkCancelAnswere answere) {
    return new Action(new DisplayableItem(TEXTS.cancel())) {

      @Override
      public void execute(Object... data) {
        dialog.hide();
        answere.onCancel();
      }
    };
  }

  private static IAction createOkAction(final ModalDialog dialog, IDisplayableItem okText, final AsyncOkAnswere asyncCallback) {
    return new Action(okText) {

      @Override
      public void execute(Object... data) {
        dialog.hide();
        asyncCallback.onOk();
      }
    };
  }

  public static void alert(String serverValidateMessage, IMessageSource messages, AsyncOkAnswere ok) {
    alert(serverValidateMessage, messages.toString(), ok);
  }
}
