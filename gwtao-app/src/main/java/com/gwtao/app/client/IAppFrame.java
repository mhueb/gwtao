package com.gwtao.app.client;

import com.google.gwt.user.client.ui.IsWidget;

public interface IAppFrame extends IsWidget {
  void close(IPage page);

  void show(IPage page);

  void updateTitle(IPage page, String title);

}
