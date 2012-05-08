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
package com.gwtao.ui.portal.client.frame;

import org.apache.commons.lang.StringUtils;
import org.mortbay.util.StringUtil;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.layout.client.FlowLayout;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.LayoutPanel;
import com.gwtao.ui.portal.client.part.AbstractPortalPart;
import com.gwtao.ui.util.client.action.Action;

public class BrokenPortalPart extends AbstractPortalPart {
  private final ScrollPanel scroll = new ScrollPanel();
  private final LayoutPanel root = new LayoutPanel(new FlowLayout(false));
  private Throwable e;

  public BrokenPortalPart(String id, Throwable e) {
    super(id);
    this.e = e;
  }

  @Override
  protected void init() {
    HTML title = new HTML("<div style='padding:2px 0 0 8px; font-weight:bold;'>This part is broken!</div>");
    BorderlessButton closeButton = new BorderlessButton(new Action(null, GWTAFImageBundle.CLOSE_ICON) {
      @Override
      public void execute(Object... data) {
        getPartContext().close();
      }
    }, BorderlessButton.ICON);

    LayoutPanel head = new LayoutPanel(new FlowLayout(true));
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
    StringBuffer sb = new StringBuffer();
    sb.append("<div style='padding:8px;'>");
    if (StringUtils.isNotEmpty(e.getMessage())) {
      sb.append("<div style='font-style:italic;'>");
      sb.append(HTMLUtil.inactivateAndNewline(e.getMessage()));
      sb.append("</div>");
    }
    sb.append("<br/>");

    sb.append("<div style='font-weight:bold;'>Part id</div>");
    sb.append("<div style='font-style:italic;'>");
    sb.append(HTMLUtil.inactivateAndNewline(getId()));
    sb.append("</div>");
    sb.append("<br/>");

    String stackTrace = ExceptionUtil.dumpStackTraceHTML(e);
    if (StringUtils.isNotEmpty(stackTrace)) {
      sb.append("<div style='font-weight:bold;'>Stacktrace</div>");
      sb.append(stackTrace);
    }
    sb.append("</div>");
    return sb.toString();
  }

  @Override
  public Widget getWidget() {
    return root;
  }
}
