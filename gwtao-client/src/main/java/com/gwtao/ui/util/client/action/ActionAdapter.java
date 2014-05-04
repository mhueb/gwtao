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

public class ActionAdapter implements IAction {
  private final IAction action;

  public ActionAdapter(IAction action) {
    this.action = action;
    if (this.action == null)
      throw new IllegalArgumentException("action is null.");
  }

  public Permission getPermission(Object... data) {
    return action.getPermission(data);
  }

  public void execute(Object... data) {
    action.execute(data);
  }

  public String getDisplayIcon() {
    return this.action.getDisplayIcon();
  }

  public String getDisplayTitle() {
    return this.action.getDisplayTitle();
  }

  public String getDisplayTooltip() {
    return this.action.getDisplayTooltip();
  }

  public IAction getAction() {
    return action;
  }


  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return action.getWidgetHandler();
  }

  @Deprecated
  public void enforcePermission() {
    getWidgetHandler().update();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) || obj instanceof ActionAdapter && isEqual((ActionAdapter) obj);
  }

  protected boolean isEqual(ActionAdapter obj) {
    return this.action.equals(obj.action);
  }

  @Override
  public int hashCode() {
    return action.hashCode();
  }
  
  @Override
  public IAction setPermissionDelegate(IPermissionDelegate delegate) {
    action.setPermissionDelegate(delegate);
    return this;
  }
}
