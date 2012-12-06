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
package com.gwtao.portalapp.client.util;

import org.shu4j.utils.permission.Permission;

import com.gwtao.portalapp.client.PortalApp;
import com.gwtao.portalapp.client.portlet.IPortletDescriptor;
import com.gwtao.ui.util.client.action.ActionWidgetHandler;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionWidgetHandler;

public class PortletOpenAction implements IAction {

  private final IActionWidgetHandler handler = new ActionWidgetHandler();
  private final IPortletDescriptor pd;

  public PortletOpenAction(IPortletDescriptor pd) {
    this.pd = pd;
  }

  @Override
  public String getTitle() {
    return pd.getTitle();
  }

  @Override
  public String getIcon() {
    return pd.getIcon();
  }

  @Override
  public String getTooltip() {
    return pd.getTooltip();
  }

  @Override
  public void execute(Object... data) {
    PortalApp.get().openPortlet(pd.getId());
  }

  @Override
  public Permission getPermission(Object... data) {
    return PortalApp.get().getPortlet(pd.getId()) == null ? Permission.ALLOWED : Permission.READONLY;
  }

  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return handler;
  }
}
