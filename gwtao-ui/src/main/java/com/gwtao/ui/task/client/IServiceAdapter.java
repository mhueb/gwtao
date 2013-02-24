package com.gwtao.ui.task.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.util.client.ParameterList;

public interface IServiceAdapter<M> {
  ParameterList toParam(M model);

  void read(ParameterList params, AsyncCallback<M> callback);

  void execute(M model, AsyncCallback<M> callback);
}