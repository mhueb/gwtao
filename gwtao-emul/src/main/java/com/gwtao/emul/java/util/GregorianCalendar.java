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


/**
 * Emulation of java Gregorian calendar for use in gwt javascript.<br>
 * 
 * @author Matthias Huebner
 * @see Calendar
 */
public class GregorianCalendar extends Calendar {
  private static final long serialVersionUID = 1L;

  public GregorianCalendar() {
    setTime(new Date());
  }

  public GregorianCalendar(int year, int month, int day) {
    setTime(new Date(year - DATE_BASE, month, day));
  }

  public GregorianCalendar(int year, int month, int day, int hour, int min, int sec) {
    setTime(new Date(year - DATE_BASE, month, day, hour, min, sec));
  }

  public GregorianCalendar clone() {
    GregorianCalendar date = new GregorianCalendar();
    date.setTime(getTime());
    return date;
  }
}
