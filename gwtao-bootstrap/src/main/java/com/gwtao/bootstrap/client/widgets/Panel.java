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

import org.apache.commons.lang.StringUtils;

import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class Panel extends ComplexPanel implements AcceptsOneWidget {

  private Element root;
  private Element heading;
  private Element body;

  private Element icon;
  private Element title;

  private IsWidget widget;

  public Panel() {
    root = DOM.createDiv();
    heading = DOM.createDiv();
    body = DOM.createDiv();

    root.setClassName("panel panel-primary");
    heading.setClassName("panel-heading");

    setElement(root);
    root.appendChild(heading);
    root.appendChild(body);
  }

  public void setIcon(IconType iconType) {
    setIcon(iconType.get());
  }

  public void setIcon(String iconClass) {
    if (StringUtils.isNotBlank(iconClass)) {
      if (this.icon == null) {
        this.icon = DOM.createElement("i");
        heading.insertFirst(this.icon);
      }
      this.icon.setClassName(iconClass);
    }
    else if (this.icon != null) {
      this.icon.removeFromParent();
      this.icon = null;
    }
  }

  public void setTitle(String text) {
    if (this.title == null) {
      this.title = DOM.createSpan();
      if (icon == null)
        heading.insertFirst(this.title);
      else
        heading.insertAfter(this.title, this.icon);
      this.title.getStyle().setPaddingLeft(8.0, Unit.PX);
    }
    this.title.setInnerText(text);
  }

  public void setWidget(IsWidget newWidget) {
    if (widget != null)
      widget.asWidget().removeFromParent();
    widget = newWidget;
    if (widget != null)
      add(widget.asWidget(), body);
  }

  public void add(Widget child) {
    setWidget(child);
  }
}
