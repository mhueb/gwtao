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
package org.apache.commons.lang;

import java.io.Serializable;

/**
 * Emulation of org.apache.commons.lang.ObjectUtils
 * 
 * @author Matthias Hübner
 *
 */
public class ObjectUtils {

  public static Object defaultIfNull(Object object, Object defaultValue) {
    return object != null ? object : defaultValue;
  }

  public static boolean equals(Object object1, Object object2) {
    if (object1 == object2) {
      return true;
    }
    if ((object1 == null) || (object2 == null)) {
      return false;
    }
    return object1.equals(object2);
  }

  public static boolean notEqual(Object object1, Object object2) {
    return ObjectUtils.equals(object1, object2) == false;
  }

  public static int hashCode(Object obj) {
    return (obj == null) ? 0 : obj.hashCode();
  }

  public static String toString(Object obj) {
    return obj == null ? "" : obj.toString();
  }

  public static String toString(Object obj, String nullStr) {
    return obj == null ? nullStr : obj.toString();
  }

  public static Object min(Comparable c1, Comparable c2) {
    return (compare(c1, c2, true) <= 0 ? c1 : c2);
  }

  public static Object max(Comparable c1, Comparable c2) {
    return (compare(c1, c2, false) >= 0 ? c1 : c2);
  }

  public static int compare(Comparable c1, Comparable c2) {
    return compare(c1, c2, false);
  }

  public static int compare(Comparable c1, Comparable c2, boolean nullGreater) {
    if (c1 == c2) {
      return 0;
    }
    else if (c1 == null) {
      return (nullGreater ? 1 : -1);
    }
    else if (c2 == null) {
      return (nullGreater ? -1 : 1);
    }
    return c1.compareTo(c2);
  }
}
