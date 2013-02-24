package com.gwtao.ui.data.client.source.events;

public class DataChangedEvent extends AbstractDataSourceEvent<DataChangedEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractDataSourceEvent.Handler {
    void onModelChanged();
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onModelChanged();
  }
}
