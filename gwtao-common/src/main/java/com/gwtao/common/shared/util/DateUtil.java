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
package com.gwtao.common.shared.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
  private DateUtil() {
  }
  
  public static Date clearTime(Date date) {
    if (date == null)
      return null;

    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date merge(Date date, Date time) {
    if (date == null)
      return time;

    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    if (time == null) {
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
    }
    else {
      Calendar tim = new GregorianCalendar();
      tim.setTime(time);

      cal.set(Calendar.HOUR_OF_DAY, tim.get(Calendar.HOUR_OF_DAY));
      cal.set(Calendar.MINUTE, tim.get(Calendar.MINUTE));
      cal.set(Calendar.SECOND, tim.get(Calendar.SECOND));
      cal.set(Calendar.MILLISECOND, tim.get(Calendar.MILLISECOND));
    }
    return cal.getTime();
  }

  public static boolean hasTime(Date time) {
    if (time != null) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(time);
      return cal.get(Calendar.HOUR_OF_DAY) != 0 || cal.get(Calendar.MINUTE) != 0 || cal.get(Calendar.SECOND) != 0 || cal.get(Calendar.MILLISECOND) != 0;
    }
    return false;
  }

  /**
   * @param year Year
   * @param month 0..11
   * @param day 1..31
   * @return
   */
  public static Date makeDate(int year, int month, int day) {
    Calendar cal = new GregorianCalendar(year, month, day);
    return cal.getTime();
  }

  /**
   * @param year Year
   * @param month 0..11
   * @param day 1..31
   * @param hour 0..23
   * @param min 0..59
   * @param sec 0..59
   * @return
   */
  public static Date makeDateTime(int year, int month, int day, int hour, int min, int sec) {
    Calendar cal = new GregorianCalendar(year, month, day, hour, min, sec);
    return cal.getTime();
  }

  public static Date makeExpireDate(int seconds) {
    Calendar instance = new GregorianCalendar();
    instance.add(Calendar.SECOND, seconds);
    return instance.getTime();
  }

  public static long calcDays(Date now, Date date) {
    return (DateUtil.clearTime(now).getTime() - DateUtil.clearTime(date).getTime()) / 1000 / 60 / 60 / 24;
  }

  /**
   * Calculate Date of easter sunday.<br>
   * Formular by Carl Friedrich Gauß to calculate easter sunday between 1583 and 8202.
   * 
   * @param year 1583..8202
   * @return Date of easter sunday
   */
  public static Date calcEasterSunday(int year) {
    if (year < 1583 || year > 8202)
      throw new IllegalArgumentException("Invalid year=" + year);

    int k = year / 100;

    int m = 15;
    m += (3 * k + 3) / 4;
    m -= (8 * k + 13) / 25;

    int s = 2;
    s -= (3 * k + 3) / 4;

    int a = year % 19;
    int d = (19 * a + m) % 30;

    int r = d / 28;
    r -= d / 29;
    r *= a / 11;
    r += d / 29;

    int og = 21 + d - r;

    int sz = s + year + year / 4;
    sz %= 7;
    sz *= -1;
    sz += 7;

    int oe = og - sz;
    oe %= 7;
    oe *= -1;
    oe += 7;

    int os = og + oe;

    Calendar cal = new GregorianCalendar();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, os > 31 ? 4 : 3);
    cal.set(Calendar.DAY_OF_MONTH, os > 31 ? os - 31 : os);
    return cal.getTime();
  }

}
