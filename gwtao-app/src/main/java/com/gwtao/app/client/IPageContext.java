package com.gwtao.app.client;

public interface IPageContext {
  void switchParameter(String parameter);

  String getParameter();

  void close();

  void show();

  void updateTitle(String title);
}
