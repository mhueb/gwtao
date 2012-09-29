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
    return MD5.hex_md5(text);
  };

  public static String sha256Hex(String data) {
    return SHA256.sha256(text);
  };
}
