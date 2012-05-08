/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.ui.portal.client.document;

import org.apache.commons.lang.StringUtils;
import org.mortbay.util.StringUtil;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.portal.client.view.AbstractPortalView;

public class InvalidDocumentView extends AbstractPortalView implements IDocument, IDocumentDescriptor {
  private final HistoryToken token;
  private final Throwable t;
  private ScrollPanel scroll;

  public InvalidDocumentView(HistoryToken token, Throwable t) {
    this.token = token;
    this.t = t;
  }

  @Override
  public void init() {
    StringBuffer sb = new StringBuffer();
    sb.append("<div style='padding:8px;'>");
    sb.append("<div style='font-weight:bold;'>Failed to open document</div>");
    if (StringUtils.isNotEmpty(t.getMessage())) {
      sb.append("<div style='font-style:italic;'>");
      sb.append(HTMLUtil.inactivateAndNewline(t.getMessage()));
      sb.append("</div>");
    }
    sb.append("<br/>");

    if (StringUtils.isNotEmpty(token.getValue())) {
      sb.append("<div style='font-weight:bold;'>Token</div>");
      sb.append("<div style='font-style:italic;'>");
      sb.append(HTMLUtil.inactivateAndNewline(token.getValue()));
      sb.append("</div>");
      sb.append("<br/>");
    }

    String stackTrace = ExceptionUtil.dumpStackTraceHTML(t);
    if (StringUtils.isNotEmpty(stackTrace)) {
      sb.append("<div style='font-weight:bold;'>Stacktrace</div>");
      sb.append(stackTrace);
    }
    sb.append("</div>");

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
  public String getIcon() {
    return null;
  }

  @Override
  public String getTitle() {
    return "Error";
  }

  @Override
  public String getTooltip() {
    return null;
  }

  @Override
  public Accordance canHandle(Object param) {
    return null;
  }

  @Override
  public Object decodeParameter(IInputParameter param) {
    return null;
  }

  @Override
  public void encodeParameter(IParameterMap params, Object param) {
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
