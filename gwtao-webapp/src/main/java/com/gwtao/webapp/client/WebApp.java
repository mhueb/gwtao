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

import org.apache.commons.lang.StringUtils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.gwtao.ui.layout.client.RootLayoutPanel;
import com.gwtao.ui.location.client.IPresenterManager;
import com.gwtao.ui.location.client.LocationManager;
import com.gwtao.ui.location.client.Token;
import com.gwtao.ui.location.client.UnkownLocationException;

public abstract class WebApp implements IPresenterManager<PageContext> {

  private IAppFrame frame;

  private final LocationManager<PageContext> locationManager;

  private final EventBus eventbus;

  private String appTitle;

  protected WebApp() {
    this.locationManager = new LocationManager<PageContext>(this);
    this.eventbus = new SimpleEventBus();
  }

  protected void initFrame(IAppFrame frame) {
    this.frame = frame;
  }

  @Override
  public boolean beforeChange(Token token) {
    if (!PageFactoryRegistry.REGISTRY.hasToken(token.getName()))
      throw new UnkownLocationException(token);
    return false;
  }

  @Override
  public final PageContext createPresenter(Token token) {
    IPage document = createPage(token.getName());
    return new PageContext(eventbus, frame, document, token);
  }

  protected IPage createPage(String id) {
    return PageFactoryRegistry.REGISTRY.create(id);
  }

  @Override
  public void activate(PageContext presenter) {
    presenter.show();
    updateWindowTitle(presenter.asDisplayableItem().getDisplayTitle());
  }

  @Override
  public boolean deactivate(PageContext presenter) {
    presenter.close();
    return true;// presenter.deactivate();
  }

  @Override
  public PageContext createErrorPresenter(Token token, String errorMessage) {
    IPage page = createErrorPage(token, errorMessage);
    return new PageContext(eventbus, frame, page, token);
  }

  protected abstract IPage createErrorPage(Token token, String errorMessage);

  @Override
  public String canClose(PageContext presenter) {
    return presenter.canClose();
  }

  public void proceedChange() {
    locationManager.proceedChange();
  }

  public void startup() {
    appTitle = StringUtils.trimToNull(Document.get().getTitle());
    RootLayoutPanel.get().add(frame);
    this.locationManager.startup();
  }

  public EventBus getEventBus() {
    return eventbus;
  }

  protected void updateWindowTitle(String title) {
    if (appTitle != null) {
      String windowTitle = appTitle;
      if (StringUtils.isNotBlank(title)) {
        windowTitle = windowTitle + " - " + title;
      }
      Document.get().setTitle(windowTitle);
    }
  }
}
