package com.gwtao.ui.util.client.action;

import com.google.gwt.user.client.History;

public class NavigationAction extends Action {

  private String token;

  public NavigationAction(String token, String title, String icon, String tooltip) {
    super(title, icon, tooltip);
    this.token = token;
  }

  public NavigationAction(String token, String title, String icon) {
    this(token, title, icon, null);
  }

  public NavigationAction(String token, String title) {
    this(token, title, null, null);
  }

  @Override
  public void execute(Object... data) {
    History.newItem(token);
  }

}
