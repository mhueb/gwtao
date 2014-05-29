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
