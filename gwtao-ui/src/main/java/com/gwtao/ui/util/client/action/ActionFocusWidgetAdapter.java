/* 
 * GWTAF - GWT Application Framework
 * 
 * Copyright (C) 2008-2010 Matthias Huebner.
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
 * $Id: ActionFocusWidgetAdapter.java 660 2011-05-19 20:57:53Z mattknight $
 */
package com.gwtao.ui.util.client.action;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.FocusWidget;

public class ActionFocusWidgetAdapter implements IActionWidgetAdapter {
  private final FocusWidget widget;
  private final IAction action;
  private final Object[] data;

  public ActionFocusWidgetAdapter(IAction action, FocusWidget widget, Object... data) {
    this.action = action;
    this.data = data;
    this.widget = widget;
  }

  @Override
  public void update() {
    setPermission(action.getPermission(data));
  }

  protected IAction getAction() {
    return action;
  }

  protected FocusWidget getWidget() {
    return widget;
  }

  protected Object[] getData() {
    return data;
  }

  private void setPermission(Permission permission) {
    switch (permission) {
    case ALLOWED:
      show(true);
      enable(true);
      break;
    case READONLY:
      show(true);
      enable(false);
      break;
    case HIDDEN:
      show(false);
      enable(false);
      break;
    }
  }

  protected void enable(boolean enable) {
    this.widget.setEnabled(enable);
  }

  protected void show(boolean show) {
    if (this.widget.isVisible() != show)
      this.widget.setVisible(show);
  }

}
