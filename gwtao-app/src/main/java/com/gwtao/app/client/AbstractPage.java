package com.gwtao.app.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

public class AbstractPage extends Composite implements IPage, RequiresResize, ProvidesResize {

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

  protected IPageContext getCtx() {
    return ctx;
  }

  protected void deferedInit() {
  }

  @Override
  public String canClose() {
    return null;
  }

  @Override
  public boolean deactivate() {
    return true;
  }

  @Override
  public void onResize() {
    if (getWidget() instanceof RequiresResize) {
      ((RequiresResize) getWidget()).onResize();
    }
  }

}
