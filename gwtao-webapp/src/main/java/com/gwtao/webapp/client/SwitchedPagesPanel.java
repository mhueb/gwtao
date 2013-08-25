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
package com.gwtao.webapp.client;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.widgets.client.SwitchPanel;

public class SwitchedPagesPanel implements IsWidget, IPagesController {

  private final SwitchPanel switchPanel = new SwitchPanel();
  private IWindowTitleSetter wts;

  public SwitchedPagesPanel(IWindowTitleSetter wts) {
    this.wts = wts;
    switchPanel.getElement().getStyle().setOverflow(Overflow.AUTO);
  }

  @Override
  public Widget asWidget() {
    return switchPanel;
  }

  public void show(IPage page) {
    int idx = switchPanel.getWidgetIndex(page);
    if (idx == -1) {
      switchPanel.add(page);
      idx = switchPanel.getWidgetIndex(page);
    }
    switchPanel.showWidget(idx);
    page.onShow();
  }

  public void close(IPage page) {
    int idx = switchPanel.getWidgetIndex(page);
    if (idx != -1) {
      page.onClose();
      switchPanel.remove(idx);
    }
  }

  @Override
  public void updateTitle(IPage page) {
    wts.updateWindowTitle(page.getDisplayTitle());
  }

  @Override
  public IPage getActivePage() {
    PageWrapper wrapper = (PageWrapper) switchPanel.getVisibleWidget();
    if (wrapper != null)
      return wrapper.getPage();
    return null;
  }

  @Override
  public void clear() {
    switchPanel.clear();
  }
}
