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
package com.gwtao.ui.portal.client.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwtao.common.shared.util.UnexpectedErrorException;

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
