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

import com.google.gwt.user.client.Window;
import com.gwtao.ui.util.client.GWTUtils;
import com.gwtao.ui.util.client.Size;

public class RootLayout extends SimpleLayout {

  private int scrollerWidth;
  private int scrollerHeight;

  @Override
  public void measure() {
    int[] calcScrollbarSize = GWTUtils.calcScrollbarSize();
    this.scrollerWidth = calcScrollbarSize[0];
    this.scrollerHeight = calcScrollbarSize[1];
    super.measure();
  }

  @Override
  protected Size getClientSize() {
    Size minSize = getMinSize();
    Size size = new Size(Window.getClientWidth(), Window.getClientHeight());

    if (minSize.getHeight() > size.getHeight())
      size = new Size(size.getWidth() - this.scrollerWidth - 4, size.getHeight());
    if (minSize.getWidth() > size.getWidth())
      size = new Size(size.getWidth(), size.getHeight() - this.scrollerHeight - 4);

    return size;
  }
}
