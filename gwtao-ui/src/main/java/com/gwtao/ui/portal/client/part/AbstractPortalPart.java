/* 
 * Copyright 2012 Matthias Huebner
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
package com.gwtao.ui.portal.client.part;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractPortalPart implements IPortalPart {
  private final String id;
  private IPortalPartContext context;

  public AbstractPortalPart(String id) {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("invalid id");
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public final void init(IPortalPartContext context) {
    this.context = context;
    init();
  }

  protected void init() {
  }

  @Override
  public final IPortalPartContext getPartContext() {
    return context;
  }

  @Override
  public void setActive() {
  }

  @Override
  public void onChangeViewState() {
  }
}
