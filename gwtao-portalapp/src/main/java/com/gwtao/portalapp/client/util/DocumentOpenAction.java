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
package com.gwtao.portalapp.client.util;

import org.shu4j.utils.permission.IPermissionDelegate;
import org.shu4j.utils.permission.Permission;

import com.gwtao.portalapp.client.PortalApp;
import com.gwtao.portalapp.client.document.IDocumentDescriptor;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.ActionWidgetHandler;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionWidgetHandler;

public class DocumentOpenAction implements IAction {

  private final IActionWidgetHandler handler = new ActionWidgetHandler();
  private final IDocumentDescriptor descr;
  private IPermissionDelegate delegate;

  public DocumentOpenAction(IDocumentDescriptor descr) {
    this.descr = descr;
    this.delegate = Permission.ALLOWED;
  }

  @Override
  public void execute(Object... data) {
    PortalApp.get().openDocument(descr.getId(), data.length == 1 ? data[0] : null, ActionUtil.getLastEventInfo() != null && ActionUtil.getLastEventInfo().isCtrl());
  }

  @Override
  public Permission getPermission(Object... data) {
    return delegate.getPermission(data);
  }

  @Override
  public String getDisplayIcon() {
    return this.descr.getDisplayIcon();
  }

  @Override
  public String getDisplayText() {
    return this.descr.getDisplayText();
  }

  @Override
  public String getDisplayTooltip() {
    return descr.getDisplayTooltip();
  }

  @Override
  public IActionWidgetHandler getWidgetHandler() {
    return handler;
  }

  @Override
  public IAction setPermissionDelegate(IPermissionDelegate delegate) {
    this.delegate = delegate;
    return this;
  }
}
