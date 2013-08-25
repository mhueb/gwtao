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
package com.gwtao.ui.util.client.action;

import org.shu4j.utils.permission.IPermissionDelegate;
import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.util.client.IDisplayableItem;

public abstract class Action implements IAction {
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
   */
  public Action(IDisplayableItem info) {
    this.title = info.getDisplayTitle();
    this.tooltip = info.getDisplayTooltip();
    this.icon = info.getDisplayIcon();
  }

  public String getDisplayIcon() {
    return icon;
  }

  public String getDisplayTitle() {
    return title;
  }

  public String getDisplayTooltip() {
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
