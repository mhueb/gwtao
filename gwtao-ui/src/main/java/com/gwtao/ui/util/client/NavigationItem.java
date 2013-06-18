package com.gwtao.ui.util.client;



public class NavigationItem extends DisplayableItem {
  private String token;

  public NavigationItem(String token, String title) {
    super(title);
    this.token = token;
  }

  public NavigationItem(String token, String title, String icon) {
    super(title, icon);
    this.token = token;
  }

  public NavigationItem(String token, String title, String icon, String tooltip) {
    super(title, icon, tooltip);
    this.token = token;
  }
  
  public String getToken() {
    return token;
  }
}
