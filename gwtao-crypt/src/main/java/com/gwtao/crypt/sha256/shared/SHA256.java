package com.gwtao.crypt.sha256.shared;

import com.gwtao.crypt.util.shared.Util;

/**
 * 
 * Secure Hash Algorithm (SHA256) http://www.webtoolkit.info/
 * 
 * Original code by Angel Marin, Paul Johnston.
 * 
 */
public class SHA256 {

  private static final int[] K = new int[] {
      0x428A2F98,
      0x71374491,
      0xB5C0FBCF,
      0xE9B5DBA5,
      0x3956C25B,
      0x59F111F1,
      0x923F82A4,
      0xAB1C5ED5,
      0xD807AA98,
      0x12835B01,
      0x243185BE,
      0x550C7DC3,
      0x72BE5D74,
      0x80DEB1FE,
      0x9BDC06A7,
      0xC19BF174,
      0xE49B69C1,
      0xEFBE4786,
      0xFC19DC6,
      0x240CA1CC,
      0x2DE92C6F,
      0x4A7484AA,
      0x5CB0A9DC,
      0x76F988DA,
      0x983E5152,
      0xA831C66D,
      0xB00327C8,
      0xBF597FC7,
      0xC6E00BF3,
      0xD5A79147,
      0x6CA6351,
      0x14292967,
      0x27B70A85,
      0x2E1B2138,
      0x4D2C6DFC,
      0x53380D13,
      0x650A7354,
      0x766A0ABB,
      0x81C2C92E,
      0x92722C85,
      0xA2BFE8A1,
      0xA81A664B,
      0xC24B8B70,
      0xC76C51A3,
      0xD192E819,
      0xD6990624,
      0xF40E3585,
      0x106AA070,
      0x19A4C116,
      0x1E376C08,
      0x2748774C,
      0x34B0BCB5,
      0x391C0CB3,
      0x4ED8AA4A,
      0x5B9CCA4F,
      0x682E6FF3,
      0x748F82EE,
      0x78A5636F,
      0x84C87814,
      0x8CC70208,
      0x90BEFFFA,
      0xA4506CEB,
      0xBEF9A3F7,
      0xC67178F2 };

  int chrsz = 8;
  boolean hexcase;

  public String sha256(String s) {
    String s8 = Util.Utf8Encode(s);
    return Util.binb2hex(core_sha256(Util.str2binb(s8, chrsz), s8.length() * chrsz), hexcase);
  }

  private static int S(int X, int n) {
    return (X >>> n) | (X << (32 - n));
  }

  private static int R(int X, int n) {
    return (X >>> n);
  }

  private static int Ch(int x, int y, int z) {
    return ((x & y) ^ ((~x) & z));
  }

  private static int Maj(int x, int y, int z) {
    return ((x & y) ^ (x & z) ^ (y & z));
  }

  private static int Sigma0256(int x) {
    return (S(x, 2) ^ S(x, 13) ^ S(x, 22));
  }

  private static int Sigma1256(int x) {
    return (S(x, 6) ^ S(x, 11) ^ S(x, 25));
  }

  private static int Gamma0256(int x) {
    return (S(x, 7) ^ S(x, 18) ^ R(x, 3));
  }

  private static int Gamma1256(int x) {
    return (S(x, 17) ^ S(x, 19) ^ R(x, 10));
  }

  private int[] core_sha256(int[] m, int l) {
    int[] HASH = new int[] {
        0x6A09E667,
        0xBB67AE85,
        0x3C6EF372,
        0xA54FF53A,
        0x510E527F,
        0x9B05688C,
        0x1F83D9AB,
        0x5BE0CD19 };
    
    int[] W = new int[64];

    m[l >> 5] |= 0x80 << (24 - l % 32);
    
    int sz = ((l + 64 >> 9) << 4) + 15;
    m = Util.extend(m, sz+1);
    m[sz] = l;

    for (int i = 0; i < m.length; i += 16) {
      int  a = HASH[0];
      int  b = HASH[1];
      int  c = HASH[2];
      int  d = HASH[3];
      int  e = HASH[4];
      int   f = HASH[5];
      int  g = HASH[6];
      int   h = HASH[7];

      for (int j = 0; j < 64; j++) {
        if (j < 16)
          W[j] = m[j + i];
        else
          W[j] = Util.safe_add(Util.safe_add(Util.safe_add(Gamma1256(W[j - 2]), W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);

        int T1 = Util.safe_add(Util.safe_add(Util.safe_add(Util.safe_add(h, Sigma1256(e)), Ch(e, f, g)), K[j]), W[j]);
        int T2 = Util.safe_add(Sigma0256(a), Maj(a, b, c));

        h = g;
        g = f;
        f = e;
        e = Util.safe_add(d, T1);
        d = c;
        c = b;
        b = a;
        a = Util.safe_add(T1, T2);
      }

      HASH[0] = Util.safe_add(a, HASH[0]);
      HASH[1] = Util.safe_add(b, HASH[1]);
      HASH[2] = Util.safe_add(c, HASH[2]);
      HASH[3] = Util.safe_add(d, HASH[3]);
      HASH[4] = Util.safe_add(e, HASH[4]);
      HASH[5] = Util.safe_add(f, HASH[5]);
      HASH[6] = Util.safe_add(g, HASH[6]);
      HASH[7] = Util.safe_add(h, HASH[7]);
    }
    return HASH;
  }
}
