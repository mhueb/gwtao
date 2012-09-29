package com.gwtao.crypt.md5.shared;

import com.gwtao.crypt.util.shared.Util;

/**
 * Based on a JavaScript implementation of the RSA Data Security, Inc. MD5 Message Digest Algorithm, as
 * defined in RFC 1321. Version 2.1 Copyright (C) Paul Johnston 1999 - 2002. Other contributors: Greg Holt,
 * Andrew Kepert, Ydnar, Lostinet Distributed under the BSD License See http://pajhome.org.uk/crypt/md5 for
 * more info.
 * 
 * @author Matt
 * 
 */
public class MD5 {

  /** hex output format. 0 - lowercase; 1 - uppercase */
  private boolean hexcase;

  /** base-64 pad character. "=" for strict RFC compliance */
  private String b64pad = "";

  /** bits per input character. 8 - ASCII; 16 - Unicode */
  private int chrsz = 8;

  /**
   * These are the functions you'll usually want to call They take string arguments and return either hex or
   * base-64 encoded strings
   */
  public String hex_md5(String s) {
    return Util.binl2hex(core_md5(Util.str2binl(s, chrsz), s.length() * chrsz), hexcase);
  }

  public String b64_md5(String s) {
    return Util.binl2b64(core_md5(Util.str2binl(s, chrsz), s.length() * chrsz), b64pad);
  }

  public String str_md5(String s) {
    return Util.binl2str(core_md5(Util.str2binl(s, chrsz), s.length() * chrsz), chrsz);
  }

  public String hex_hmac_md5(String key, String data) {
    return Util.binl2hex(core_hmac_md5(key, data), hexcase);
  }

  public String b64_hmac_md5(String key, String data) {
    return Util.binl2b64(core_hmac_md5(key, data), b64pad);
  }

  public String str_hmac_md5(String key, String data) {
    return Util.binl2str(core_hmac_md5(key, data), chrsz);
  }

