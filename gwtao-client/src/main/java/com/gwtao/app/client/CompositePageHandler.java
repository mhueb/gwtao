package com.gwtao.app.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CompositePageHandler implements IPageHandler {
  private List<IPageHandler> list = new ArrayList<IPageHandler>();

  public void add(IPageHandler handler) {
    if (handler != null)
      list.add(handler);
  }

  @Override
  public void onInit() {
    for (IPageHandler ph : list)
      ph.onInit();
  }

  @Override
  public String canClose() {
    for (IPageHandler ph : list) {
      String canClose = ph.canClose();
      if (StringUtils.isNotBlank(canClose))
        return canClose;
    }
    return null;
  }

  @Override
  public void onShow() {
    for (IPageHandler ph : list)
      ph.onShow();
  }

  @Override
  public void onHide() {
    for (IPageHandler ph : list)
      ph.onHide();
  }

  @Override
  public void onClose() {
    for (IPageHandler ph : list)
      ph.onClose();
  }
}
