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
package com.gwtao.portalapp.client.layout;

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
