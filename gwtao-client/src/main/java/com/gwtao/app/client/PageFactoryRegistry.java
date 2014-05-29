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

import java.util.HashMap;
import java.util.Map;

public final class PageFactoryRegistry {
  public static final PageFactoryRegistry REGISTRY = new PageFactoryRegistry();

  private final Map<String, IPageFactory> descriptorMap = new HashMap<String, IPageFactory>();

  public void register(IPageFactory entry) {
    descriptorMap.put(entry.getTokenName(), entry);
  }

  public IPage create(String token) {
    IPageFactory factory = descriptorMap.get(token);
    if (factory == null)
      throw new RuntimeException();

    return factory.createPage();
  }

  public boolean hasToken(String token) {
    return descriptorMap.containsKey(token);
  }
}
