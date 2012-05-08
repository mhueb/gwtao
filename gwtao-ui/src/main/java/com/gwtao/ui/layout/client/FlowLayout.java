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

public class FlowLayout extends AbstractLayout<FlowLayoutData> {
  private final boolean horizontal;
  private Size minSize;
  private float totalRatio;

  public FlowLayout(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  @Override
  protected void init() {
    super.init();
    Element element = getLayoutPanel().getElement();
    DOM.setStyleAttribute(element, "position", "static");
    DOM.setStyleAttribute(element, "overflow", "hidden");
  }

  @Override
  protected FlowLayoutData createDefaultLayoutData() {
    return new FlowLayoutData(100, 100, 0.0f);
  }

  @Override
  public void layout() {
    if (minSize == null)
      return;

    Size totalSize = getClientSize();

    int freeSize = totalSize.get(horizontal) - minSize.get(horizontal);
    if (freeSize < 0)
      freeSize = 0;

    int otherSize = Math.max(totalSize.get(!horizontal), minSize.get(!horizontal));
    int left = 0;
    int top = 0;

    Iterator<Widget> it = iterateWidgets();
    while (it.hasNext()) {
      Widget widget = it.next();
      FlowLayoutData widgetData = getWidgetData(widget);
      Size effectiveMinSize = widgetData.getEffectiveMinSize();

      int size = (int) (effectiveMinSize.get(horizontal) + freeSize * widgetData.getRatio() / totalRatio + 0.5);
      if (horizontal) {
        placeWidget(widget, left, top, size, otherSize);
        left += size;
      }
      else {
        placeWidget(widget, left, top, otherSize, size);
        top += size;
      }
    }
  }

  public void measure() {
    totalRatio = calcTotalRatio();
    minSize = calcMinSize();
  }

  private float calcTotalRatio() {
    float totalRatio = 0.0f;
    Iterator<Widget> it = iterateWidgets();
    while (it.hasNext()) {
      Widget widget = it.next();
      FlowLayoutData widgetData = getWidgetData(widget);
      totalRatio += widgetData.getRatio();
    }
    return totalRatio;
  }

  private Size calcMinSize() {
    totalRatio = calcTotalRatio();
    int minWidth = 0;
    int minHeight = 0;
    Iterator<Widget> it = iterateWidgets();
    while (it.hasNext()) {
      Widget widget = it.next();
      Size minSize = updateEffectiveMinSize(widget);
      if (horizontal) {
        minWidth += minSize.getWidth();
        minHeight = Math.max(minHeight, minSize.getHeight());
      }
      else {
        minWidth = Math.max(minWidth, minSize.getWidth());
        minHeight += minSize.getHeight();
      }
    }
    return new Size(minWidth, minHeight);
  }

  @Override
  public Size getMinSize() {
    return minSize;
  }
}
