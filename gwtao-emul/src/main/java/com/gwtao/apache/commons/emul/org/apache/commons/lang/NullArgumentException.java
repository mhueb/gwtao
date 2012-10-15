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
package org.apache.commons.lang;

import java.io.Serializable;

/**
 * Emulation of org.apache.commons.lang.NullArgumentException
 * 
 * @author Matthias Hübner
 * 
 */
public class NullArgumentException extends IllegalArgumentException {
  private static final long serialVersionUID = 1174360235354917591L;
  
  public NullArgumentException(java.lang.String argName) {
    super((argName == null ? "Argument" : argName) + " must not be null.");
  }
}
