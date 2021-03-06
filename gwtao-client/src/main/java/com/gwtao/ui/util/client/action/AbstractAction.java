/* 
 * Copyright 2012 GWTAO
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

public abstract class AbstractAction implements IAction {
  private final ActionWidgetHandler handler = new ActionWidgetHandler();

  private IPermissionDelegate delegate;

  protected AbstractAction() {
  }

  public AbstractAction setPermissionDelegate(IPermissionDelegate delegate) {
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
