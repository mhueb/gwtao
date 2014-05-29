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

import com.gwtao.ui.util.client.displayable.IDisplayableItem;

public interface IAction extends IDisplayableItem {
  final Object[] NODATA = new Object[0];

  void execute(Object... data);

  Permission getPermission(Object... data);

  IActionWidgetHandler getWidgetHandler();

  IAction setPermissionDelegate(IPermissionDelegate delegate);
}
