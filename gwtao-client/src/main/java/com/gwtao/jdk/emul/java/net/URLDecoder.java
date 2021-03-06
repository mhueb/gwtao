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
package java.net;

import java.io.UnsupportedEncodingException;

import com.google.gwt.http.client.URL;

public class URLDecoder {
  private static final String UTF_8 = "UTF-8";

  public static String decode(String s, String enc) throws UnsupportedEncodingException {
    if (!UTF_8.equals(enc))
      throw new UnsupportedEncodingException(enc);

    return URL.decodeComponent(s);
  }
}
