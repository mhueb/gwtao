/* 
 * Copyright 2012 GWTAO
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
