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
