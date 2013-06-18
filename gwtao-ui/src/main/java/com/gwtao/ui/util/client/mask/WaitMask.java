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
