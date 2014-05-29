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
