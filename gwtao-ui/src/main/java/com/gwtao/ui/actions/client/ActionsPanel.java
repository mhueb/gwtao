package com.gwtao.ui.actions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ActionsPanel extends Composite {

  private static ActionsPanelUiBinder uiBinder = GWT.create(ActionsPanelUiBinder.class);

  interface ActionsPanelUiBinder extends UiBinder<Widget, ActionsPanel> {
  }

  public ActionsPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
