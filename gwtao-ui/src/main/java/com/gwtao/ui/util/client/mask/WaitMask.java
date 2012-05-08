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
package com.gwtao.ui.util.client.mask;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WaitMask implements IWaitMask {
  private final Widget related;
  private HTML mask;
  private VerticalPanel back;
  private IWaitInfo waitInfo;
  private int showLock;

  public WaitMask(Widget related) {
    this.related = related;
  }

  @Override
  public void show() {
    show(null);
  }

  @Override
  public void show(final String message) {
    if (showLock++ == 0) {
      this.mask = new HTML();
      this.back = new VerticalPanel();
      this.mask.setStyleName("gwtaf-mask-mask");
      this.back.setStyleName("gwtaf-mask-back");
      this.waitInfo = createWaitInfo();
      this.waitInfo.setMessage(message);
      this.back.add(waitInfo.getWidget());
      this.back.setCellHorizontalAlignment(waitInfo.getWidget(), HasHorizontalAlignment.ALIGN_CENTER);
      this.back.setCellVerticalAlignment(waitInfo.getWidget(), HasVerticalAlignment.ALIGN_MIDDLE);
      this.related.getElement().getParentElement().appendChild(mask.getElement());
      this.related.getElement().getParentElement().appendChild(back.getElement());
      onCreate();
    }
  }

  protected void onCreate() {
  }

  @Override
  public void hide() {
    if (showLock > 0) {
      if (--showLock == 0) {
        this.back.getElement().removeFromParent();
        this.mask.getElement().removeFromParent();
        this.back = null;
        this.mask = null;
        this.waitInfo = null;
        onHide();
      }
    }
  }

  protected void onHide() {
  }

  protected IWaitInfo createWaitInfo() {
    return new WaitInfo();
  }
}
