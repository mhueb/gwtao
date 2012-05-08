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
package com.gwtao.common.shared.message;

import com.gwtao.common.shared.i18n.CommonConstants;


public enum MessageLevel {
  INFO(0),
  WARN(1),
  DECIDE(2),
  ERROR(3),
  FATAL(4);

  private int level;
  private transient String name;

  private MessageLevel(int level) {
    this.level = level;
  }

  public int getNr() {
    return level;
  }

  public String getName() {
    if (name == null)
      name = mapName();

    return name;
  }

  private String mapName() {
    switch (this) {
    case INFO:
      return CommonConstants.c.info();
    case WARN:
      return CommonConstants.c.warn();
    case DECIDE:
      return CommonConstants.c.decide();
    case ERROR:
      return CommonConstants.c.error();
    case FATAL:
      return CommonConstants.c.fatal();
    }
    return String.valueOf(level);
  }

  public static boolean isError(MessageLevel lvl) {
    return lvl != null && lvl.getNr() >= ERROR.getNr();
  }

  public static MessageLevel map(Integer value) {
    if (value != null) {
      if (value == INFO.level)
        return INFO;
      else if (value == WARN.level)
        return WARN;
      else if (value == DECIDE.level)
        return DECIDE;
      else if (value == ERROR.level)
        return ERROR;
      else if (value == FATAL.level)
        return FATAL;
    }
    return null;
  }
}
