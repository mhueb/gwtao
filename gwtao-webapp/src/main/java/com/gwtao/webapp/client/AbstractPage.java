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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

public abstract class AbstractPage extends Composite implements IPage, RequiresResize, ProvidesResize {

  private IPageContext ctx;

  @Override
  public final void init(IPageContext ctx) {
    this.ctx = ctx;
    init();
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        deferedInit();
      }
    });
  }

  protected void init() {
  }

  protected final IPageContext getCtx() {
    return ctx;
  }

  protected void deferedInit() {
  }

  @Override
  public String canClose() {
    return null;
  }

  @Override
  public void onShow() {
  }

  @Override
  public void onHide() {
  }

  @Override
  public void onClose() {
  }

  @Override
  public void onResize() {
    if (getWidget() instanceof RequiresResize) {
      ((RequiresResize) getWidget()).onResize();
    }
  }

  @Override
  public String getDisplayIcon() {
    return null;
  }

  @Override
  public String getDisplayTooltip() {
    return null;
  }
}
