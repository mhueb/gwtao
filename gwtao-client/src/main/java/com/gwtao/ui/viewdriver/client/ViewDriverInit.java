package com.gwtao.ui.viewdriver.client;

import java.util.ArrayList;
import java.util.List;

public class ViewDriverInit {
  private static List<String> types;

  static {
    if (types==null) {
      types = new ArrayList<String>();
    }
  }
  
  public static void addAdapter(Class<?> zClass) {
    types.add(zClass.getName());
  }

  public static List<String> getAndClearTypes() {
    if ( types.size()==0 ) {
      return types;
    }
    else {
      List<String> ret = types;
      
      types = new ArrayList<String>();
      
      return ret;
    }
  }
}
