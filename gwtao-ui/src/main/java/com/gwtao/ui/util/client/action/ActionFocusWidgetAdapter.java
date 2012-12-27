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
