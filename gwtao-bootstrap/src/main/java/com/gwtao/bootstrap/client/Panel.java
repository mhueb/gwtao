package com.gwtao.bootstrap.client;

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
