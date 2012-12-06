package com.gwtao.app.client;

import com.google.gwt.user.client.ui.IsWidget;

public interface IDocument extends IsWidget {

  void init();

  String canClose();

  boolean deactivate();
}
