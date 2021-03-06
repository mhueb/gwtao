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
package com.gwtao.portalapp.client.document;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.view.AbstractPortalView;
import com.gwtao.ui.history.client.Token;
import com.gwtao.ui.util.client.ParameterList;
import com.gwtao.ui.util.client.ParameterList.Builder;

public class InvalidDocumentView extends AbstractPortalView implements IDocument, IDocumentDescriptor {
  private final Token token;
  private final String errorMessage;
  private ScrollPanel scroll;

  public InvalidDocumentView(Token token, String errorMessage) {
    this.token = token;
    this.errorMessage = errorMessage;
  }

  @Override
  public void init() {
    SafeHtmlBuilder sb = new SafeHtmlBuilder();

    sb.appendHtmlConstant("<div style='padding:8px;'>");
    sb.appendHtmlConstant("<div style='font-weight:bold;'>Failed to open document</div>");
    if (StringUtils.isNotEmpty(this.errorMessage)) {
      sb.appendHtmlConstant("<div style='font-style:italic;'>");
      sb.appendEscapedLines(this.errorMessage);
      sb.appendHtmlConstant("</div>");
    }
    sb.appendHtmlConstant("<br/>");

    if (StringUtils.isNotEmpty(token.getValue())) {
      sb.appendHtmlConstant("<div style='font-weight:bold;'>Token</div>");
      sb.appendHtmlConstant("<div style='font-style:italic;'>");
      sb.appendEscaped(token.getValue());
      sb.appendHtmlConstant("</div>");
      sb.appendHtmlConstant("<br/>");
    }

    sb.appendHtmlConstant("</div>");

    HTML html = new HTML(sb.toString());

    scroll = new ScrollPanel(html);
    scroll.getElement().getStyle().setBackgroundColor("#ffa0a0");
    scroll.setSize("100%", "100%");
  }

  @Override
  public Widget getWidget() {
    return scroll;
  }

  @Override
  public String canClose() {
    return null;
  }

  @Override
  public IDocumentDescriptor getDescriptor() {
    return this;
  }

  @Override
  public Object getParameter() {
    return null;
  }

  @Override
  public void setParameter(Object parameter) {
  }

  @Override
  public boolean isDirty() {
    return false;
  }

  @Override
  public boolean isClosable() {
    return true;
  }

  @Override
  public void onActivate() {
  }

  @Override
  public void onClose() {
  }

  @Override
  public void onDeactivate() {
  }

  @Override
  public void onOpen() {
  }

  @Override
  public String getDisplayIcon() {
    return null;
  }

  @Override
  public String getDisplayText() {
    return "Error";
  }

  @Override
  public String getDisplayTooltip() {
    return null;
  }

  @Override
  public Accordance canHandle(Object param) {
    return null;
  }

  @Override
  public Object decodeParameter(ParameterList param) {
    return null;
  }

  @Override
  public void encodeParameter(Builder builder, Object param) {
  }

  @Override
  public IDocument create() {
    return null;
  }

  @Override
  public String getId() {
    return null;
  }
}
