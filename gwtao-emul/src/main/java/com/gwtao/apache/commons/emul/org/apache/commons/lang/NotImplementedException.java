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

/**
 * Emulation of org.apache.commons.lang.NotImplementedException
 * 
 * @author Matthias Hübner
 * 
 */
public class NotImplementedException extends UnsupportedOperationException {

    private static final long serialVersionUID = -6894122266938754088L;

    private static final String DEFAULT_MESSAGE = "Code is not implemented";
    
    public NotImplementedException() {
        super(DEFAULT_MESSAGE);
    }

    public NotImplementedException(String msg) {
        super(msg == null ? DEFAULT_MESSAGE : msg);
    }

    public NotImplementedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public NotImplementedException(String msg, Throwable cause) {
        super(msg == null ? DEFAULT_MESSAGE : msg, cause );
    }

    public NotImplementedException(Class clazz) {
        super(clazz == null ? DEFAULT_MESSAGE : DEFAULT_MESSAGE + " in " + clazz);
    }
}
