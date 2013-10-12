/* 
 * Copyright 2012 Matthias Huebner
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
package com.gwtao.portalapp.client.frame;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.util.ExceptionUtil;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.deprecated.client.BorderlessButton;
import com.gwtao.portalapp.client.deprecated.client.ContextImageBundle;
import com.gwtao.portalapp.client.part.AbstractPortalPart;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.HorizontalFlowLayout;
import com.gwtao.ui.layout.client.LayoutPanel;
import com.gwtao.ui.layout.client.VerticalFlowLayout;
import com.gwtao.ui.util.client.action.Action;

public class BrokenPortalPart extends AbstractPortalPart {
  private final ScrollPanel scroll = new ScrollPanel();
  private final LayoutPanel root = new LayoutPanel(new VerticalFlowLayout());
  private Throwable e;

  public BrokenPortalPart(String id, Throwable e) {
    super(id);
    this.e = e;
  }

  @Override
  protected void init() {
    HTML title = new HTML("<div style='padding:2px 0 0 8px; font-weight:bold;'>This part is broken!</div>");
    BorderlessButton closeButton = new BorderlessButton(new Action(null, ContextImageBundle.CLOSE_ICON) {
      @Override
      public void execute(Object... data) {
        getPartContext().close();
      }
    }, BorderlessButton.ICON);

    LayoutPanel head = new LayoutPanel(new HorizontalFlowLayout());
    title.setLayoutData(new FlowLayoutData(100, 20, 1.0f));
    head.add(title);
    closeButton.setLayoutData(new FlowLayoutData(20, 20));
    head.add(closeButton);

    head.setLayoutData(new FlowLayoutData(120, 20));
    root.add(head);

    HTML message = new HTML(createMessage());
    scroll.setSize("100%", "100%");
    scroll.add(message);
    scroll.setLayoutData(new FlowLayoutData(100, 100, 1.0f));
    root.add(scroll);

    root.getElement().getStyle().setBackgroundColor("#ffa0a0");
  }

  private String createMessage() {
    SafeHtmlBuilder sb = new SafeHtmlBuilder();
    sb.appendHtmlConstant("<div style='padding:8px;'>");
    if (StringUtils.isNotEmpty(e.getMessage())) {
      sb.appendHtmlConstant("<div style='font-style:italic;'>");
      sb.appendEscapedLines(e.getMessage());
      sb.appendHtmlConstant("</div>");
    }
    sb.appendHtmlConstant("<br/>");

    sb.appendHtmlConstant("<div style='font-weight:bold;'>Part id</div>");
    sb.appendHtmlConstant("<div style='font-style:italic;'>");
    sb.appendEscaped(getId());
    sb.appendHtmlConstant("</div>");
    sb.appendHtmlConstant("<br/>");

    String stackTrace = ExceptionUtil.dumpStackTraceHTML(e);
    if (StringUtils.isNotEmpty(stackTrace)) {
      sb.appendHtmlConstant("<div style='font-weight:bold;'>Stacktrace</div>");
      sb.appendHtmlConstant(stackTrace);
    }
    sb.appendHtmlConstant("</div>");
    return sb.toString();
  }

  @Override
  public Widget getWidget() {
    return root;
  }
}
