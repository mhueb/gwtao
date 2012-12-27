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

import com.gwtao.ui.model.client.fields.IStringFieldInfo;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public abstract class StringFieldInfo<M> implements IStringFieldInfo<M> {
  public static final String TEXT = "\\w+ ";

  public static final String EMAILEX = "$EMAIL";

  public static final IValueFormat<String> FORMAT = new IValueFormat<String>() {

    public String parse(String text) throws ValueParseException {
      return text;
    }

    public String format(String value) {
      return value;
    }
  };

  private int minLength;
  private int maxLength;
  private String emptyText;
  private String mask;

  public StringFieldInfo<M> setLength(int minLength, int maxLength) {
    if (maxLength < minLength)
      throw new IllegalArgumentException("maxValue must not be lower than minValue");
    this.minLength = minLength;
    this.maxLength = maxLength;
    return this;
  }

  public StringFieldInfo<M> setEmptyText(String emptyText) {
    this.emptyText = emptyText;
    return this;
  }

  public StringFieldInfo<M> setMask(String mask) {
    this.mask = mask;
    return this;
  }

  public boolean isNotNull() {
    return minLength != 0;
  }

  public int getMinLength() {
    return minLength;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public String getMask() {
    return mask;
  }

  public IValueFormat<String> getValueFormat() {
    return FORMAT;
  }
}
