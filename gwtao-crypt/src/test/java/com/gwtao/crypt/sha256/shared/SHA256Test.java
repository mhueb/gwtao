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
