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

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.ParameterList;
import com.gwtao.ui.util.client.ParameterList.Builder;

public class TokenUtils {

  public static Token buildToken(String token) {
    if (StringUtils.isEmpty(token))
      return Token.NOWHERE;

    String name;
    String params;
    int idx = token.indexOf('?');
    if (idx >= 0) {
      name = token.substring(0, idx);
      params = token.substring(idx + 1);
    }
    else {
      name = token;
      params = null;
    }

    return new Token(name, params);
  }

  public static Token buildToken(String name, ParameterList params) {
    if (StringUtils.isEmpty(name))
      return null;
    return new Token(name, params == null ? null : format(params));
  }

  private static String format(ParameterList params) {
    StringBuilder buff = new StringBuilder();
    for (ParameterList.Entry entry : params) {
      if (buff.length() > 0)
        buff.append("&");
      if (StringUtils.isNotBlank(entry.getName()))
        buff.append(entry.getName()).append("=");
      buff.append(entry.getValue());
    }
    return buff.toString();
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

  public static <P, M> Token createByData(HasTokenConverter<P, M> f, M m) {
    return createByParam(f, f.getConverter().extract(m));
  }

  public static <P, M> Token createByParam(HasTokenConverter<P, M> f, P p) {
    Builder builder = ParameterList.getBuilder();
    f.getConverter().encode(builder, p);
    return TokenUtils.buildToken(f.getTokenName(), builder.build());
  }

  public static void newHistoryItem(Token token) {
    History.newItem(token.getValue());
  }
}
