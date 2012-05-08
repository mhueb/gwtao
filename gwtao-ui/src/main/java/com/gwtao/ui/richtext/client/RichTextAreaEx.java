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
package com.gwtao.ui.richtext.client;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.Element;
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
