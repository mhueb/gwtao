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
