package com.gwtao.ui.util.client;


public class Token extends DisplayableItem {
  private String token;

  public Token(String token, String title) {
    super(title);
    this.token = token;
  }

  public Token(String token, String title, String icon) {
    super(title, icon);
    this.token = token;
  }

  public Token(String token, String title, String icon, String tooltip) {
    super(title, icon, tooltip);
    this.token = token;
  }
  
  public String getToken() {
    return token;
  }
}
