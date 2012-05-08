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

import com.gwtao.ui.util.client.Size;

public class LayoutData {

  private int minWidth;
  private int minHeight;

  int effectiveMinWidth, effectiveMinHeight;

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

  public Size getEffectiveMinSize() {
    return new Size(effectiveMinWidth, effectiveMinHeight);
  }
}
