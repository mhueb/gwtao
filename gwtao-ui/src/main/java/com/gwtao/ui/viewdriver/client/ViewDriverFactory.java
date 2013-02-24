package com.gwtao.ui.viewdriver.client;


public interface ViewDriverFactory<M, V> {
  IViewDriver<M> create(V view);
}
