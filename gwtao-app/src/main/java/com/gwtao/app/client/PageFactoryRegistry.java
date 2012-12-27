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
package com.gwtao.app.client;

import java.util.HashMap;
import java.util.Map;

public final class PageFactoryRegistry {
  public interface Entry {
    String getToken();

    IPage create();
  }

  private final Map<String, Entry> descriptorMap = new HashMap<String, Entry>();

  public void register(Entry entry) {
    descriptorMap.put(entry.getToken(), entry);
  }

  public IPage create(String token) {
    Entry factory = descriptorMap.get(token);
    if (factory == null)
      throw new RuntimeException();

    return factory.create();
  }
}
