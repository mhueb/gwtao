/* 
 * GWTAF - GWT Application Framework
 * 
 * Copyright (C) 2008-2010 Matthias Huebner.
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
 * $Id: AppFrame.java 640 2010-09-19 01:01:54Z matthuebner $
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
  public void show(IPage page) {
    PageWrapper tab = findTab(page);
    if (tab == null) {
      tab = new PageWrapper(page);
      tabPanel.add(tab);
    }

    tabPanel.selectTab(tab);
    doUpdateTitle(tab);
  }

  private PageWrapper findTab(IPage page) {
    for (Widget widget : tabPanel) {
      PageWrapper tab = (PageWrapper) widget;
      if (tab.getPage() == page)
        return tab;
    }
    return null;
  }

  @Override
  public void close(IPage page) {
    PageWrapper wrapper = findTab(page);
    if (wrapper != null){
      tabPanel.remove(wrapper);
      wrapper.onClose();
    }
  }

  @Override
  public void updateTitle(IPage page) {
    PageWrapper tab = findTab(page);
    if (tab != null)
      doUpdateTitle(tab);
  }

  private void doUpdateTitle(PageWrapper tab) {
    String title = tab.getPage().getDisplayTitle();
    if (StringUtils.isBlank(title))
      title = "<i>Missing Title</i>";

    int widgetIndex = tabPanel.getWidgetIndex(tab);
    if (widgetIndex != -1) {
      // TODO icon per tag, text-overflow per ellipse
      tabPanel.setTabHTML(widgetIndex, SafeHtmlUtils.fromString(title));
    }
    app.updateWindowTitle(title);
  }

  public IPage getActivePage() {
    int idx = tabPanel.getSelectedIndex();
    return idx == -1 ? null : ((PageWrapper) tabPanel.getWidget(idx)).getPage();
  }

  @Override
  public void clear() {
    ignoreEvent = true;
    tabPanel.clear();
    ignoreEvent = false;
  }
}
