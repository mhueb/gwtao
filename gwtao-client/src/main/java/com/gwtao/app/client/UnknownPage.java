/* 
 * Copyright 2012 GWTAO
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.app.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.history.client.Token;

final class UnknownPage implements IPage {
  private final HTMLPanel html;
  private Token token;

  public UnknownPage(Token token) {
    this.token = token;
    SafeHtmlBuilder buff = new SafeHtmlBuilder();
    buff.appendHtmlConstant("<h1>Unknown Page</h1>");
    buff.appendEscapedLines(token.getPlace());
    html = new HTMLPanel(buff.toSafeHtml());
    html.getElement().getStyle().setPadding(12.0, Unit.PX);
    html.getElement().getStyle().setMarginLeft(12.0, Unit.PX);
    html.getElement().getStyle().setMarginRight(12.0, Unit.PX);
    html.getElement().getStyle().setBackgroundColor("#FFFFD4");
  }

  @Override
  public void init(IPageContext ctx) {
  }

  @Override
  public String getDisplayText() {
    return token.getPlace();
  }

  @Override
  public Widget asWidget() {
    return html;
  }
}