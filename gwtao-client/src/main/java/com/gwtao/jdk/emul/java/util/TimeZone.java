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
package java.util;

import java.io.Serializable;
import java.util.Date;

/**
 * Emulation of java Gregorian calendar for use in gwt javascript.<br>
 * 
 * @author Matthias Huebner
 * @see Calendar
 */
@SuppressWarnings("deprecation")
public class TimeZone implements Serializable, Cloneable {
  private static final int LOCALE_OFFSET = new Date().getTimezoneOffset();

  static final long serialVersionUID = 3581463369166924961L;

  private final com.google.gwt.i18n.client.TimeZone impl;

  public static TimeZone getDefault() {
    return new TimeZone(com.google.gwt.i18n.client.TimeZone.createTimeZone(LOCALE_OFFSET));
  }

  private TimeZone(com.google.gwt.i18n.client.TimeZone impl) {
    this.impl = impl;
  }

  public int getOffset(long date) {
    return impl.getOffset(new Date(date)) * 60 * 1000;
  }

  public String getID() {
    return impl.getID();
  }

  public boolean inDaylightTime(Date date) {
    return impl.isDaylightTime(date);
  }

  public TimeZone clone() {
    return new TimeZone(impl);
  }

}
