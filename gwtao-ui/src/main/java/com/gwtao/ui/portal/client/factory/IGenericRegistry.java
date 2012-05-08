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

import com.google.gwt.core.client.EntryPoint;

public interface IGenericRegistry<T, U extends IGenericDescriptor<T>> {
  /**
   * In general this is done in the {@link EntryPoint#onModuleLoad} function
   * 
   * @param descriptor The descriptor to register
   */
  void register(U descriptor);

  Collection<U> getDescriptors();

  T create(String id);

  U lookup(String id);
}
