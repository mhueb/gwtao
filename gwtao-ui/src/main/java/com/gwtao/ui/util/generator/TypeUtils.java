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
}
