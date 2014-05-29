/* 
 * Copyright 2012 GWTAO
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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