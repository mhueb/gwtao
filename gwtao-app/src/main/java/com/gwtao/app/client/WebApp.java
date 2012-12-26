package com.gwtao.app.client;

import com.gwtao.ui.layout.client.RootLayoutPanel;
import com.gwtao.ui.location.client.IPresenterManager;
import com.gwtao.ui.location.client.Location;
import com.gwtao.ui.location.client.LocationManager;

public class WebApp implements IPresenterManager<PageContext> {

  private final IAppFrame frame;

  private final LocationManager<PageContext> manager;

  public WebApp(IAppFrame frame) {
    this.frame = frame;
    this.manager = new LocationManager<PageContext>(this);
  }

  @Override
  public boolean beforeChange(Location location) {
    return true;
  }

  @Override
  public final PageContext createPresenter(Location location) {
    IPage document = createPage(location.getId());
    return new PageContext(frame, document, location);
  }

  protected IPage createPage(String id) {
    return Pages.REGISTRY.create(id);
  }

  @Override
  public void activate(PageContext presenter) {
    presenter.show();
  }

  @Override
  public boolean deactivate(PageContext presenter) {
    return true;// presenter.deactivate();
  }

  @Override
  public PageContext createErrorPresenter(Location location, String errorMessage) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String canClose(PageContext presenter) {
    return presenter.canClose();
  }

  public void startup() {
    RootLayoutPanel.get().add(frame);
    this.manager.startup();
  }

}
