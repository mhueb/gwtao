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
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.layout.client.RootLayoutPanel;
import com.gwtao.ui.location.client.IPresenterManager;
import com.gwtao.ui.location.client.LocationManager;
import com.gwtao.ui.location.client.Token;
import com.gwtao.webapp.client.i18n.WebAppConstants;

public abstract class WebApp implements IWindowTitleSetter {

  private IPagesController pages;

  private final LocationManager<PageContext> locationManager;

  private final EventBus eventbus;

  private final String appTitle;

  private IsWidget frame;

  private IPresenterManager<PageContext> manager = new IPresenterManager<PageContext>() {

    @Override
    public void show(PageContext presenter) {
      presenter.show();
    }

    @Override
    public boolean locationChangeHook(Token token) {
      return WebApp.this.placeChangeHook(token);
    }

    @Override
    public boolean hide(PageContext presenter) {
      return true;
    }

    @Override
    public PageContext createPresenter(Token token) {
      IPage document = createPage(token.getName());
      return createContext(token, document);
    }

    @Override
    public PageContext createErrorPresenter(Token token, String errorMessage) {
      IPage page = createErrorPage(token, errorMessage);
      return createContext(token, page);
    }

    @Override
    public String canClose(PageContext presenter) {
      return presenter.canClose();
    }

    @Override
    public String canClose() {
      return null;
    }
  };

  protected WebApp() {
    this.locationManager = new LocationManager<PageContext>(manager);
    this.eventbus = new SimpleEventBus();
    this.appTitle = StringUtils.trimToNull(Document.get().getTitle());
  }

  protected void init(IsWidget frame, IPagesController pages) {
    this.pages = pages;
    this.frame = frame;
    RootLayoutPanel.get().add(frame);
  }

  public void replacePage(IPage page) {
    PageContext ctx = createContext(locationManager.getCurrentToken(), page);
    locationManager.replacePresenter(ctx);
  }

  private PageContext createContext(Token token, IPage document) {
    return new PageContext(eventbus, pages, document, token);
  }

  protected IPage createPage(String id) {
    return PageFactoryRegistry.REGISTRY.create(id);
  }

  protected abstract IPage createErrorPage(Token token, String errorMessage);

  protected boolean placeChangeHook(Token token) {
    return true;
  }

  public void startup() {
    startup(null);
  }

  public void startup(Token token) {
    locationManager.startup(token);
  }

  public EventBus getEventBus() {
    return eventbus;
  }

  public void updateWindowTitle(String title) {
    String windowTitle = appTitle;
    if (appTitle != null) {
      if (StringUtils.isNotBlank(title))
        windowTitle = windowTitle + " - " + title;
    }
    Document.get().setTitle(windowTitle);
  }

  public void rebuildPage() {
    locationManager.rebuildLocation();
  }

  public void reset() {
    locationManager.reset();
  }

  public IsWidget getFrame() {
    return frame;
  }
}
