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
