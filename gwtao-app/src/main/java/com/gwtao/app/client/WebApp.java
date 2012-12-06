package com.gwtao.app.client;

import com.gwtao.ui.location.client.IPresenterManager;
import com.gwtao.ui.location.client.Location;

public class WebApp implements IPresenterManager<IDocument> {

  @Override
  public IDocument createPresenter(Location token) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void activate(IDocument location) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean deactivate(IDocument location) {
    return location.deactivate();
  }

  @Override
  public IDocument createErrorPresenter(Location token, String errorMessage) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String canClose(IDocument location) {
    return location.canClose();
  }

  public void startup() {
    // TODO Auto-generated method stub

  }

}
