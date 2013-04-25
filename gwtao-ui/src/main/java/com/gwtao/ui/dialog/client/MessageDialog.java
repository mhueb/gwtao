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

import org.apache.commons.lang.NotImplementedException;
import org.shu4j.utils.message.IMessageSource;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;

public class MessageDialog {

  public static void confirm(String title, String message, final AsyncYESNOAnswere asyncYESNOAnswere) {
    final DialogBox dialogBox = new DialogBox();
    dialogBox.ensureDebugId("cwDialogBox");
    dialogBox.setText(title);
    dialogBox.setGlassEnabled(true);

    VerticalPanel dialogContents = new VerticalPanel();
    dialogBox.setWidget(dialogContents);

    // Create a table to layout the content
    HorizontalPanel info = new HorizontalPanel();
    info.setSpacing(4);
    dialogContents.add(info);

    // Image image = new Image(Showcase.images.jimmy());
    // dialogContents.add(image);
    // dialogContents.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);

    // Add some text to the top of the dialog
    HTML details = new HTML(message);
    dialogContents.add(details);
    dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);

    HorizontalPanel buttons = new HorizontalPanel();
    info.setSpacing(4);
    dialogContents.add(buttons);

    Button yesButton = new Button("Yes", new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        asyncYESNOAnswere.onYes();
      }
    });
    buttons.add(yesButton);

    Button noButton = new Button("No", new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        asyncYESNOAnswere.onNo();
      }
    });
    buttons.add(noButton);

    buttons.setCellHorizontalAlignment(yesButton, HasHorizontalAlignment.ALIGN_RIGHT);
    buttons.setCellHorizontalAlignment(noButton, HasHorizontalAlignment.ALIGN_RIGHT);

    dialogBox.center();
    dialogBox.show();
  }

  public static void alert(String title, String message, final AsyncOKAnswere answere) {
    final DialogBox dialogBox = new DialogBox();
    dialogBox.ensureDebugId("cwDialogBox");
    dialogBox.setText(title);
    dialogBox.setGlassEnabled(true);

    VerticalPanel dialogContents = new VerticalPanel();
    dialogBox.setWidget(dialogContents);

    // Create a table to layout the content
    HorizontalPanel info = new HorizontalPanel();
    info.setSpacing(4);
    dialogContents.add(info);

    // Image image = new Image(Showcase.images.jimmy());
    // dialogContents.add(image);
    // dialogContents.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);

    // Add some text to the top of the dialog
    HTML details = new HTML(message);
    dialogContents.add(details);
    dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);

    HorizontalPanel buttons = new HorizontalPanel();
    info.setSpacing(4);
    dialogContents.add(buttons);

    Button okButton = new Button("Ok", new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        answere.onOk();
      }
    });
    buttons.add(okButton);

    buttons.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_RIGHT);

    dialogBox.center();
    dialogBox.show();
  }

  public static void alert(String title, IMessageSource messages, AsyncOKAnswere answere) {
    throw new NotImplementedException();
  }

}
