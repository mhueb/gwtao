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
package com.gwtao.ui.richtext.client;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RichTextArea;

public class RichTextAreaEx extends Composite {
  private final RichTextToolbar toolbar;
  private final RichTextArea richtext;
  private DockPanel panel;
  private boolean readonly;

  public RichTextAreaEx() {
    richtext = new RichTextArea() {
      @Override
      protected void onLoad() {
        super.onLoad();
        onRichTextLoad();
      }
    };

    richtext.setStylePrimaryName("gwtao-RichText");
    richtext.setWidth("100%");
    richtext.setHeight("100%");

    toolbar = new RichTextToolbar(richtext);
    toolbar.setWidth("100%");

    panel = new DockPanel();
    panel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
    panel.setVerticalAlignment(DockPanel.ALIGN_TOP);
    panel.add(toolbar, DockPanel.NORTH);
    panel.add(richtext, DockPanel.CENTER);
    panel.setCellHeight(toolbar, "56px");
    panel.setCellHeight(richtext, "100%");

    setBorder(true);

    initWidget(panel);

    setWidth("100%");
    setHeight("100%");
  }

  protected void onRichTextLoad() {
    Timer timer = new Timer() {
      @Override
      public void run() {
        Element x = xGet(richtext.getElement());
        if (x != null) {
          x.getStyle().setProperty("font", "normal 14px arial,helvetica,sans-serif");
          cancel();
        }
      }
    };
    timer.scheduleRepeating(1000);
  }

  private native Element xGet(Element x)
  /*-{
    return (x.contentWindow&&x.contentWindow.document)?
    x.contentWindow.document.body:null;
  }-*/;

  public void setBorder(boolean b) {
    if (b)
      panel.setStyleName("gwtao-RichText-Border");
    else
      panel.removeStyleName("gwtao-RichText-Border");
  }

  public RichTextArea getRichtext() {
    return richtext;
  }

  public String getHTML() {
    return richtext.getHTML();
  }

  public void setFocus() {
    // Tricky, move focus to richtext...
    try {
      richtext.getFormatter().toggleBold();
      toolbar.flushFocus();
      richtext.setFocus(true);
      richtext.getFormatter().toggleBold();
    }
    catch (Exception e) {
      // ignore
    }
  }

  public void setReadOnly(boolean readonly) {
    this.readonly = readonly;
  }

  public void setHTML(String html) {
    richtext.setHTML(html);
  }

  public void addBlurHandler(BlurHandler handler) {
    richtext.addBlurHandler(handler);
  }
}
