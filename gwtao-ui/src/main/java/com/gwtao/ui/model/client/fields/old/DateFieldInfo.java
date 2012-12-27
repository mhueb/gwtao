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

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtao.ui.model.client.fields.IDateFieldInfo;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public abstract class DateFieldInfo<M> implements IDateFieldInfo<M> {
  public static DateTimeFormat FORM_DDMMYYYY_HHMM = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");

  public final IValueFormat<Date> format = new IValueFormat<Date>() {

    public Date parse(String text) throws ValueParseException {
      try {
        return getFormat().parse(text);
      }
      catch (IllegalArgumentException e) {
        throw new ValueParseException(e);
      }
    }

    public String format(Date date) {
      if (date == null)
        return null;
      return getFormat().format(date);
    }

  };

  private Date minDate;
  private Date maxDate;
  private String emptyText;

  public DateFieldInfo<M> setRange(Date minDate, Date maxDate) {
    this.minDate = minDate;
    this.maxDate = maxDate;
    return this;
  }

  public DateFieldInfo<M> setEmptyText(String emptyText) {
    this.emptyText = emptyText;
    return this;
  }

  public Date getMinDate() {
    return minDate;
  }

  public Date getMaxDate() {
    return maxDate;
  }

  public String getEmptyText() {
    return emptyText;
  }

  public IValueFormat<Date> getValueFormat() {
    return format;
  }

  public DateTimeFormat getFormat() {
    return FORM_DDMMYYYY_HHMM;
  }
}
