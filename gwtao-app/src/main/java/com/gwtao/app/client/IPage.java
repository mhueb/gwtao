package com.gwtao.app.client;

import com.google.gwt.user.client.ui.IsWidget;

public interface IPage extends IsWidget {

  void init(IPageContext ctx);

  String canClose();

  boolean deactivate();
}
