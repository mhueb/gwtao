/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
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
