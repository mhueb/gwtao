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
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.NullDataSource;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;

public class ActionFocusWidgetAdapter implements IActionWidgetAdapter {
  private final FocusWidget widget;
  private final IAction action;
  private final IDataSource<?> data;

  public ActionFocusWidgetAdapter(IAction action, FocusWidget widget, IDataSource<?> data) {
    this.action = action;
    this.data = data == null ? new NullDataSource<Object>(Permission.ALLOWED) : data;
    this.widget = widget;
    this.data.addHandler(new DataChangedEvent.Handler() {
      
      @Override
      public void onDataChanged() {
        update();
      }
    }, DataChangedEvent.TYPE);
  }

  @Override
  public void update() {
    Object arg = data.getData();
    if (arg instanceof Object[])
      setPermission(action.getPermission((Object[]) arg).add(data.getPermission()));
    else
      setPermission(action.getPermission(arg).add(data.getPermission()));
  }

  protected IAction getAction() {
    return action;
  }

  protected FocusWidget getWidget() {
    return widget;
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
