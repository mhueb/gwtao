package com.gwtao.app.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.gwtao.ui.layout.client.ILayoutContainer;
import com.gwtao.ui.util.client.Size;

public abstract class AbstractPage extends Composite implements IPage, RequiresResize, ProvidesResize, ILayoutContainer {

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
  public void activate() {
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

  @Override
  public Size getMinSize() {
    if (getWidget() instanceof ILayoutContainer) {
      return ((ILayoutContainer) getWidget()).getMinSize();
    }
    else
      return Size.ZERO;
  }

  @Override
  public void layout() {
    if (getWidget() instanceof ILayoutContainer) {
      ((ILayoutContainer) getWidget()).layout();
    }
  }

  @Override
  public void measure() {
    if (getWidget() instanceof ILayoutContainer) {
      ((ILayoutContainer) getWidget()).measure();
    }
  }

}
