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
import com.gwtao.ui.util.client.Size;

public class LayoutData {

  private final int minWidth;
  private final int minHeight;

  private int initialWidgetWidth = -1;
  private int initialWidgetHeight = -1;

  int effectiveMinWidth, effectiveMinHeight;

  public LayoutData(int minWidth, int minHeight) {
    this.minWidth = minWidth;
    this.minHeight = minHeight;
    this.initialWidgetWidth = minWidth;
    this.initialWidgetHeight = minHeight;
  }

  void initSize(Widget w) {
    if (/* this.initialWidgetWidth == -1 && */w.getOffsetHeight() != 0 && w.getOffsetWidth() != 0) {
      this.initialWidgetWidth = w.getOffsetWidth();
      this.initialWidgetHeight = w.getOffsetHeight();
      this.initialWidgetWidth = Math.max(this.minWidth, this.initialWidgetWidth);
      this.initialWidgetHeight = Math.max(this.minHeight, this.initialWidgetHeight);
    }
  }

  public LayoutData(LayoutData o) {
    this.minWidth = o.minWidth;
    this.minHeight = o.minHeight;
  }

  public int getMinWidth() {
    return initialWidgetWidth;
  }

  public int getMinHeight() {
    return initialWidgetHeight;
  }

  public int getMin(boolean horizontal) {
    return horizontal ? initialWidgetWidth : initialWidgetHeight;
  }

  public Size getEffectiveMinSize() {
    return new Size(effectiveMinWidth, effectiveMinHeight);
  }

  public Size getMinSize() {
    return new Size(minWidth, minHeight);
  }
}
