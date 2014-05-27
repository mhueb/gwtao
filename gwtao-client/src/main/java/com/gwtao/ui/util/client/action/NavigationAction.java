package com.gwtao.ui.util.client.action;

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.NavigationItem;

public class NavigationAction extends AbstractAction {

  private NavigationItem item;

  public NavigationAction(NavigationItem item) {
    this.item = item;
  }

  @Override
  public void execute(Object... data) {
    History.newItem(item.getToken());
  }

  @Override
  public String getDisplayText() {
    return item.getDisplayText();
  }

  @Override
  public String getDisplayIcon() {
    return item.getDisplayIcon();
  }

  @Override
  public String getDisplayTooltip() {
    return item.getDisplayTooltip();
  }
}
