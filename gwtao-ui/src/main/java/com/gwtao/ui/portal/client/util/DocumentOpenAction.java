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

import com.gwtao.common.shared.permission.IPermissionDelegate;
import com.gwtao.common.shared.permission.Permission;
import com.gwtao.ui.portal.client.Portal;
import com.gwtao.ui.portal.client.document.IDocumentDescriptor;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.ActionWidgetHandler;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionWidgetHandler;

public class DocumentOpenAction implements IAction {

  private final IActionWidgetHandler handler = new ActionWidgetHandler();
  private final IDocumentDescriptor descr;
  private final IPermissionDelegate delegate;

  public DocumentOpenAction(IDocumentDescriptor descr, IPermissionDelegate delegate) {
    this.descr = descr;
    this.delegate = delegate;
  }

  public DocumentOpenAction(IDocumentDescriptor descr) {
    this(descr, IPermissionDelegate.ALLOWED);
  }

  @Override
  public void execute(Object... data) {
    Portal.get().openDocument(descr.getId(), data.length == 1 ? data[0] : null, ActionUtil.getLastEventInfo() != null && ActionUtil.getLastEventInfo().isCtrl());
  }

  @Override
  public Permission getPermission(Object... data) {
    return delegate.getPermission(data);
  }

  @Override
  public String getIcon() {
    return this.descr.getIcon();
  }

  @Override
  public String getTitle() {
    return this.descr.getTitle();
  }

  @Override
  public String getTooltip() {
    return descr.getTooltip();
  }

  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return handler;
  }

}
