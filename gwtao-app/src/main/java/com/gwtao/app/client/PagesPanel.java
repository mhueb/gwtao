package com.gwtao.app.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PagesPanel implements IsWidget {

  SimplePanel dummy = new SimplePanel();

  @Override
  public Widget asWidget() {
    return dummy;
  }

}
