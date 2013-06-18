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
package com.gwtao.webapp.client;

import com.google.gwt.event.shared.EventBus;
import com.gwtao.ui.location.client.Token;
import com.gwtao.ui.location.client.TokenUtils;
import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.ParameterList;

public class PageContext implements IPageContext {

  private final IPage page;
  private final Token token;
  private final IAppFrame frame;
  private final EventBus eventBus;

  public PageContext(EventBus eventBus, IAppFrame frame, IPage page, Token token) {
    this.eventBus = eventBus;
    this.frame = frame;
    this.page = page;
    this.token = token;
    page.init(this);
  }

  public IDisplayableItem asDisplayableItem() {
    return page;
  }

  @Override
  public void switchToken(ParameterList parameter) {
    // Token newToken = TokenUtils.buildToken(token.getName(), parameter);
    // if (!newToken.equals(token)) {
    // this.locationManager.switchToken(this, token);
    // this.token = newToken;
    // }
  }

  @Override
  public void close() {
    frame.close(page);
  }

  @Override
  public void show() {
    frame.show(page);
    page.activate();
  }

  @Override
  public void updateTitle() {
    frame.updateTitle(page);
    frame.getApp().updateWindowTitle(asDisplayableItem().getDisplayTitle());
  }

  public String canClose() {
    return page.canClose();
  }

  @Override
  public String getParameter() {
    return token.getParameters();
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }
}