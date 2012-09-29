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
