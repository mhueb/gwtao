package com.gwtao.ui.task.client.events;

import com.gwtao.ui.data.client.source.events.AbstractDataSourceEvent;

public class TaskRefreshEvent extends AbstractTaskEvent<TaskRefreshEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractDataSourceEvent.Handler {
    void onTaskRefresh(boolean pre);
  }

  public TaskRefreshEvent(boolean pre) {
    super(pre);
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onTaskRefresh(isPre());
  }
}
