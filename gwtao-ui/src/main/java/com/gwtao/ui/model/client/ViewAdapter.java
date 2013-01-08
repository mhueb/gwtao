package com.gwtao.ui.model.client;

import com.gwtao.ui.model.client.mgr.IViewManager;

public interface ViewAdapter<M, V> {
  IViewManager<M> create(V view);
}
