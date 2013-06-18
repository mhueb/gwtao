package com.gwtao.ui.task.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IAsyncTaskPerformer<M> {
  String getDisplayTitle();

  String getWaitMessage();

  void perform(M model, AsyncCallback<M> callback);
}
