package com.gwtao.ui.location.client;

import org.apache.commons.lang.StringUtils;

public class LocationUtils {

  public static Location buildLocation(String token) {
    if (StringUtils.isEmpty(token))
      return null;

    String id;
    String params;
    int idx = token.indexOf('?');
    if (idx > 0) {
      id = token.substring(0, idx);
      params = token.substring(idx);
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

}
