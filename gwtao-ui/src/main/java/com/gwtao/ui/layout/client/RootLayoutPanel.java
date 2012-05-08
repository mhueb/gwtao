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
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.Size;

@SuppressWarnings("deprecation")
public class RootLayoutPanel extends LayoutPanel {
  private final WindowResizeListener handler = new WindowResizeListener() {
    @Override
    public void onWindowResized(int width, int height) {
      RootLayoutPanel.this.onResize();
    }
  };

  public RootLayoutPanel(Widget w) {
    setLayout(new RootLayout());
    add(w);
  }

  @Override
  public void setLayout(ILayout layout) {
    if (layout instanceof RootLayout)
      super.setLayout(layout);
    else
      throw new IllegalArgumentException("layout not accepted");
  }

  @Override
  protected void onLoad() {
    Window.enableScrolling(false);
    Window.setMargin("0");

    Window.addWindowResizeListener(handler);
    super.onLoad();
  }

  @Override
  protected void onUnload() {
    Window.removeWindowResizeListener(handler);
    super.onUnload();
  }

  @Override
  public void onResize() {
    Window.enableScrolling(false);
    super.onResize();
    Size minSize = getMinSize();
    if (minSize.getWidth() > Window.getClientWidth() || minSize.getHeight() > Window.getClientHeight())
      Window.enableScrolling(true);
  }
}
