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
package com.gwtao.portalapp.client.factory;

public abstract class GenericDescriptor<T> implements IGenericDescriptor<T> {

  private final String id;
  private final String title;
  private final String icon;

  protected GenericDescriptor(String id, String title, String icon) {
    this.id = id;
    this.title = title;
    this.icon = icon;
  }

  protected GenericDescriptor(String id, String title) {
    this(id, title, null);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getDisplayIcon() {
    return icon;
  }

  @Override
  public String getDisplayTitle() {
    return title;
  }

  @Override
  public String getDisplayTooltip() {
    return null;
  }

}
