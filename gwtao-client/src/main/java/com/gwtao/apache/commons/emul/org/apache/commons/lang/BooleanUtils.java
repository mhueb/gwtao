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

import org.apache.commons.lang.math.NumberUtils;

/**
 * Emulation of org.apache.commons.lang.BooleanUtils
 * 
 */
public final class BooleanUtils {
  private static final String OFF = "off";
  private static final String ON = "on";
  private static final String NO = "no";
  private static final String YES = "yes";
  private static final String FALSE = "false";
  private static final String TRUE = "true";

  public static boolean isTrue(Boolean bool) {
    if (bool == null) {
      return false;
    }
    return bool.booleanValue() ? true : false;
  }

  public static boolean isNotTrue(Boolean bool) {
    return !isTrue(bool);
  }

  public static boolean isFalse(Boolean bool) {
    if (bool == null) {
      return false;
    }
    return bool.booleanValue() ? false : true;
  }

  public static boolean isNotFalse(Boolean bool) {
    return !isFalse(bool);
  }

  public static Boolean toBooleanObject(boolean bool) {
    return bool ? Boolean.TRUE : Boolean.FALSE;
  }

  public static boolean toBoolean(Boolean bool) {
    if (bool == null) {
      return false;
    }
    return bool.booleanValue() ? true : false;
  }

  public static Boolean toBooleanObject(String str) {
    if (str == null || str.isEmpty())
      return null;
    else if (str.equalsIgnoreCase(TRUE) || str.equalsIgnoreCase(YES) || str.equalsIgnoreCase(ON))
      return Boolean.TRUE;
    else if (str.equalsIgnoreCase(FALSE) || str.equalsIgnoreCase(NO) || str.equalsIgnoreCase(OFF))
      return Boolean.FALSE;
    else
      return null;
  }

  public static boolean toBoolean(String str) {
    return toBoolean(toBooleanObject(str));
  }

  public static String toStringTrueFalse(Boolean bool) {
    return toString(bool, TRUE, FALSE, null);
  }

  public static String toStringOnOff(Boolean bool) {
    return toString(bool, ON, OFF, null);
  }

  public static String toStringYesNo(Boolean bool) {
    return toString(bool, YES, NO, null);
  }

  public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
    return bool == null ? nullString : bool.booleanValue() ? trueString : falseString;
  }

  public static String toStringTrueFalse(boolean bool) {
    return toString(bool, TRUE, FALSE);
  }

  public static String toStringOnOff(boolean bool) {
    return toString(bool, ON, OFF);
  }

  public static String toStringYesNo(boolean bool) {
    return toString(bool, YES, NO);
  }

  public static String toString(boolean bool, String trueString, String falseString) {
    return bool ? trueString : falseString;
  }
}
