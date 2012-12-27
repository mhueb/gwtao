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
package org.apache.commons.codec.digest;

import com.gwtao.crypt.md5.shared.MD5;
import com.gwtao.crypt.sha256.shared.SHA256;

/**
 * Emulates some DigestUtils operations to simplify common MessageDigest tasks.
 * 
 * @author Matthias Huebner
 * 
 */
public class DigestUtils {

  private static final SHA256 SHA256 = new SHA256();
  private static final MD5 MD5 = new MD5();

  public static String md5Hex(String data) {
    return new MD5().hex(data);
  };

  public static String sha256Hex(String data) {
    return new SHA256().sha256(data);
  };
}
