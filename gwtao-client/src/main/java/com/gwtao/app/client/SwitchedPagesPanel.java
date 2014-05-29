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
package com.gwtao.app.client;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.widgets.client.SwitchPanel;

public class SwitchedPagesPanel implements IsWidget, IPageController {

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

  public void show(IPageContext pageCtx) {
    int idx = switchPanel.getWidgetIndex(pageCtx.getPage());
    if (idx == -1) {
      switchPanel.add(pageCtx.getPage());
      idx = switchPanel.getWidgetIndex(pageCtx.getPage());
    }
    switchPanel.showWidget(idx);
    pageCtx.getHandler().onShow();
  }

  public void close(IPageContext pageCtx) {
    int idx = switchPanel.getWidgetIndex(pageCtx.getPage());
    if (idx != -1) {
      pageCtx.getHandler().onClose();
      switchPanel.remove(idx);
    }
  }

  @Override
  public void updateTitle(IPageContext page) {
    wts.updateWindowTitle(page.getPage().getDisplayText());
  }

  @Override
  public IPageContext getActivePage() {
    PageWrapper wrapper = (PageWrapper) switchPanel.getVisibleWidget();
    if (wrapper != null)
      return wrapper.getPageCtx();
    return null;
  }

  @Override
  public void clear() {
    switchPanel.clear();
  }
}
