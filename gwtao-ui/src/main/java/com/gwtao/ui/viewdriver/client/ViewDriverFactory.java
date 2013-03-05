package com.gwtao.ui.viewdriver.client;


public interface ViewDriverFactory<M, V> {
  IViewDriver<M> generateDriver(V view);
}
