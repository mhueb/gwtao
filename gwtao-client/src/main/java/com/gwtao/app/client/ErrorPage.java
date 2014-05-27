package com.gwtao.app.client;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.place.client.Token;

final class ErrorPage implements IPage {
  private final HTMLPanel html;

  public ErrorPage(Token token, String errorMessage) {
    SafeHtmlBuilder buff = new SafeHtmlBuilder();
    buff.appendHtmlConstant("<h1>Unhandled Exception</h1>");
    buff.appendEscapedLines(errorMessage);
    html = new HTMLPanel(buff.toSafeHtml());
  }

  @Override
  public void init(IPageContext ctx) {
  }

  @Override
  public String getDisplayText() {
    return "Error";
  }

  @Override
  public Widget asWidget() {
    return html;
  }
}