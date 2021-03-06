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
package com.gwtao.ui.layout.client;

import com.google.gwt.dom.client.Element;
import com.gwtao.ui.util.client.Size;

public class LayoutData {

  private int minWidth;
  private int minHeight;

  public LayoutData(int minWidth, int minHeight) {
    this.minWidth = minWidth;
    this.minHeight = minHeight;
  }

  public LayoutData(LayoutData o) {
    this.minWidth = o.minWidth;
    this.minHeight = o.minHeight;
  }

  public int getMinWidth() {
    return minWidth;
  }

  public int getMinHeight() {
    return minHeight;
  }

  public int getMin(boolean horizontal) {
    return horizontal ? minWidth : minHeight;
  }

  void init(Element el) {
    if( minWidth == -1 )
      minWidth = el.getOffsetWidth();
    if( minHeight == -1 )
      minHeight = el.getOffsetHeight();
  }

  public Size getMinSize() {
    return new Size(minWidth, minHeight);
  }
}
