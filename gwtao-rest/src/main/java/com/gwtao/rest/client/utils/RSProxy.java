package com.gwtao.rest.client.utils;

import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

public abstract class RSProxy<T extends RestService> {

  public static Resource resource;

  private T service;

  public static void setResource(Resource resource) {
    RSProxy.resource = resource;
  }

  public T get() {
    if (service == null) {
      service = create();
      ((RestServiceProxy) service).setResource(resource);
    }
    return service;
  }

  protected abstract T create();
}
