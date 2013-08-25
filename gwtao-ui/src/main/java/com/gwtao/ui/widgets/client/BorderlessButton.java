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
package com.gwtao.ui.widgets.client;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.gwtao.ui.util.client.GWTUtils;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class BorderlessButton extends Button {
  public static final int ICON = 0x01;
  public static final int TEXT = 0x02;
  public static final int ICONANDTEXT = 0x03;
  public static final int UNDERLINE = 0x04;

  private String title;
  private String iconClass;
  private int offset;
  private boolean underline;
  private IAction action;

  public BorderlessButton(final IAction action) {
    this(action, ICONANDTEXT);
  }

  public BorderlessButton(final IAction action, int style) {
    this((style & TEXT) == 0 ? null : action.getDisplayTitle(), action.getDisplayIcon(), 0, (style & UNDERLINE) == UNDERLINE);
    init(action);
  }

  public BorderlessButton(final IAction action, int style, String altIcon) {
    this((style & TEXT) == 0 ? null : action.getDisplayTitle(), action.getDisplayIcon() == null ? altIcon : action.getDisplayIcon(), 0, (style & UNDERLINE) == UNDERLINE);
    init(action);
  }

  protected BorderlessButton(String title) {
    this(title, null);
  }

  public BorderlessButton(String title, String iconClass) {
    this(title, iconClass, 0, false);
  }

  public BorderlessButton(String title, String iconClass, int offset, boolean underline) {
    if (StringUtils.isBlank(title) && StringUtils.isBlank(iconClass))
      throw new IllegalArgumentException("Missing title and/or icon to create button");

    this.title = title;
    this.iconClass = iconClass;
    this.offset = offset;
    this.underline = underline;

    update();

    DOM.setStyleAttribute(getElement(), "background", "transparent");
    DOM.setStyleAttribute(getElement(), "border", "0");
  }

  private void init(final IAction action) {
    this.action = action;

    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (action.getPermission() == Permission.ALLOWED)
          ActionUtil.saveExecute(GWTUtils.createEventInfo(event.getNativeEvent()), action);
      }
    });

    action.getWidgetHandler().addAdapter(new ActionFocusWidgetAdapter(action, this, null));
  }

  private void update() {
    StringBuilder buff = new StringBuilder();

    String style = "gwtaf-action-item";
    if (title != null) {
      // int lines = StringUtil.count(title.toLowerCase(), "<br>");
      // if (lines > 1) {
      // DOM.setStyleAttribute(getElement(), "height", String.valueOf(16 * lines));
      // style = "gwtaf-action-item-multiline";
      // }
    }

    buff.append("<div class=\"");
    if (!StringUtils.isBlank(iconClass)) {
      buff.append(style + " gwtaf-action-item-icon ");
      if (!isEnabled())
        buff.append("gwtaf-action-item-icon-readonly ");
      buff.append(iconClass);
    }
    else
      buff.append(style);

    buff.append("\"");

    if (offset > 0) {
      buff.append(" style=\"margin-left:");
      buff.append(offset);
      buff.append(";\"");
    }

    if (StringUtils.isNotBlank(title)) {
      buff.append(">");
      if (underline)
        buff.append("<ul>");
      buff.append(title);
      if (underline)
        buff.append("</ul>");
      buff.append("</div>");
    }
    else
      buff.append("/>");

    setHTML(buff.toString());
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    update();
  }

  public IAction getAction() {
    return action;
  }
}
