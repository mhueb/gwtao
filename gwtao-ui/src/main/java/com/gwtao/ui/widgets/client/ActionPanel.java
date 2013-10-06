package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.NullDataSource;
import com.gwtao.ui.util.client.action.IAction;

public class ActionPanel extends Composite {
  private final FlowPanel buttons;

  public ActionPanel() {
    buttons = new FlowPanel();
    initWidget(buttons);
  }

  public void add(IAction action) {
    add(action, NullDataSource.NULL);
  }

  public void add(IAction action, IDataSource<?> source) {
    SimpleButton but = new SimpleButton(action, source);
    but.getElement().getStyle().setMarginTop(4, Unit.PX);
    but.getElement().getStyle().setMarginRight(2, Unit.PX);
    but.getElement().getStyle().setMarginBottom(4, Unit.PX);
    but.getElement().getStyle().setMarginLeft(2, Unit.PX);
    buttons.add(but);
  }

  public void addSpace(int i, Unit px) {
    HTMLPanel w = new HTMLPanel("");
    w.getElement().getStyle().setWidth(i, px);
    w.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
    buttons.add(w);
  }
}
