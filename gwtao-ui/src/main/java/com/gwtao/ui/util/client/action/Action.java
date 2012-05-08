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
package com.gwtao.ui.util.client.action;

import com.gwtao.common.shared.permission.IPermissionDelegate;
import com.gwtao.common.shared.permission.Permission;


public abstract class Action implements IPrivilegedAction {
  private final ActionWidgetHandler handler = new ActionWidgetHandler();

  private IPermissionDelegate delegate;

  private String icon;
  private String title;
  private String tooltip;

  protected Action() {
  }

  /**
   * @param title Title of the action
   */
  public Action(String title) {
    this.title = title;
  }

  /**
   * @param title Title of the action
   * @param icon Icon css class
   */
  public Action(String title, String icon) {
    this.title = title;
    this.icon = icon;
  }

  /**
   * @param title Title of the action
   * @param icon Icon css class
   * @param tooltip
   */
  public Action(String title, String icon, String tooltip) {
    this.title = title;
    this.icon = icon;
    this.tooltip = tooltip;
  }

  public String getIcon() {
    return icon;
  }

  public String getTitle() {
    return title;
  }

  public String getTooltip() {
    return tooltip;
  }

  public Action setPermissionDelegate(IPermissionDelegate delegate) {
    this.delegate = delegate;
    return this;
  }

  public Permission getPermission(Object... data) {
    if (delegate != null)
      return delegate.getPermission(data);
    return Permission.ALLOWED;
  }

  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return handler;
  }
}
