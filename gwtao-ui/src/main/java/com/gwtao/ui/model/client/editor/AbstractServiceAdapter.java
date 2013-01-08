package com.gwtao.ui.model.client.editor;

import org.apache.commons.lang.NotImplementedException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.util.client.ParameterList;

public abstract class AbstractServiceAdapter<M> implements IServiceAdapter<M> {
  @Override
  public boolean isCreate(ParameterList params) {
    return params.isEmpty();
  }
  
  public void create(M model, AsyncCallback<M> callback) {
    callback.onFailure(new NotImplementedException());
  }

  public void read(ParameterList params, AsyncCallback<M> callback) {
    callback.onFailure(new NotImplementedException());
  }

  public void update(M model, AsyncCallback<M> callback) {
    callback.onFailure(new NotImplementedException());
  }

  public void remove(M model, AsyncCallback<Void> callback) {
    callback.onFailure(new NotImplementedException());
  }
}
