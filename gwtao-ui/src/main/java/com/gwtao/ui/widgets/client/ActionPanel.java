package com.gwtao.ui.widgets.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.NullDataSource;
import com.gwtao.ui.util.client.action.IAction;

public class ActionPanel extends Composite {
  private final HorizontalPanel buttons;

  public ActionPanel() {
    buttons = new HorizontalPanel();
    buttons.setSpacing(8);
    initWidget(buttons);
  }

  public void add(IAction action) {
    add(action, NullDataSource.NULL);
  }

  public void add(IAction action, IDataSource<?> source) {
    SimpleButton but = new SimpleButton(action, source);
    buttons.add(but);
    buttons.setCellHorizontalAlignment(but, HasHorizontalAlignment.ALIGN_LEFT);
  }
}
