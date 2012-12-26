package com.gwtao.ui.navigator.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NavigatorPanel extends Composite {

  private static NavigatorPanelUiBinder uiBinder = GWT.create(NavigatorPanelUiBinder.class);

  interface NavigatorPanelUiBinder extends UiBinder<Widget, NavigatorPanel> {
  }

  public NavigatorPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
