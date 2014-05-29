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

import org.apache.commons.lang.StringUtils;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Application frame.<br>
 * 
 * @author Matthias Huebner
 */
public class TabbedPagesPanel extends Composite implements IPageController {

  private TabLayoutPanel tabPanel;
  private boolean ignoreEvent;
  private IWindowTitleSetter app;

  public TabbedPagesPanel(IWindowTitleSetter app) {
    this.app = app;

    tabPanel = new TabLayoutPanel(20, Unit.PX);

    tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {

      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        if (ignoreEvent)
          return;
        PageWrapper page = (PageWrapper) tabPanel.getWidget(event.getItem());
        if (page != null)
          page.onHide();
      }
    });

    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

      @Override
      public void onSelection(SelectionEvent<Integer> event) {
        if (ignoreEvent)
          return;
        PageWrapper wrapper = (PageWrapper) tabPanel.getWidget(event.getSelectedItem());
        if (wrapper != null)
          wrapper.onShow();
      }
    });

    initWidget(tabPanel);
  }

  @Override
  public void show(IPageContext page) {
    PageWrapper tab = findTab(page);
    if (tab == null) {
      tab = new PageWrapper(page);
      tabPanel.add(tab);
    }

    tabPanel.selectTab(tab);
    doUpdateTitle(tab);
  }

  private PageWrapper findTab(IPageContext page) {
    for (Widget widget : tabPanel) {
      PageWrapper tab = (PageWrapper) widget;
      if (tab.getPageCtx() == page)
        return tab;
    }
    return null;
  }

  @Override
  public void close(IPageContext page) {
    PageWrapper wrapper = findTab(page);
    if (wrapper != null){
      tabPanel.remove(wrapper);
      wrapper.onClose();
    }
  }

  @Override
  public void updateTitle(IPageContext page) {
    PageWrapper tab = findTab(page);
    if (tab != null)
      doUpdateTitle(tab);
  }

  private void doUpdateTitle(PageWrapper tab) {
    String title = tab.getPageCtx().getPage().getDisplayText();
    if (StringUtils.isBlank(title))
      title = "<i>Missing Title</i>";

    int widgetIndex = tabPanel.getWidgetIndex(tab);
    if (widgetIndex != -1) {
      // TODO icon per tag, text-overflow per ellipse
      tabPanel.setTabHTML(widgetIndex, SafeHtmlUtils.fromString(title));
    }
    app.updateWindowTitle(title);
  }

  public IPageContext getActivePage() {
    int idx = tabPanel.getSelectedIndex();
    return idx == -1 ? null : ((PageWrapper) tabPanel.getWidget(idx)).getPageCtx();
  }

  @Override
  public void clear() {
    ignoreEvent = true;
    tabPanel.clear();
    ignoreEvent = false;
  }
}
