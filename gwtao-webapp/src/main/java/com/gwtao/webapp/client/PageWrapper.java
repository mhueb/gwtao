package com.gwtao.webapp.client;

import com.google.gwt.user.client.ui.Composite;

public class PageWrapper extends Composite {
  private IPage page;

  public PageWrapper(IPage page) {
    this.page = page;
    initWidget(page.asWidget());
  }

  public IPage getPage() {
    return page;
  }

  public void onHide() {
    page.onHide();
  }

  public void onShow() {
    page.onShow();
  }

  public void onClose() {
    page.onClose();
  }
}