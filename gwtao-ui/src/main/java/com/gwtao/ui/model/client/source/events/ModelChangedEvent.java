package com.gwtao.ui.model.client.source.events;

public class ModelChangedEvent extends AbstractModelSourceEvent<ModelChangedEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractModelSourceEvent.Handler {
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
