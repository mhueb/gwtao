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

import org.shu4j.utils.exception.ValidateException;

import com.gwtao.ui.model.client.fields.IFloatFieldInfo;
import com.gwtao.ui.model.client.i18n.ModelConstants;
import com.gwtao.ui.model.client.i18n.ModelMessages;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public abstract class FloatFieldInfo<M> implements IFloatFieldInfo<M> {
  public static final IValueFormat<Float> FORMAT = new IValueFormat<Float>() {

    public Float parse(String text) throws ValueParseException {
      try {
        return Float.parseFloat(text);
      }
      catch (NumberFormatException e) {
        throw new ValueParseException(e);
      }
    }

    public String format(Float t) {
      if (t == null)
        return null;
      return t.toString();
    }

  };
  private Float minValue;
  private Float maxValue;
  private String emptyText;

  public IFloatFieldInfo<M> setRange(Float minValue, Float maxValue) {
    if (maxValue != null && minValue != null && maxValue < minValue)
      throw new IllegalArgumentException("maxValue must not be lower than minValue");
    this.minValue = minValue;
    this.maxValue = maxValue;
    return this;
  }

  public IFloatFieldInfo<M> setEmptyText(String emptyText) {
    this.emptyText = emptyText;
    return this;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public Float getMinValue() {
    return minValue;
  }

  public Float getMaxValue() {
    return maxValue;
  }

  public IValueFormat<Float> getValueFormat() {
    return FORMAT;
  }

  public void validate(Float val) throws ValidateException {
    if (val != null) {
      if (getMinValue() != null && val < getMinValue())
        throw new ValidateException(ModelMessages.c.valueToLow(getValueFormat().format(getMinValue())));
      if (getMaxValue() != null && val > getMaxValue())
        throw new ValidateException(ModelMessages.c.valueToHigh(getValueFormat().format(getMaxValue())));
    }
    else if (isNotNull())
      throw new ValidateException(ModelConstants.c.valueMustNotNull());
  }
}
