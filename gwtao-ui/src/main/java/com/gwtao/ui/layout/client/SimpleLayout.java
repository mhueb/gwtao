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

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.CSS;
import com.gwtao.ui.util.client.Size;

public class SimpleLayout extends AbstractLayout<LayoutData> {
  private Size minSize;

  @Override
  public void onAddChild(Widget w) {
    super.onAddChild(w);
    if (getLayoutPanel().getWidgetCount() > 1)
      throw new IllegalStateException("Only one widget allowed");
  }

  @Override
  protected LayoutData createDefaultLayoutData(int minWidth, int minHeight) {
    return new LayoutData(minWidth, minHeight);
  }

  @Override
  public void layout() {
    if (getLayoutPanel().getWidgetCount() > 0) {
      Size size = getClientSize();
      Widget widget = getLayoutPanel().getWidget(0);
      LayoutData widgetData = getWidgetData(widget);
      int width = Math.max(widgetData.effectiveMinWidth, size.getWidth() - CSS.calcWidthOffset(widget.getElement()));
      int height = Math.max(widgetData.effectiveMinHeight, size.getHeight() - CSS.calcHeightOffset(widget.getElement()));
      sizeWidget(widget, width, height);
    }
  }

  @Override
  public void measure() {
    super.measure();
    if (getLayoutPanel().getWidgetCount() > 0) {
      Widget widget = getLayoutPanel().getWidget(0);
      minSize = updateEffectiveMinSize(widget);
    }
    else
      minSize = new Size(0, 0);

    int xw = CSS.calcWidthOffset(getLayoutPanel().getElement());
    int yw = CSS.calcHeightOffset(getLayoutPanel().getElement());

    minSize = new Size(minSize.getWidth() + xw, minSize.getHeight() + yw);

  }

  @Override
  public Size getMinSize() {
    return minSize == null ? Size.ZERO : minSize;
  }
}
