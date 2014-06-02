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

import org.apache.commons.lang.Validate;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ContainerSidebar extends ComplexPanel implements AcceptsOneWidget {

  private Element root;

  private IsWidget widget;

  public ContainerSidebar() {
    root = DOM.createDiv();
    setElement(root);
  }

  public void setWidget(IsWidget newWidget) {
    if (widget != null)
      widget.asWidget().removeFromParent();
    widget = newWidget;
    if (widget != null)
      add(widget.asWidget(), root);
  }

  public void add(Widget child) {
    setWidget(child);
  }

  public void showSidebar(Sidebar sidebar, boolean show) {
    if (show) {
      addStyleName("content-with-sidebar");
      removeStyleName("content-without-sidebar");
      sidebar.removeStyleName("sidebar-hidden");
    }
    else {
      addStyleName("content-without-sidebar");
      removeStyleName("content-with-sidebar");
      sidebar.addStyleName("sidebar-hidden");
    }
  }
}
