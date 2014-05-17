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

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WaitMask implements IWaitMask {
  private final Widget related;
  private HTML mask;
  private VerticalPanel back;
  private WaitInfo waitInfo;
  private int showLock;

  public WaitMask(IsWidget related) {
    this(related.asWidget());
  }

  public WaitMask(Widget related) {
    this.related = related;
    this.waitInfo = new WaitInfo();
  }

  @Override
  public void setMessage(SafeHtml message) {
    this.waitInfo.setMessage(message);
  }

  @Override
  public void show() {
    if (showLock++ == 0) {
      this.mask = new HTML();
      this.back = new VerticalPanel();
      this.mask.setStyleName("gwtao-waitmask-mask");
      this.back.setStyleName("gwtao-waitmask-back");
      this.back.add(waitInfo.asWidget());
      this.back.setCellHorizontalAlignment(waitInfo.asWidget(), HasHorizontalAlignment.ALIGN_CENTER);
      this.back.setCellVerticalAlignment(waitInfo.asWidget(), HasVerticalAlignment.ALIGN_MIDDLE);
      this.related.getElement().getParentElement().appendChild(mask.getElement());
      this.related.getElement().getParentElement().appendChild(back.getElement());
    }
  }

  @Override
  public void hide() {
    if (showLock > 0) {
      if (--showLock == 0) {
        setMessage(null);
        this.back.getElement().removeFromParent();
        this.mask.getElement().removeFromParent();
        this.back = null;
        this.mask = null;
      }
    }
  }
}
