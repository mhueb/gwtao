package com.gwtao.ui.util.client;

public abstract class AsyncOKAnswere {
  public static final AsyncOKAnswere OK = new AsyncOKAnswere() {

    @Override
    public void onOk() {
    }
  };

  public abstract void onOk();
}
