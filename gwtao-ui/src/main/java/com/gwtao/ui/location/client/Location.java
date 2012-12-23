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
import org.apache.commons.lang.Validate;

/**
 * This class represents a item of the browser history.<br>
 * A location of an URL is interpreted with this format:<br>
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
public final class Location {
  private final String id;
  private final String parameters;

  protected Location(String id, String parameters) {
    Validate.notEmpty(id);
    this.id = id;
    this.parameters = StringUtils.trimToNull(parameters);
  }

  public String getValue() {
    return parameters == null ? id : id + "?" + parameters;
  }

  public String getId() {
    return id;
  }

  public String getParameters() {
    return parameters;
  }
}
