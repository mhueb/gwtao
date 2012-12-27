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
package com.gwtao.ui.model.client.fields.old;

import com.gwtao.ui.model.client.fields.IReferenceFieldInfo;
import com.gwtao.ui.model.client.fields.IStringFieldInfo;
import com.gwtao.ui.model.client.util.IFormatter;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public class ReferenceToStringFieldInfo<T, RT> implements IStringFieldInfo<T> {
  private IReferenceFieldInfo<T, RT> reference;
  private IFormatter<RT> formatter;
  private String emptytext;

  private IValueFormat<String> format = new IValueFormat<String>() {

    public String parse(String text) throws ValueParseException {
      return text;
    }

    public String format(String value) {
      return value;
    }
  };

  public ReferenceToStringFieldInfo(IReferenceFieldInfo<T, RT> reference, IFormatter<RT> formatter) {
    this.reference = reference;
    this.formatter = formatter;
  }

  public String getEmptyText() {
    return emptytext;
  }

  public String getMask() {
    return null;
  }

  public int getMaxLength() {
    return 0;
  }

  public int getMinLength() {
    return 0;
  }

  public String getDisplayName() {
    return reference.getDisplayName();
  }

  public boolean isNotNull() {
    return reference.isNotNull();
  }

  public boolean isReadOnly() {
    return true;
  }

  public String getValue(T model) {
    return formatter.format(reference.getValue(model));
  }

  public void setValue(T model, String value) {
  }

  public String getString(T model) {
    return getValue(model);
  }

  public void setString(T model, String value) {
    setValue(model, value);
  }

  public IValueFormat<String> getValueFormat() {
    return format;
  }

}
