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
package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.NullDataSource;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.card.ICard;

public class ToolPanel extends Composite {
  private final FlowPanel panel;

  public ToolPanel() {
    panel = new FlowPanel();
    initWidget(panel);
    setStyleName("gwtao-toolpanel");
  }

  public void add(IAction action) {
    add(action, NullDataSource.NULL);
  }

  public void add(IAction action, IDataSource<?> source) {
    SimpleButton but = new SimpleButton(action, source);
    add(but);
  }

  public void add(Widget widget) {
    widget.addStyleName("gwtao-toolpanelitem");
    widget.getElement().getStyle().setMarginTop(4, Unit.PX);
    widget.getElement().getStyle().setMarginRight(2, Unit.PX);
    widget.getElement().getStyle().setMarginBottom(4, Unit.PX);
    widget.getElement().getStyle().setMarginLeft(2, Unit.PX);
    panel.add(widget);
  }

  public void addSpace(int i, Unit px) {
    HTML w = new HTML();
    w.getElement().getStyle().setWidth(i, px);
    w.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
    panel.add(w);
  }

  public void update(ICard actions) {
    // TODO Auto-generated method stub

  }

  public void setAddStyleName(String name) {
    addStyleName(name);
  }
}