  /*
   * Calculate the MD5 of an array of little-endian words, and a bit length
   */
  private int[] core_md5(int[] x, int len) {
    /* append padding */
    x[len >> 5] |= 0x80 << ((len) % 32);
    int ts = (((len + 64) >>> 9) << 4) + 14;
    x = Util.extend(x, ts + 2);
    x[ts] = len;

    int a = 1732584193;
    int b = -271733879;
    int c = -1732584194;
    int d = 271733878;

    for (int i = 0; i < x.length; i += 16) {
      int olda = a;
      int oldb = b;
      int oldc = c;
      int oldd = d;

      a = md5_ff(a, b, c, d, x[i + 0], 7, -680876936);
      d = md5_ff(d, a, b, c, x[i + 1], 12, -389564586);
      c = md5_ff(c, d, a, b, x[i + 2], 17, 606105819);
      b = md5_ff(b, c, d, a, x[i + 3], 22, -1044525330);
      a = md5_ff(a, b, c, d, x[i + 4], 7, -176418897);
      d = md5_ff(d, a, b, c, x[i + 5], 12, 1200080426);
      c = md5_ff(c, d, a, b, x[i + 6], 17, -1473231341);
      b = md5_ff(b, c, d, a, x[i + 7], 22, -45705983);
      a = md5_ff(a, b, c, d, x[i + 8], 7, 1770035416);
      d = md5_ff(d, a, b, c, x[i + 9], 12, -1958414417);
      c = md5_ff(c, d, a, b, x[i + 10], 17, -42063);
      b = md5_ff(b, c, d, a, x[i + 11], 22, -1990404162);
      a = md5_ff(a, b, c, d, x[i + 12], 7, 1804603682);
      d = md5_ff(d, a, b, c, x[i + 13], 12, -40341101);
      c = md5_ff(c, d, a, b, x[i + 14], 17, -1502002290);
      b = md5_ff(b, c, d, a, x[i + 15], 22, 1236535329);

      a = md5_gg(a, b, c, d, x[i + 1], 5, -165796510);
      d = md5_gg(d, a, b, c, x[i + 6], 9, -1069501632);
      c = md5_gg(c, d, a, b, x[i + 11], 14, 643717713);
      b = md5_gg(b, c, d, a, x[i + 0], 20, -373897302);
      a = md5_gg(a, b, c, d, x[i + 5], 5, -701558691);
      d = md5_gg(d, a, b, c, x[i + 10], 9, 38016083);
      c = md5_gg(c, d, a, b, x[i + 15], 14, -660478335);
      b = md5_gg(b, c, d, a, x[i + 4], 20, -405537848);
      a = md5_gg(a, b, c, d, x[i + 9], 5, 568446438);
      d = md5_gg(d, a, b, c, x[i + 14], 9, -1019803690);
      c = md5_gg(c, d, a, b, x[i + 3], 14, -187363961);
      b = md5_gg(b, c, d, a, x[i + 8], 20, 1163531501);
      a = md5_gg(a, b, c, d, x[i + 13], 5, -1444681467);
      d = md5_gg(d, a, b, c, x[i + 2], 9, -51403784);
      c = md5_gg(c, d, a, b, x[i + 7], 14, 1735328473);
      b = md5_gg(b, c, d, a, x[i + 12], 20, -1926607734);

      a = md5_hh(a, b, c, d, x[i + 5], 4, -378558);
      d = md5_hh(d, a, b, c, x[i + 8], 11, -2022574463);
      c = md5_hh(c, d, a, b, x[i + 11], 16, 1839030562);
      b = md5_hh(b, c, d, a, x[i + 14], 23, -35309556);
      a = md5_hh(a, b, c, d, x[i + 1], 4, -1530992060);
      d = md5_hh(d, a, b, c, x[i + 4], 11, 1272893353);
      c = md5_hh(c, d, a, b, x[i + 7], 16, -155497632);
      b = md5_hh(b, c, d, a, x[i + 10], 23, -1094730640);
      a = md5_hh(a, b, c, d, x[i + 13], 4, 681279174);
      d = md5_hh(d, a, b, c, x[i + 0], 11, -358537222);
      c = md5_hh(c, d, a, b, x[i + 3], 16, -722521979);
      b = md5_hh(b, c, d, a, x[i + 6], 23, 76029189);
      a = md5_hh(a, b, c, d, x[i + 9], 4, -640364487);
      d = md5_hh(d, a, b, c, x[i + 12], 11, -421815835);
      c = md5_hh(c, d, a, b, x[i + 15], 16, 530742520);
      b = md5_hh(b, c, d, a, x[i + 2], 23, -995338651);

      a = md5_ii(a, b, c, d, x[i + 0], 6, -198630844);
      d = md5_ii(d, a, b, c, x[i + 7], 10, 1126891415);
      c = md5_ii(c, d, a, b, x[i + 14], 15, -1416354905);
      b = md5_ii(b, c, d, a, x[i + 5], 21, -57434055);
      a = md5_ii(a, b, c, d, x[i + 12], 6, 1700485571);
      d = md5_ii(d, a, b, c, x[i + 3], 10, -1894986606);
      c = md5_ii(c, d, a, b, x[i + 10], 15, -1051523);
      b = md5_ii(b, c, d, a, x[i + 1], 21, -2054922799);
      a = md5_ii(a, b, c, d, x[i + 8], 6, 1873313359);
      d = md5_ii(d, a, b, c, x[i + 15], 10, -30611744);
      c = md5_ii(c, d, a, b, x[i + 6], 15, -1560198380);
      b = md5_ii(b, c, d, a, x[i + 13], 21, 1309151649);
      a = md5_ii(a, b, c, d, x[i + 4], 6, -145523070);
      d = md5_ii(d, a, b, c, x[i + 11], 10, -1120210379);
      c = md5_ii(c, d, a, b, x[i + 2], 15, 718787259);
      b = md5_ii(b, c, d, a, x[i + 9], 21, -343485551);

      a = Util.safe_add(a, olda);
      b = Util.safe_add(b, oldb);
      c = Util.safe_add(c, oldc);
      d = Util.safe_add(d, oldd);
    }
    return new int[] {
        a,
        b,
        c,
        d };

  }

  /*
   * These functions implement the four basic operations the algorithm uses.
   */
  private int md5_cmn(int q, int a, int b, int x, int s, int t) {
    return Util.safe_add(Util.bit_rol(Util.safe_add(Util.safe_add(a, q), Util.safe_add(x, t)), s), b);
  }

  private int md5_ff(int a, int b, int c, int d, int x, int s, int t) {
    return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
  }

  private int md5_gg(int a, int b, int c, int d, int x, int s, int t) {
    return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
  }

  private int md5_hh(int a, int b, int c, int d, int x, int s, int t) {
    return md5_cmn(b ^ c ^ d, a, b, x, s, t);
  }

  private int md5_ii(int a, int b, int c, int d, int x, int s, int t) {
    return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
  }

  /*
   * Calculate the HMAC-MD5, of a key and some data
   */
  private int[] core_hmac_md5(String key, String data) {
    int[] bkey = Util.str2binl(key, chrsz);
    if (bkey.length > 16)
      bkey = core_md5(bkey, key.length() * chrsz);

    int[] ipad = new int[16];
    int[] opad = new int[16];
    for (int i = 0; i < 16; i++) {
      ipad[i] = bkey[i] ^ 0x36363636;
      opad[i] = bkey[i] ^ 0x5C5C5C5C;
    }

    int[] hash = core_md5(Util.join(ipad, Util.str2binl(data, chrsz)), 512 + data.length() * chrsz);
    return core_md5(Util.join(opad, hash), 512 + 128);
  }

}
