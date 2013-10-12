package com.gwtao.ui.util.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.data.client.source.AbstractDataSource;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.events.DataLoadEvent;

public class CollectionPool {
  private static CollectionPool INSTANCE;

  public static CollectionPool get() {
    if (INSTANCE == null)
      INSTANCE = new CollectionPool();
    return INSTANCE;
  }

  public interface IAsyncLoader<T> {
    void loadAsync(AsyncCallback<List<T>> callback);
  }

  private static class Loader<T> extends AbstractDataSource<List<T>> implements AsyncCallback<List<T>> {
    private final IAsyncLoader<T> loader;

    private List<T> data = Collections.emptyList();

    private boolean needLoad = true;

    public Loader(IAsyncLoader<T> loader) {
      this.loader = loader;
    }

    @Override
    public List<T> getData() {
      triggerLoad();
      return data;
    }

    private void triggerLoad() {
      if (needLoad) {
        fireEvent( new DataLoadEvent());
        loader.loadAsync(this);
      }
      needLoad = false;
    }

    @Override
    public void onFailure(Throwable caught) {
      fireEvent( new DataLoadEvent(caught));
    }

    @Override
    public void onSuccess(List<T> result) {
      data = result;
      fireEvent( new DataLoadEvent(null));
      fireDataChanged();
    }
  }

  @SuppressWarnings("rawtypes")
  private final Map<Class, Loader> loaderMap = new HashMap<Class, Loader>();

  @SuppressWarnings({
      "rawtypes",
      "unchecked" })
  public <T> void addLoader(IAsyncLoader<T> loader, Class<T> type) {
    loaderMap.put(type, new Loader(loader));
  }

  @SuppressWarnings({
      "rawtypes",
      "unchecked" })
  public <T> IDataSource<List<T>> getSource(Class<T> type) {
    Loader loader = loaderMap.get(type);
    Validate.notNull(loader);
    return loader;
  }
}
