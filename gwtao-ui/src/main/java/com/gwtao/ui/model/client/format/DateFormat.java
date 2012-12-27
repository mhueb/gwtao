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
package com.gwtao.ui.model.client.format;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.gwtao.ui.model.client.util.IValueFormat;
import com.gwtao.ui.model.client.util.ValueParseException;

public class DateFormat implements IValueFormat<Date> {
  public static DateTimeFormat FORM_DDMMYYYY_HHMM = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");

  private final DateTimeFormat format;

  public DateFormat(DateTimeFormat format) {
    this.format = format;
  }

  public Date parse(String text) throws ValueParseException {
    try {
      return format.parse(text);
    }
    catch (IllegalArgumentException e) {
      throw new ValueParseException(e);
    }
  }

  public String format(Date date) {
    if (date == null)
      return null;
    return format.format(date);
  }

}
