/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
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
