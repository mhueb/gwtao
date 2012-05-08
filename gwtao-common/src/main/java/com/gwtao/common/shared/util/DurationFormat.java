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

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.gwtao.common.shared.i18n.CommonConstants;

public class DurationFormat {
  public static final DurationFormat HOURS = new DurationFormat(false);
  public static final DurationFormat FULL = new DurationFormat(24, true);

  private Integer hpd;
  private boolean seconds;

  public DurationFormat(Integer hpd, boolean seconds) {
    this.hpd = hpd;
    this.seconds = seconds;
  }

  public DurationFormat(boolean seconds) {
    this(null, seconds);
  }

  public DurationFormat(Integer hpd) {
    this(hpd, false);
  }

  public String format(Long value) {
    if (value == null || value == 0)
      return "";

    long mins = value / 60;
    long secs = value - mins * 60;
    long hours = mins / 60;
    mins = mins - hours * 60;

    long days = 0;
    if (hpd != null && hours > hpd) {
      days = hours / hpd;
      hours = hours - days * hpd;
    }

    StringBuffer buff = new StringBuffer();
    if (days != 0) {
      buff.append(days);
      buff.append(CommonConstants.c.abbreviationDays());
    }

    if (hours != 0) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(hours);
      buff.append(CommonConstants.c.abbreviationHours());
    }

    if (mins != 0) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(mins);
      buff.append(CommonConstants.c.abbreviationMinutes());
    }

    if (seconds) {
      if (buff.length() > 0)
        buff.append("  ");
      buff.append(secs);
      buff.append(CommonConstants.c.abbreviationSeconds());
    }

    return buff.toString();
  }

  public Long parse(String text) throws ParseException {
    if (StringUtils.isEmpty(text))
      return null;

    try {
      double d = Double.parseDouble(text);
      return (long) (d * 3600.0);
    }
    catch (NumberFormatException e) {
    }

    long duration = 0;

    for (int pos = 0; pos < text.length();) {
      Integer val = null;
      for (; pos < text.length(); ++pos) {
        char ch = text.charAt(pos);
        if (ch == ' ')
          continue;
        if (ch >= '0' && ch <= '9') {
          if (val == null)
            val = 0;
          val = val * 10 + (ch - '0');
        }
        else
          break;
      }

      if (val == null)
        throw new ParseException("Unexpected format: " + text, pos);

      char sym = 0;
      for (; pos < text.length(); ++pos) {
        char ch = text.charAt(pos);
        if (ch == ' ')
          continue;
        sym = ch;
        break;
      }

      final String abbrev = String.valueOf(sym);
      if (abbrev.equals(CommonConstants.c.abbreviationDays())) {
        if (hpd == null)
          duration += val * 3600 * 24;
        else
          duration += val * 3600 * hpd;
      }
      else if (abbrev.equals(CommonConstants.c.abbreviationHours())) {
        duration += val * 3600;
      }
      else if (abbrev.equals(CommonConstants.c.abbreviationMinutes())) {
        duration += val * 60;
      }
      else if (abbrev.equals(CommonConstants.c.abbreviationSeconds())) {
        duration += val;
      }
      else {
        throw new ParseException("Unexpected format: " + text, pos);
      }

      while (++pos < text.length() && text.charAt(pos) == ' ')
        ;
    }

    return duration;
  }
}
