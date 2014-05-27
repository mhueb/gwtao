package com.gwtao.app.client;

import com.google.gwt.user.client.ui.Composite;

public class PageWrapper extends Composite {
  private IPageContext pageCtx;

  public PageWrapper(IPageContext page) {
    this.pageCtx = page;
    initWidget(page.getPage().asWidget());
  }

  public IPageContext getPageCtx() {
    return pageCtx;
  }

  public void onHide() {
    pageCtx.getHandler().onHide();
  }

  public void onShow() {
    pageCtx.getHandler().onShow();
  }

  public void onClose() {
    pageCtx.getHandler().onClose();
  }
}