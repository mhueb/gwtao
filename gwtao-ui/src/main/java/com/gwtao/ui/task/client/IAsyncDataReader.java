package com.gwtao.ui.task.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IAsyncDataReader<P, M> {
  void read(P param, AsyncCallback<M> callback);
}