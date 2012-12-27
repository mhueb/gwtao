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
package com.gwtao.ui.model.client.fields;

import org.shu4j.utils.exception.ValidateException;

import com.gwtao.ui.model.client.i18n.ModelConstants;
import com.gwtao.ui.model.client.i18n.ModelMessages;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public abstract class IntegerFieldInfo<M> implements IIntegerFieldInfo<M> {
  public static final IValueFormat<Integer> FORMAT = new IValueFormat<Integer>() {

    public Integer parse(String text) throws ValueParseException {
      try {
        return Integer.parseInt(text);
      }
      catch (NumberFormatException e) {
        throw new ValueParseException(e);
      }
    }

    public String format(Integer t) {
      if (t == null)
        return null;
      return t.toString();
    }
  };

  private Integer minValue;
  private Integer maxValue;
  private String emptyText;

  public IntegerFieldInfo<M> setRange(Integer minValue, Integer maxValue) {
    if (maxValue != null && minValue != null && maxValue < minValue)
      throw new IllegalArgumentException("maxValue must not be lower than minValue");
    this.minValue = minValue;
    this.maxValue = maxValue;
    return this;
  }

  public IntegerFieldInfo<M> setEmptyText(String emptyText) {
    this.emptyText = emptyText;
    return this;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void validate(Integer val) throws ValidateException {
    if (val != null) {
      if (getMinValue() != null && val < getMinValue())
        throw new ValidateException(ModelMessages.c.valueToLow(getValueFormat().format(getMinValue())));
      if (getMaxValue() != null && val > getMaxValue())
        throw new ValidateException(ModelMessages.c.valueToHigh(getValueFormat().format(getMaxValue())));
    }
    else if (isNotNull())
      throw new ValidateException(ModelConstants.c.valueMustNotNull());
  }

  public IValueFormat<Integer> getValueFormat() {
    return FORMAT;
  }
}
