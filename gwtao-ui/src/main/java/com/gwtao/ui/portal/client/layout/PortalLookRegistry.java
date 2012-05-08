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
package com.gwtao.ui.portal.client.layout;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PortalLookRegistry implements IPortalLookRegistry {
  private static final IPortalLookRegistry instance = new PortalLookRegistry();

  public static IPortalLookRegistry get() {
    return instance;
  }

  private static final Map<String, IPortalLookFactory> factoryMap = new HashMap<String, IPortalLookFactory>();

  @Override
  public IPortalLookFactory getLayoutFactory(String id) {
    IPortalLookFactory factory = factoryMap.get(id);
    if (factory == null)
      throw new IllegalArgumentException("Unknown portal look factory id=" + id);
    return factory;
  }

  @Override
  public Collection<IPortalLookFactory> getLayouts() {
    return factoryMap.values();
  }

  @Override
  public void register(IPortalLookFactory layout) {
    IPortalLookFactory old = factoryMap.put(layout.getId(), layout);
    if (old != null)
      throw new IllegalStateException("Duplicate layout factory id=" + layout.getId());
  }

}
