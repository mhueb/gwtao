package com.gwtao.ui.model.client.editor;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.util.client.ParameterList;

public interface IServiceAdapter<M> {
  ParameterList toParam(M model);

  void create(M model, AsyncCallback<M> callback);

  void read(ParameterList params, AsyncCallback<M> callback);

  void update(M model, AsyncCallback<M> callback);

  void remove(M model, AsyncCallback<Void> callback);

  boolean isCreate(ParameterList params);

}