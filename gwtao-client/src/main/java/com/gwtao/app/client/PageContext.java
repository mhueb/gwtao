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
package com.gwtao.app.client;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.permission.IPermissionProvider;

import com.google.gwt.event.shared.EventBus;
import com.gwtao.ui.place.client.Token;
import com.gwtao.ui.util.client.IDisplayableItem;

public final class PageContext implements IPageContext {

  private final EventBus eventBus;
  private final IPage page;
  private final Token token;
  private final IPageController pagesCtrl;
  private final IPermissionProvider privilegeProvider;

  public PageContext(EventBus eventBus, IPermissionProvider privilegeProvider, IPageController pagesCtrl, IPage page, Token token) {
    this.eventBus = eventBus;
    this.privilegeProvider = privilegeProvider;
    this.pagesCtrl = pagesCtrl;
    this.page = page;
    this.token = token;
    page.init(this);
    updateTitle();
  }

  public IDisplayableItem asDisplayableItem() {
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
    pagesCtrl.close(page);
  }

  @Override
  public void show() {
    pagesCtrl.show(page);
    updateTitle();
  }

  @Override
  public void updateTitle() {
    pagesCtrl.updateTitle(page);
  }

  @Override
  public String canClose() {
    return page.canClose();
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
}