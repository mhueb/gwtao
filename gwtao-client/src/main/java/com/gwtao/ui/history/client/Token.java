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
package com.gwtao.ui.history.client;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.shu4j.utils.util.Hasher;

/**
 * This class represents a item of the browser history.<br>
 * A token as part the URL is interpreted with this format:<br>
 * <br>
 * <code><pre>
 * Name [ '?' [ ( Param | ParamName [ '=' Value ] ) { '&' ( Param | ParamName [ '=' Value ] ) } ] ]
 * </pre></code>
 * 
 * <ul>
 * <li>Name id with parameters as main information</li>
 * <li>ParamName Parameter name</li>
 * <li>ParamValue Value of parameter</li>
 * <li>Param A Parameter</li>
 * </ul>
 * 
 * @author Matthias Hübner
 * 
 */
public final class Token {
  public static final Token NOWHERE = new Token("", null);

  private final String place;

  private final String parameters;

  protected Token(String place, String parameters) {
    Validate.notNull(place);
    this.place = place;
    this.parameters = StringUtils.trimToNull(parameters);
  }

  public String getValue() {
    return parameters == null ? place : place + "?" + parameters;
  }

  public boolean isNoWhere() {
    return place.length() == 0;
  }

  public String getPlace() {
    return place;
  }

  public String getParameters() {
    return parameters;
  }

  @Override
  public int hashCode() {
    return new Hasher().add(place).add(parameters).getHash();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Token other = (Token) obj;
    return ObjectUtils.equals(place, other.place) && ObjectUtils.equals(parameters, other.parameters);
  }

  @Override
  public String toString() {
    return "Token [place=" + place + ", parameters=" + parameters + "]";
  }

}
