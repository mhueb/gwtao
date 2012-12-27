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
package com.gwtao.crypt.sha256.shared;

import org.junit.Assert;
import org.junit.Test;

public class SHA256Test {

  @Test
  public void sha256() {
    String sha256 = new SHA256().sha256("Hallo");
    Assert.assertNotNull("753692ec36adb4c794c973945eb2a99c1649703ea6f76bf259abb4fb838e013e", sha256);
    sha256 = new SHA256().sha256("101kdmc94jkcdmsadfefcsdöofsldöi,jfsdlaf");
    Assert.assertNotNull("14370aa251e1e1088384033e309084bdb61598131d04417e8ed6f82498fae20e", sha256);
  }

}
