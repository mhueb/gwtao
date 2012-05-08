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
package com.gwtao.ui.layout.client;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.Size;

public abstract class AbstractLayout<T extends LayoutData> implements ILayout {
  private static final String POSITION = "position";
  private static final String TOP = "top";
  private static final String LEFT = "left";
  private static final String PX = "px";
  private LayoutPanel layoutPanel;

  @Override
  public final void init(LayoutPanel layoutPanel) {
    this.layoutPanel = layoutPanel;
    init();
  }

  protected void init() {
    // Element elem = getLayoutPanel().getElement();
    // DOM.setStyleAttribute(elem, POSITION, "static");
    // DOM.setStyleAttribute(elem, WIDTH, "100%");
    // DOM.setStyleAttribute(elem, HEIGHT, "100%");
    // DOM.setStyleAttribute(elem, LEFT, "");
    // DOM.setStyleAttribute(elem, TOP, "");
  }

  @Override
  public void exit() {
  }

  @Override
  public void onAddChild(Widget child) {
    // DOM.setStyleAttribute(w.getElement(), "overflow", "hidden");
  }

  @Override
  public void onRemoveChild(Widget child) {
  }

  public LayoutPanel getLayoutPanel() {
    return layoutPanel;
  }

  protected Iterator<Widget> iterateWidgets() {
    return layoutPanel.iterator();
  }

  protected Size getClientSize() {
    Size size;
    // size = new Size(layoutPanel.getOffsetWidth(), layoutPanel.getOffsetHeight());
    Element e = layoutPanel.getElement();
    size = new Size(e.getClientWidth(), e.getClientHeight());
    return size;
  }

  protected static Size getWidgetMinSize(Widget widget) {
    if (widget instanceof ILayoutContainer)
      return ((ILayoutContainer) widget).getMinSize();
    return new Size(0, 0);// widget.getOffsetWidth(), widget.getOffsetHeight());
  }

  protected Size updateEffectiveMinSize(Widget widget) {
    Size minSize = getWidgetMinSize(widget);
    LayoutData fld = getWidgetData(widget);
    fld.effectiveMinHeight = Math.max(fld.getMinHeight(), minSize.getHeight());
    fld.effectiveMinWidth = Math.max(fld.getMinWidth(), minSize.getWidth());
    return fld.getEffectiveMinSize();
  }

  @SuppressWarnings("unchecked")
  protected T getWidgetData(Widget widget) {
    T ld = (T) widget.getLayoutData();
    if (ld == null) {
      ld = createDefaultLayoutData();
      widget.setLayoutData(ld);
    }
    return ld;
  }

  protected abstract T createDefaultLayoutData();

  protected static void placeWidget(Widget widget, int left, int top, int width, int height) {
    Element elem = widget.getElement();
    DOM.setStyleAttribute(elem, POSITION, "absolute");
    DOM.setStyleAttribute(elem, LEFT, left + PX);
    DOM.setStyleAttribute(elem, TOP, top + PX);
    widget.setSize(width + PX, height + PX);
    // DOM.setStyleAttribute(elem, WIDTH, width + PX);
    // DOM.setStyleAttribute(elem, HEIGHT, height + PX);
  }

  protected static void sizeWidget(Widget widget, int width, int height) {
    widget.setSize(width + PX, height + PX);
    // Element elem = widget.getElement();
    // DOM.setStyleAttribute(elem, WIDTH, width + PX);
    // DOM.setStyleAttribute(elem, HEIGHT, height + PX);
  }
}
