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
package com.gwtao.ui.util.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class GlassPane {

  private IFrameElement iFrame;
  private final Widget reference;
  private Integer original;
  private DivElement iDiv;

  private GlassPane(Widget reference) {
    iFrame = Document.get().createIFrameElement();
    // iFrame.setAttribute("src", "javascript:false");

    iDiv = Document.get().createDivElement();

    this.reference = reference;
    String zIndex = this.reference.getElement().getStyle().getZIndex();
    try {
      original = Integer.parseInt(zIndex);
    }
    catch (Exception e) {
    }

    this.reference.getElement().getStyle().setZIndex(10002);
    iFrame.getStyle().setBorderStyle(BorderStyle.NONE);
    iFrame.getStyle().setPosition(Position.ABSOLUTE);
    iFrame.getStyle().setZIndex(10000);

    iDiv.getStyle().setPosition(Position.ABSOLUTE);
    iDiv.getStyle().setZIndex(10001);

    com.google.gwt.dom.client.Element elem1 = iFrame.cast();
    RootPanel.getBodyElement().appendChild(elem1);

    com.google.gwt.dom.client.Element elem2 = iDiv.cast();
    RootPanel.getBodyElement().appendChild(elem2);
  }

  public void update() {
    int left = reference.getAbsoluteLeft();
    int top = reference.getAbsoluteTop();
    int width = reference.getOffsetWidth();
    int height = reference.getOffsetHeight();

    update(left, top, width, height);
  }

  private void update(int left, int top, int width, int height) {
    com.google.gwt.dom.client.Element elem1 = iFrame.cast();
    move(left, top, width, height, elem1);

    com.google.gwt.dom.client.Element elem2 = iDiv.cast();
    move(left, top, width, height, elem2);
  }

  private void move(int left, int top, int width, int height, com.google.gwt.dom.client.Element elem) {
    elem.getStyle().setProperty("left", left + "px");
    elem.getStyle().setProperty("top", top + "px");
    elem.getStyle().setProperty("width", width + "px");
    elem.getStyle().setProperty("height", height + "px");
  }

  public void remove() {
    if (original != null)
      reference.getElement().getStyle().setZIndex(original);
    else
      reference.getElement().getStyle().clearZIndex();
    iFrame.removeFromParent();
    iDiv.removeFromParent();
  }

  public static GlassPane create(Widget reference) {
    GlassPane shield = new GlassPane(reference);
    shield.update();
    return shield;
  }

  private void setCursor(String cursor) {
    com.google.gwt.dom.client.Element elem2 = iDiv.cast();
    elem2.getStyle().setProperty("cursor", cursor);
  }

  public static GlassPane createGlassPane(Widget reference, String cursor) {
    GlassPane shield = new GlassPane(reference);
    shield.update(0, 0, Window.getClientWidth(), Window.getClientHeight());
    shield.setCursor(cursor);
    return shield;
  }
}
