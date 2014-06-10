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
package com.gwtao.bootstrap.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class SidebarContainer extends ComplexPanel implements AcceptsOneWidget {

  public class SidebarButton extends ComplexPanel {
    private Element button;

    public SidebarButton() {
      button = DOM.createDiv();
      button.setClassName("sidebarcontainer-button");
      setElement(button);
      sinkEvents(Event.ONCLICK);
      addDomHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          toggleSidebar();
        }
      }, ClickEvent.getType());
    }
  }

  private Element root;
  private Element sidebar;
  private Element sidebarContent;
  private Element content;
  private SidebarButton button;

  private IsWidget widget;
  private IsWidget sidebarWidget;
  private HandlerRegistration resizeHandler;
  private boolean enabled;
  private boolean show;
  private int delta;

  public SidebarContainer() {
    root = DOM.createDiv();
    sidebar = DOM.createDiv();
    content = DOM.createDiv();
    button = new SidebarButton();
    sidebarContent = DOM.createDiv();
    root.setClassName("sidebarcontainer");
    root.addClassName("sidebaroff");
    sidebar.setClassName("sidebarcontainer-sidebar");
    sidebarContent.setClassName("sidebarcontainer-sidebar-content");
    content.setClassName("sidebarcontainer-content");
    root.appendChild(sidebar);
    add(button, root);
    root.appendChild(content);
    sidebar.appendChild(sidebarContent);
    setElement(root);
  }

  public void setHeightAdjust(int delta) {
    this.delta = delta;
    adjustHeight();
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    adjustHeight();
    resizeHandler = Window.addResizeHandler(new ResizeHandler() {

      @Override
      public void onResize(ResizeEvent event) {
        adjustHeight();
      }
    });
  }

  private void adjustHeight() {
    if (isAttached()) {
      int absoluteTop = sidebar.getAbsoluteTop();
      int height = Window.getClientHeight() - absoluteTop;
      if (absoluteTop != 0)
        height -= delta;
      if (height < 0)
        height = 0;
      sidebar.getStyle().setHeight(height, Unit.PX);
      button.getElement().getStyle().setHeight(height, Unit.PX);
    }
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    if (resizeHandler != null) {
      resizeHandler.removeHandler();
      resizeHandler = null;
    }
  }

  public void setWidget(IsWidget newWidget) {
    if (widget != null)
      widget.asWidget().removeFromParent();
    widget = newWidget;
    if (widget != null)
      add(widget.asWidget(), content);
  }

  public void add(Widget child) {
    setWidget(child);
  }

  @Override
  public void clear() {
    if (widget != null) {
      widget.asWidget().removeFromParent();
      widget = null;
    }
  }

  public void setSidebarWidget(IsWidget sidebarWidget) {
    if (this.sidebarWidget != null)
      this.sidebarWidget.asWidget().removeFromParent();
    this.sidebarWidget = sidebarWidget;
    if (sidebarWidget != null)
      add(sidebarWidget.asWidget(), sidebarContent);
  }

  public void clearSidebarWidget() {
    if (sidebarWidget != null) {
      enableSidebar(false);
      sidebarWidget.asWidget().removeFromParent();
      sidebarWidget = null;
    }
  }

  public void enableSidebar(boolean enable) {
    if (this.enabled != enable) {
      this.enabled = enable;
      if (enable) {
        root.removeClassName("sidebaroff");
      }
      else {
        root.addClassName("sidebaroff");
      }
    }
  }

  public void toggleSidebar() {
    showSidebar(!show);
  }

  public void showSidebar(boolean show) {
    if (enabled && this.show != show) {
      this.show = show;
      if (show) {
        root.addClassName("active");
      }
      else {
        root.removeClassName("active");
      }
    }
  }
}
