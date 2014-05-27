package com.gwtao.ui.util.client;

import com.gwtao.ui.util.client.displayable.IDisplayableItem;

public class NavigationItem implements IDisplayableItem {
  private String token;
  private String title;
  private String icon;
  private String tooltip;

  public NavigationItem(String token, String title) {
    this.token = token;
    this.title = title;
  }

  public NavigationItem(String token, String title, String icon) {
    this(token, title);
    this.icon = icon;
  }

  public NavigationItem(String token, String title, String icon, String tooltip) {
    this(token, title, icon);
    this.tooltip = tooltip;
  }

  public String getToken() {
    return token;
  }

  @Override
  public String getDisplayTooltip() {
    return tooltip;
  }

  @Override
  public String getDisplayIcon() {
    return icon;
  }

  @Override
  public String getDisplayText() {
    return title;
  }

}
