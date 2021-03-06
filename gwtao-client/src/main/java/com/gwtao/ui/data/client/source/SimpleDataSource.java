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
package com.gwtao.ui.data.client.source;

import org.shu4j.utils.permission.Permission;

public class SimpleDataSource<T> extends AbstractDataSource<T> {
  private T data;

  public SimpleDataSource() {
  }

  public SimpleDataSource(T data) {
    this.data = data;
  }

  public void setData(T data) {
    if (this.data != data) {
      this.data = data;
      onSetData();
    }
  }

  protected void onSetData() {
    fireDataChanged();
  }

  protected void setDataSilent(T data) {
    this.data = data;
  }

  public T getData() {
    return data;
  }

  @Override
  public boolean isNull() {
    return data == null;
  }

  @Override
  public Permission getPermission() {
    return isNull() ? Permission.READONLY : Permission.ALLOWED;
  }

}
