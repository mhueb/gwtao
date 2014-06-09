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
package com.gwtao.app.client;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.permission.IPermissionProvider;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.gwtao.ui.history.client.Token;
import com.gwtao.ui.util.client.displayable.HasDisplayText;
import com.gwtao.ui.util.client.mask.IWaitMask;
import com.gwtao.ui.util.client.mask.WaitMask;

public final class PageContext implements IPageContext {

  private final EventBus eventBus;
  private final IPage page;
  private final Token token;
  private final IPageController pagesCtrl;
  private final IPermissionProvider privilegeProvider;
  private final CompositePageHandler handler = new CompositePageHandler();
  private WaitMask waitMask;

  public PageContext(EventBus eventBus, IPermissionProvider privilegeProvider, IPageController pagesCtrl, IPage page, Token token) {
    this.eventBus = eventBus;
    this.privilegeProvider = privilegeProvider;
    this.pagesCtrl = pagesCtrl;
    this.page = page;
    this.token = token;
    page.init(this);
    updateTitle();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        handler.onInit();
      }
    });
  }

  public IPage getPage() {
    return page;
  }

  public HasDisplayText asDisplayableTitle() {
    return page;
  }

  @Override
  public void switchToken(String parameter) {
    throw new NotImplementedException();
    // Token newToken = TokenUtils.buildToken(token.getName(), parameter);
    // if (!newToken.equals(token)) {
    // this.locationManager.switchToken(this, token);
    // this.token = newToken;
    // }
  }

  @Override
  public void close() {
    pagesCtrl.close(this);
  }

  @Override
  public void show() {
    pagesCtrl.show(this);
    updateTitle();
  }

  @Override
  public void updateTitle() {
    pagesCtrl.updateTitle(this);
  }

  @Override
  public String canClose() {
    return handler.canClose();
  }

  @Override
  public String getParameter() {
    return StringUtils.trimToEmpty(token.getParameters());
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }

  @Override
  public IPermissionProvider getPermissionProvider() {
    return privilegeProvider;
  }

  public IWaitMask getWaitMask() {
    if (waitMask == null)
      waitMask = new WaitMask(page);
    return waitMask;
  }

  @Override
  public IPageHandler getHandler() {
    return handler;
  }

  @Override
  public void addHandler(IPageHandler handler) {
    this.handler.add(handler);
  }
}