package com.gwtao.ui.task.client.events;

import com.gwtao.ui.data.client.source.events.AbstractDataSourceEvent;

public abstract class AbstractTaskEvent<H extends AbstractDataSourceEvent.Handler> extends AbstractDataSourceEvent<H> {
  private boolean pre;

  protected AbstractTaskEvent(boolean pre) {
    this.pre = pre;
  }

  public boolean isPre() {
    return pre;
  }
}
