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
package com.gwtao.ui.model.client.access;

import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public class AccessGenericAdapter<M, V> implements IAccessGeneric<M> {
  private IAccessValue<M, V> acc;
  private IValueFormat<V> form;

  public AccessGenericAdapter(IAccessValue<M, V> acc, IValueFormat<V> form) {
    this.acc = acc;
    this.form = form;
  }

  public void setString(M model, String text) throws ValueParseException {
    acc.setValue(model, form.parse(text));
  }

  public String getString(M model) {
    return form.format(acc.getValue(model));
  }
}
