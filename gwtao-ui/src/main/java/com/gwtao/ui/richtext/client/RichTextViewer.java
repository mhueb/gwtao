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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

public class RichTextViewer extends Composite {
  private final Frame richtext;
  private boolean loaded;
  private String html;

  public RichTextViewer() {
    this.richtext = new Frame() {
      @Override
      protected void onUnload() {
        super.onUnload();
        loaded = false;
      }

      @Override
      protected void onLoad() {
        super.onLoad();
        new Timer() {

          @Override
          public void run() {
            loaded = true;
            setHTML(html);
          }
        }.schedule(1000);
      }
    };

    this.richtext.setStyleName("gwtaf-RichTextAreaEx");

    // this.richtext.setStylePrimaryName("gwtaf-RichTextArea-Editor");
    this.richtext.setWidth("100%");
    this.richtext.setHeight("100%");

    initWidget(richtext);

    DOM.setStyleAttribute(getElement(), "border", "0px");
    // DOM.setStyleAttribute(getElement(), "overflow", "visible");

    // setBorder(true);
    setWidth("100%");
    setHeight("100%");
  }

  @Override
  protected void onAttach() {
    super.onAttach();
  }

  public void setBorder(boolean b) {
    if (b) {
      this.setStyleName("gwtaf-RichTextAreaEx-Border");
      DOM.setStyleAttribute(getElement(), "padding", "5px");
    }
    else {
      this.removeStyleName("gwtaf-RichTextAreaEx-Border");
      DOM.setStyleAttribute(getElement(), "padding", "");
    }
  }

  public String getHTML() {
    return html;
  }

  private native Element xGet(Element x)
  /*-{
    return (x.contentWindow&&x.contentWindow.document)?
    x.contentWindow.document.body:null;
  }-*/;

  public void setHTML(String html) {
    if (loaded) {
      Element element = xGet(this.richtext.getElement());
      element.getStyle().setProperty("font", "normal 14px arial,helvetica,sans-serif");
      element.setInnerHTML(html);
      // Element e = xGet(richtext.getElement());
      // if (e != null)
      // e.setInnerHTML(html);
    }
    this.html = html;
  }
  // private native Document getIFrameDocument(IFrameElement iframe)/*-{
  // return iframe.contentDocument;
  // }-*/;

}
