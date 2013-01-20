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

import com.google.gwt.event.shared.EventBus;
import com.gwtao.ui.location.client.Location;
import com.gwtao.ui.util.client.IDisplayableItem;

public class PageContext implements IPageContext {

  private final IPage document;
  private final Location location;
  private final IAppFrame frame;
  private final EventBus eventBus;

  public PageContext(EventBus eventBus, IAppFrame frame, IPage document, Location location) {
    this.eventBus = eventBus;
    this.frame = frame;
    this.document = document;
    this.location = location;
    document.init(this);
  }

  public IDisplayableItem asDisplayableItem() {
    return document.asDisplayableItem();
  }

  @Override
  public void switchParameter(String parameter) {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() {
    frame.close(document);
  }

  @Override
  public void show() {
    frame.show(document);
    document.activate();
  }

  @Override
  public void updateTitle() {
    frame.updateTitle(document);
    frame.getApp().updateWindowTitle(asDisplayableItem().getTitle());
  }

  public String canClose() {
    return document.canClose();
  }

  @Override
  public String getParameter() {
    return location.getParameters();
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }
}