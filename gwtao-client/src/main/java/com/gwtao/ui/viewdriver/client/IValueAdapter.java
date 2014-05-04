package com.gwtao.ui.viewdriver.client;

public interface IValueAdapter<M, V> {
  void setValue(M m, V v);

  V getValue(M m);
}
