package org.apache.commons.codec.digest;

/**
 * Emulates some DigitUtils Operations to simplify common MessageDigest tasks.
 * 
 * @author Matthias Huebner
 * 
 */
public class DigestUtils {

  /**
   * A JavaScript implementation of the RSA Data Security, Inc. <br>
   * MD5 Message Digest Algorithm, as defined in RFC 1321.<br>
   * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.<br>
   * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet<br>
   * Distributed under the BSD License See http://pajhome.org.uk/crypt/md5 for more info.
   */
  public static native String md5Hex(String data) /*-{
		return $wnd.hex_md5(text);
  }-*/;

  /**
   * Secure Hash Algorithm (SHA256) http://www.webtoolkit.info/.<br>
   * Original code by Angel Marin, Paul Johnston.
   */
  public static native String sha256Hex(String data) /*-{
		return $wnd.SHA256(text);
  }-*/;
}
