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
package com.gwtao.ui.data.client.source;

import org.shu4j.utils.permission.Permission;

public class NullDataSource<T> extends AbstractDataSource<T> {
  public static final IDataSource<Object> NULL = new NullDataSource<Object>(Permission.ALLOWED);

  public static final IDataSource<Object> RO = new NullDataSource<Object>();

  private Permission perm;

  public NullDataSource() {
    this(Permission.READONLY);
  }

  public NullDataSource(Permission perm) {
    this.perm = perm;
  }

  public T getData() {
    return null;
  }

  @Override
  public boolean isNull() {
    return true;
  }

  @Override
  public Permission getPermission() {
    return perm;
  }

}
