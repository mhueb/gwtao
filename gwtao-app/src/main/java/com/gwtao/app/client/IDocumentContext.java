package com.gwtao.app.client;


public interface IDocumentContext {
  void switchParameter(String parameter);

  void close();

  void show();

  void update(String title);
}
