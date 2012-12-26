package com.gwtao.app.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.SwitchPanel;

public class PagesPanel implements IsWidget {

  private final SwitchPanel switchPanel = new SwitchPanel();

  @Override
  public Widget asWidget() {
    return switchPanel;
  }

  public void show(IPage page) {
    int idx = switchPanel.getWidgetIndex(page);
    if (idx == -1) {
      switchPanel.add(page);
    }
    else {
      switchPanel.showWidget(idx);
    }
  }

}
