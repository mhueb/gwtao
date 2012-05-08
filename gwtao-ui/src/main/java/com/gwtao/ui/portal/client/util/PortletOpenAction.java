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
package com.gwtao.ui.portal.client.util;

import com.gwtao.common.shared.permission.Permission;
import com.gwtao.ui.portal.client.Portal;
import com.gwtao.ui.portal.client.portlet.IPortletDescriptor;
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
    Portal.get().openPortlet(pd.getId());
  }

  @Override
  public Permission getPermission(Object... data) {
    return Portal.get().getPortlet(pd.getId()) == null ? Permission.ALLOWED : Permission.UNALLOWED;
  }

  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return handler;
  }
}
