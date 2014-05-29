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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtao.ui.util.client.Size;

public class RootLayoutPanel extends LayoutPanel {
  private static RootLayoutPanel singleton;

  public static RootLayoutPanel get() {
    if (singleton == null) {
      singleton = new RootLayoutPanel();
      RootPanel.get().add(singleton);
    }
    return singleton;
  }

  private HandlerRegistration resizeHandler;

  private RootLayoutPanel() {
    setLayout(new RootLayout());
  }

  @Override
  public void setLayout(ILayout layout) {
    if (layout instanceof RootLayout)
      super.setLayout(layout);
    else
      throw new IllegalArgumentException("RootLayout accepted only");
  }

  @Override
  protected void onLoad() {
    Window.enableScrolling(false);
    Window.setMargin("0");
    resizeHandler = Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(ResizeEvent event) {
        new Timer() {
          @Override
          public void run() {
            RootLayoutPanel.this.onResize();
          }
        }.schedule(500);
      }
    });
    super.onLoad();
  }

  @Override
  protected void onUnload() {
    resizeHandler.removeHandler();
    resizeHandler = null;
    super.onUnload();
  }

  @Override
  public void onResize() {
    Window.enableScrolling(false);
    super.onResize();
    Size minSize = getLayout().getMinSize();
    if (minSize.getWidth() > Window.getClientWidth() || minSize.getHeight() > Window.getClientHeight())
      Window.enableScrolling(true);
  }
}
