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
package com.gwtao.portalapp.client.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.exception.UnexpectedErrorException;

public class GenericRegistry<T, U extends IGenericDescriptor<T>> implements IGenericRegistry<T, U> {
  private final Map<String, U> descriptorMap = new HashMap<String, U>();

  @Override
  public T create(String id) {
    U descriptor = lookup(id);
    if (descriptor != null) {
      T create = descriptor.create();
      if (create == null)
        throw new UnexpectedErrorException("Factory create returned null for id=" + id);
      return create;
    }
    else
      throw new IllegalArgumentException("Unknown descriptor id=" + id);
  }

  @Override
  public U lookup(String id) {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Id is empty or null");
    return descriptorMap.get(id);
  }

  @Override
  public Collection<U> getDescriptors() {
    return descriptorMap.values();
  }

  @Override
  public void register(U descriptor) {
    U oldDescriptor = descriptorMap.put(descriptor.getId(), descriptor);
    if (oldDescriptor != null)
      throw new IllegalStateException("Duplictate descriptor id=" + descriptor.getId());
  }
}
