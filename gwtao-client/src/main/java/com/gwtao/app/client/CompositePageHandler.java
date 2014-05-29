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
