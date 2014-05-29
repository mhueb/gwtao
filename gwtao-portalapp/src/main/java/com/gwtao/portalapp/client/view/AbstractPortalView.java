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
package com.gwtao.portalapp.client.view;

public abstract class AbstractPortalView implements IPortalView {

  private IPortalViewContext context;

  @Override
  public final void setViewContext(IPortalViewContext context) {
    if (this.context != null)
      throw new IllegalStateException("context change not expected");
    this.context = context;
  }

  @Override
  public final IPortalViewContext getViewContext() {
    return context;
  }

  @Override
  public void onActivate() {
  }

  @Override
  public void onOpen() {
  }

  @Override
  public void onClose() {
  }

  @Override
  public void onDeactivate() {
  }

  @Override
  public boolean isClosable() {
    return true;
  }

  @Override
  public String canClose() {
    return null;
  }
}
