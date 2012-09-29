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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a item of the browser history.<br>
 * A location of an URL is interpreted in this format:<br>
 * <br>
 * <code>
 * TokenId ['?' ParamId [ '=' Value ] { '&' ParamId [ '=' Value ] } ]
 * </code>
 * 
 * <ul>
 * <li>TokenId id with parameters as main information</li>
 * <li>ParamId Parameter identifier</li>
 * <li>Value Value of parameter</li>
 * </ul>
 * 
 * @author Matthias Hübner
 * 
 */
public final class Token {
  public static final class Parameter {
    private final String token;
    private final String value;

    public Parameter(String token, String value) {
      this.token = token;
      this.value = value;
    }

    public String getToken() {
      return token;
    }

    public String getValue() {
      return value;
    }
  }

  private final String value;
  private final String id;
  private final List<Parameter> parameters;

  private Token(String value, String id, Parameter... parameters) {
    this.value = value;
    this.id = id;
    this.parameters = Arrays.asList(parameters);
  }

  public String getValue() {
    return value;
  }

  public String getId() {
    return id;
  }

  public List<Parameter> getParameters() {
    return Collections.unmodifiableList(parameters);
  }

  public static Token create(String token) {
    // TODO Auto-generated method stub
    return null;
  }

  public static String create(String id, List<Parameter> parameter) {
    // TODO Auto-generated method stub
    return null;
  }
}
