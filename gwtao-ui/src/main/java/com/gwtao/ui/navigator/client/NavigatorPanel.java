package com.gwtao.ui.navigator.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class NavigatorPanel implements IsWidget {

  SimplePanel dummy = new SimplePanel();

  @Override
  public Widget asWidget() {
    return dummy;
  }

}
