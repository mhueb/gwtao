package com.gwtao.app.client;

import com.gwtao.ui.location.client.Location;

public class PageContext implements IPageContext {

  private final IPage document;
  private final Location location;
  private final IAppFrame frame;

  public PageContext(IAppFrame frame, IPage document, Location location) {
    this.frame = frame;
    this.document = document;
    this.location = location;
    document.init(this);
  }

  @Override
  public void switchParameter(String parameter) {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() {
    frame.close(document);
  }

  @Override
  public void show() {
    frame.show(document);
  }

  @Override
  public void updateTitle(String title) {
    frame.updateTitle(document, title);
  }

  public String canClose() {
    return document.canClose();
  }

  @Override
  public String getParameter() {
    return location.getParameters();
  }

}
