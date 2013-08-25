package com.gwtao.ui.util.client.action;

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.IDisplayableItem;

public class NavigationAction extends Action {

  private String token;

  public NavigationAction(String token, IDisplayableItem item) {
    super(item);
    this.token = token;
  }

  public NavigationAction(String token, String title) {
    super(title);
    this.token = token;
  }

  @Override
  public void execute(Object... data) {
    History.newItem(token);
  }

}
