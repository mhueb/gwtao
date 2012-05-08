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

import com.google.gwt.user.client.Window;
import com.gwtao.ui.util.client.GWTUtil;
import com.gwtao.ui.util.client.Size;

public class RootLayout extends SimpleLayout {

  private int scrollerWidth;
  private int scrollerHeight;

  @Override
  public void measure() {
    int[] calcScrollbarSize = GWTUtil.calcScrollbarSize();
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
