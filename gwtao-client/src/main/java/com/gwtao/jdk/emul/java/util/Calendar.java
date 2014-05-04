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
 * Emulation of java Calendar class for use in gwt javascript.<br>
 * This is not a full implementation, only commonly used methods are implemented.
 * 
 * @author Matthias Huebner
 * 
 */
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
  private static final long serialVersionUID = 1L;

  public final static int ERA = 0;
  public final static int YEAR = 1;
  public final static int MONTH = 2;
  public final static int WEEK_OF_YEAR = 3;
  public final static int WEEK_OF_MONTH = 4;
  public final static int DATE = 5;
  public final static int DAY_OF_MONTH = 5;
  public final static int DAY_OF_YEAR = 6;
  public final static int DAY_OF_WEEK = 7;
  public final static int DAY_OF_WEEK_IN_MONTH = 8;
  public final static int AM_PM = 9;
  public final static int HOUR = 10;
  public final static int HOUR_OF_DAY = 11;
  public final static int MINUTE = 12;
  public final static int SECOND = 13;
  public final static int MILLISECOND = 14;
  public final static int ZONE_OFFSET = 15;
  public final static int DST_OFFSET = 16;
  public final static int FIELD_COUNT = 17;

  public final static int SUNDAY = 1;
  public final static int MONDAY = 2;
  public final static int TUESDAY = 3;
  public final static int WEDNESDAY = 4;
  public final static int THURSDAY = 5;
  public final static int FRIDAY = 6;
  public final static int SATURDAY = 7;

  public final static int JANUARY = 0;
  public final static int FEBRUARY = 1;
  public final static int MARCH = 2;
  public final static int APRIL = 3;
  public final static int MAY = 4;
  public final static int JUNE = 5;
  public final static int JULY = 6;
  public final static int AUGUST = 7;
  public final static int SEPTEMBER = 8;
  public final static int OCTOBER = 9;
  public final static int NOVEMBER = 10;
  public final static int DECEMBER = 11;

  public final static int AM = 0;
  public final static int PM = 1;

  protected static final int DATE_BASE = 1900;

  protected static final int ONE_SECOND = 1000;
  protected static final int ONE_MINUTE = 60 * ONE_SECOND;
  protected static final int ONE_HOUR = 60 * ONE_MINUTE;
  protected static final long ONE_DAY = 24 * ONE_HOUR;
  protected static final long ONE_WEEK = 7 * ONE_DAY;

  private static final int monthLength[] = {
      31,
      28,
      31,
      30,
      31,
      30,
      31,
      31,
      30,
      31,
      30,
      31 };

  private static final String INVALID_FIELDNAME = "invalid";

  private static final String[] FIELDNAMES = {
      "ERA",
      "YEAR",
      "MONTH",
      "WEEK_OF_YEAR",
      "WEEK_OF_MONTH",
      "DAY_OF_MONTH",
      "DAY_OF_YEAR",
      "DAY_OF_WEEK",
      "DAY_OF_WEEK_IN_MONTH",
      "AM_PM",
      "HOUR",
      "HOUR_OF_DAY",
      "MINUTE",
      "SECOND",
      "MILLISECOND",
      "ZONE_OFFSET",
      "DST_OFFSET" };

  static final int MIN_VALUES[] = {
      0,
      100,
      JANUARY,
      1,
      0,
      1,
      1,
      SUNDAY,
      1,
      AM,
      0,
      0,
      0,
      0,
      0,
      -13 * ONE_HOUR,
      0 };

  static final int MAX_VALUES[] = {
      1,
      5000,
      DECEMBER,
      53,
      6,
      31,
      366,
      SATURDAY,
      6,
      PM,
      11,
      23,
      59,
      59,
      999,
      14 * ONE_HOUR,
      2 * ONE_HOUR };

  private Date date;
  private transient TimeZone timezone;
  private transient int firstDayOfWeek = SUNDAY;

  protected Calendar() {
    timezone = TimeZone.getDefault();
  }

  public final Date getTime() {
    return new Date(getTimeInMillis());
  }

  public final void setTime(Date date) {
    setTimeInMillis(date.getTime());
  }

  public long getTimeInMillis() {
    return date.getTime();
  }

  public void setTimeInMillis(long millis) {
    date = new Date(millis);
  }

  public TimeZone getTimeZone() {
    return this.timezone;
  }

  // public void setTimeZone(TimeZone timezone) {
  // if (timezone == null)
  // throw new IllegalArgumentException();
  // this.timezone = timezone;
  // }

  public int get(int field) {
    checkField(field);

    switch (field) {
    case YEAR:
      return date.getYear() + DATE_BASE;
    case MONTH:
      return date.getMonth();
    case DAY_OF_MONTH:
      return date.getDate();
    case HOUR:
      return getHour();
    case HOUR_OF_DAY:
      return date.getHours();
    case AM_PM:
      return getAMPM();
    case MINUTE:
      return date.getMinutes();
    case SECOND:
      return date.getSeconds();
    case MILLISECOND:
      return (int) (date.getTime() % 1000);
    case DAY_OF_WEEK:
      return date.getDay() + 1;
    case DAY_OF_YEAR:
      return calcDayOfYear();
    case WEEK_OF_YEAR:
      return calcWeekOfYear();
    default:
      throw new IllegalArgumentException("unsupported field=" + getFieldName(field));
    }
  }

  private int getAMPM() {
    int hour = date.getHours();
    return hour < 12 ? AM : PM;
  }

  private int getHour() {
    int hour = date.getHours();
    if (hour > 11)
      hour -= 12;
    return hour;
  }

  public void set(int field, int value) {
    checkField(field);
    switch (field) {
    case YEAR:
      date.setYear(value - DATE_BASE);
      break;
    case MONTH:
      date.setMonth(value);
      break;
    case DAY_OF_MONTH:
      date.setDate(value);
      break;
    // case HOUR:
    case HOUR_OF_DAY:
      date.setHours(value);
      break;
    case MINUTE:
      date.setMinutes(value);
      break;
    case SECOND:
      date.setSeconds(value);
      break;
    case MILLISECOND:
      date.setTime(date.getTime() / ONE_SECOND * ONE_SECOND + value);
      break;
    default:
      throw new IllegalArgumentException("unsupported field=" + getFieldName(field));
    }
  }

  public final void set(int year, int month, int date) {
    set(YEAR, year);
    set(MONTH, month);
    set(DATE, date);
  }

  public final void set(int year, int month, int date, int hourOfDay, int minute) {
    set(YEAR, year);
    set(MONTH, month);
    set(DATE, date);
    set(HOUR_OF_DAY, hourOfDay);
    set(MINUTE, minute);
  }

  public final void set(int year, int month, int date, int hourOfDay, int minute, int second) {
    set(YEAR, year);
    set(MONTH, month);
    set(DATE, date);
    set(HOUR_OF_DAY, hourOfDay);
    set(MINUTE, minute);
    set(SECOND, second);
  }

  public void add(int field, int amount) {
    checkField(field);

    switch (field) {
    case YEAR:
      date.setYear(date.getYear() + amount);
      break;
    case MONTH:
      date.setMonth(date.getMonth() + amount);
      break;
    case DAY_OF_MONTH:
      date.setDate(date.getDate() + amount);
      break;
    case HOUR:
    case HOUR_OF_DAY:
      date.setHours(date.getHours() + amount);
      break;
    case MINUTE:
      date.setMinutes(date.getMinutes() + amount);
      break;
    case SECOND:
      date.setSeconds(date.getSeconds() + amount);
      break;
    case MILLISECOND:
      date.setTime(date.getTime() + amount);
      break;
    default:
      throw new IllegalArgumentException("unsupported field=" + getFieldName(field));
    }
  }

  public int getActualMinimum(int field) {
    checkField(field);
    return getMinimum(field);
  }

  public int getActualMaximum(int field) {
    checkField(field);
    switch (field) {
    case DAY_OF_MONTH:
      return getDaysInMonth(get(YEAR), get(MONTH));
    case DAY_OF_YEAR:
      return getDaysInYear(get(YEAR));
    }
    return getMaximum(field);
  }

  public int getMinimum(int field) {
    checkField(field);
    return MIN_VALUES[field];
  }

  public int getMaximum(int field) {
    checkField(field);
    return MAX_VALUES[field];
  }

  public static int getWeekDay(long julian, boolean startAtMonday) {
    if (startAtMonday)
      return (int) julian % 7 + 1;
    else
      return (int) (julian + 1) % 7 + 1;
  }

  private int calcWeekOfYear() {
    int year = get(YEAR);
    long dayOfYear = getJulianDay(year, 0, 1);
    long actualJulianDay = getJulianDay();

    int weekOfYear = 1;
    int dayOfWeek = getWeekDay(dayOfYear, isMondayFirstDay());

    if (dayOfWeek >= 5)
      weekOfYear = 0;

    if (dayOfWeek > 1) {
      dayOfYear += 8 - dayOfWeek;
      if (dayOfYear <= actualJulianDay)
        weekOfYear++;
    }

    if (dayOfYear < actualJulianDay)
      weekOfYear += (int) ((actualJulianDay - dayOfYear) / 7);

    if (weekOfYear == 0 && year > getMinimum(YEAR)) {
      Calendar cal = clone();
      cal.set(year - 1, 11, 31);
      return cal.calcWeekOfYear();
    }

    if (weekOfYear > 52) {
      Calendar cal = clone();
      cal.set(year - 1, 11, 31);
      dayOfWeek = cal.calcWeekOfYear();
      long julianDayOfWeek = getJulianDay(year, 11, 31 - dayOfWeek);
      if (dayOfWeek < 4 && actualJulianDay > julianDayOfWeek)
        return 1;
    }

    return weekOfYear;
  }

  private long getJulianDay() {
    return date.getTime() / ONE_DAY;
  }

  private static long getJulianDay(int year, int month, int dayOfWeek) {
    return new Date(year - DATE_BASE, month, dayOfWeek).getTime() / ONE_DAY;
  }

  private int calcDayOfYear() {
    long julianLast = getJulianDay(get(YEAR) - 1, 11, 31);
    long julianActual = getJulianDay();
    return (int) (julianActual - julianLast);
  }

  public void setFirstDayOfWeek(int day) {
    if (day != SUNDAY && day != MONDAY)
      throw new IllegalArgumentException("unsupported day=" + day);
    firstDayOfWeek = day;
  }

  public int getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  private boolean isMondayFirstDay() {
    return getFirstDayOfWeek() == MONDAY ? true : false;
  }

  private static boolean isLeapYear(int year) {
    return (year % 4) == 0 && (year % 100 != 0 || year % 400 == 0);
  }

  private static int getDaysInMonth(int year, int month) {
    if (month < 0 || month > 11)
      throw new IllegalArgumentException("illegal month=" + month);

    int days = monthLength[month];
    if (month == FEBRUARY && isLeapYear(year))
      days++;
    return days;
  }

  private static int getDaysInYear(int year) {
    return isLeapYear(year) ? 366 : 365;
  }

  private static void checkField(int field) {
    if (field < 0 || field >= FIELD_COUNT)
      throw new IllegalArgumentException("invalid field=" + field);
  }

  protected static String getFieldName(int field) {
    return field >= 0 && field <= FIELD_COUNT ? FIELDNAMES[field] : INVALID_FIELDNAME;
  }

  public static Calendar getInstance() {
    Calendar cal = new GregorianCalendar();
    cal.setTime(new Date());
    return cal;
  }

  public abstract Calendar clone();

  public boolean before(Object when) {
    return when instanceof Calendar && compareTo((Calendar) when) < 0;
  }

  public boolean after(Object when) {
    return when instanceof Calendar && compareTo((Calendar) when) > 0;
  }

  public int compareTo(Calendar anotherCalendar) {
    long thisTime = getTimeInMillis();
    long thatTime = anotherCalendar.getTimeInMillis();
    return (thisTime > thatTime) ? 1 : (thisTime == thatTime) ? 0 : -1;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    try {
      Calendar that = (Calendar) obj;
      return this.date.equals(that.date);
    }
    catch (Exception e) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return date.hashCode();
  }
}
