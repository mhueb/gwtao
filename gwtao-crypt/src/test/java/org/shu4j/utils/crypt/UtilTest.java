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
package org.shu4j.utils.crypt;

import org.junit.Assert;
import org.junit.Test;

import com.gwtao.crypt.util.shared.Util;

public class UtilTest {

  @Test
  public void str2binl() {
    int[] result = Util.str2binl("Hallo", 8);

    int[] expected = {
        1,
        2,
        3 };
    Assert.assertArrayEquals(expected, result);
  }

  @Test
  public void binl2hex() {
    String t = Util.binl2hex(new int[]{0x61626364},false);
    Assert.assertEquals("64636261", t);
  }
}
