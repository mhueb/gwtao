/* 
 * Copyright 2012 Matthias Huebner
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
package com.gwtao.ui.location.client;

import org.apache.commons.lang.StringUtils;

import com.gwtao.ui.util.client.ParameterList;
import com.gwtao.ui.util.client.ParameterList.Builder;

public class LocationUtils {

  public static Location buildLocation(String token) {
    if (StringUtils.isEmpty(token))
      return Location.NOWHERE;

    String id;
    String params;
    int idx = token.indexOf('?');
    if (idx >= 0) {
      id = token.substring(0, idx);
      params = token.substring(idx + 1);
    }
    else {
      id = token;
      params = null;
    }

    return new Location(id, params);
  }

  public static Location buildToken(String id, String params) {
    if (StringUtils.isEmpty(id))
      return null;
    return new Location(id, params);
  }

  public static ParameterList parse(String parameter) {
    Builder builder = new ParameterList.Builder();
    if (StringUtils.isNotBlank(parameter)) {
      String[] items = parameter.split("&");
      for (String item : items) {
        item = item.trim();
        String[] parts = item.split("=");
        if (parts.length == 2)
          builder.add(normalize(parts[0]), normalize(parts[1]));
        else
          builder.add(normalize(item));
      }
    }
    return builder.build();
  }

  private static String normalize(String string) {
    return string;
  }

}
