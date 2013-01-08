package com.gwtao.rest.client;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MethodCallbackAdapter<T> implements MethodCallback<T> {

  private final AsyncCallback<T> source;

  private MethodCallbackAdapter(AsyncCallback<T> source) {
    this.source = source;
  }

  @Override
  public void onFailure(Method method, Throwable exception) {
    source.onFailure(exception);
  }

  public void onSuccess(Method method, T response) {
    source.onSuccess(response);
  };

  public static <T> MethodCallbackAdapter<T> create(AsyncCallback<T> source) {
    return new MethodCallbackAdapter<T>(source);
  }
}
