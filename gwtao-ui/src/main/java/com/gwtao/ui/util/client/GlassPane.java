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
package com.gwtao.ui.util.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
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

    com.google.gwt.user.client.Element elem1 = iFrame.cast();
    DOM.appendChild(RootPanel.getBodyElement(), elem1);

    com.google.gwt.user.client.Element elem2 = iDiv.cast();
    DOM.appendChild(RootPanel.getBodyElement(), elem2);
  }

  public void update() {
    int left = reference.getAbsoluteLeft();
    int top = reference.getAbsoluteTop();
    int width = reference.getOffsetWidth();
    int height = reference.getOffsetHeight();

    update(left, top, width, height);
  }

  private void update(int left, int top, int width, int height) {
    com.google.gwt.user.client.Element elem1 = iFrame.cast();
    move(left, top, width, height, elem1);

    com.google.gwt.user.client.Element elem2 = iDiv.cast();
    move(left, top, width, height, elem2);
  }

  private void move(int left, int top, int width, int height, com.google.gwt.user.client.Element elem) {
    DOM.setStyleAttribute(elem, "left", left + "px");
    DOM.setStyleAttribute(elem, "top", top + "px");
    DOM.setStyleAttribute(elem, "width", width + "px");
    DOM.setStyleAttribute(elem, "height", height + "px");
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
    com.google.gwt.user.client.Element elem2 = iDiv.cast();
    DOM.setStyleAttribute(elem2, "cursor", cursor);
  }

  public static GlassPane createGlassPane(Widget reference, String cursor) {
    GlassPane shield = new GlassPane(reference);
    shield.update(0, 0, Window.getClientWidth(), Window.getClientHeight());
    shield.setCursor(cursor);
    return shield;
  }
}
