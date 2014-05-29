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
package com.gwtao.ui.util.generator;

import org.apache.commons.lang.WordUtils;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;

public final class TypeUtils {

  public static JMethod getGetter(JClassType targetType, String fieldName) throws NotFoundException {
    String fieldNameC = WordUtils.capitalize(fieldName);
    JType[] arr = new JType[0];
    try {
      return targetType.getMethod("get" + fieldNameC, arr);
    }
    catch (NotFoundException e1) {
      try {
        return targetType.getMethod("is" + fieldNameC, arr);
      }
      catch (NotFoundException e2) {
        try {
          return targetType.getMethod("get" + fieldName, arr);
        }
        catch (NotFoundException e3) {
          try {
            return targetType.getMethod("is" + fieldName, arr);
          }
          catch (NotFoundException e4) {
            throw new NotFoundException("Now accessor found for field: " + fieldName);
          }
        }
      }
    }
  }

  public static JMethod getSetter(JClassType targetType, String fieldName, JType type) throws NotFoundException {
    String fieldNameC = WordUtils.capitalize(fieldName);
    JType[] arr = { type };
    return targetType.getMethod("set" + fieldNameC, arr);
  }
}
