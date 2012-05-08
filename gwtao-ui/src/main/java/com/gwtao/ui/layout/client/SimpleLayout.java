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

import com.google.gwt.user.client.ui.Widget;
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
  protected LayoutData createDefaultLayoutData() {
    return new LayoutData(100, 100);
  }

  @Override
  public void layout() {
    if (getLayoutPanel().getWidgetCount() > 0) {
      Size size = getClientSize();
      Widget widget = getLayoutPanel().getWidget(0);
      LayoutData widgetData = getWidgetData(widget);
      int width = Math.max(widgetData.effectiveMinWidth, size.getWidth());
      int height = Math.max(widgetData.effectiveMinHeight, size.getHeight());
      sizeWidget(widget, width, height);
    }
  }

  @Override
  public void measure() {
    if (getLayoutPanel().getWidgetCount() > 0) {
      Widget widget = getLayoutPanel().getWidget(0);
      minSize = updateEffectiveMinSize(widget);
    }
    else
      minSize = new Size(0, 0);
  }

  public Size getMinSize() {
    return minSize;
  }
}
