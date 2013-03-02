package com.gwtao.ui.data.client.source.events;

public class DataLoadEvent extends AbstractDataSourceEvent<DataLoadEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractDataSourceEvent.Handler {
    void onDataLoading();

    void onDataLoaded(boolean success);
  }

  private final boolean loading;
  private final Throwable error;

  public DataLoadEvent() {
    this.loading = true;
    this.error = null;
  }

  public DataLoadEvent(Throwable error) {
    this.loading = false;
    this.error = error;
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    if (loading)
      handler.onDataLoading();
    else
      handler.onDataLoaded(error == null);
  }
}
