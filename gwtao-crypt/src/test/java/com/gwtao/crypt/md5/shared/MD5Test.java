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
package com.gwtao.crypt.md5.shared;

import org.junit.Assert;
import org.junit.Test;

public class MD5Test {

  /*
   * Perform a simple self-test to see if the VM is working
   */
  @Test
  public void md5_vm_test() {
    Assert.assertEquals("900150983cd24fb0d6963f7d28e17f72", new MD5().hex("abc"));
    Assert.assertEquals("7dc0898a1c0dcc36313970d5db996ea7", new MD5().hex("sdfkdsjfsdkljfäööw33"));
  }

}
