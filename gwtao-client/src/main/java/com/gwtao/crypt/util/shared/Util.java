/* 
 * Copyright 2012 GWTAO
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
package com.gwtao.crypt.util.shared;

public class Util {

  /**
   * Add integers, wrapping at 2^32. This uses 16-bit operations internally to work around bugs in some JS
   * interpreters.
   */
  public static int safe_add(int x, int y) {
    return x + y;
    // int lsw = (x & 0xFFFF) + (y & 0xFFFF);
    // int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    // return (msw << 16) | (lsw & 0xFFFF);
  }

  /**
   * Bitwise rotate a 32-bit number to the left.
   */
  public static int bit_rol(int num, int cnt) {
    return (num << cnt) | (num >>> (32 - cnt));
  }

  /**
   * Convert a string to an array of little-endian words If chrsz is ASCII, characters >255 have their hi-byte
   * silently ignored.
   */
  public static int[] str2binl(String str, int chrsz) {
    int[] bin = new int[1 + ((str.length() * chrsz) >> 5)];
    int mask = (1 << chrsz) - 1;
    for (int i = 0; i < str.length() * chrsz; i += chrsz) {
      int val = str.charAt(i / chrsz) & mask;
      bin[i >> 5] |= val << (i % 32);
    }
    return bin;
  }

  public static int[] str2binb(String str, int chrsz) {
    int[] bin = new int[1 + ((str.length() * chrsz) >> 5)];
    int mask = (1 << chrsz) - 1;
    for (int i = 0; i < str.length() * chrsz; i += chrsz) {
      int val = str.charAt(i / chrsz) & mask;
      bin[i >> 5] |= val << (24 - i % 32);
    }
    return bin;
  }

  /**
   * Convert an array of little-endian words to a string
   */
  public static String binl2str(int[] bin, int chrsz) {
    StringBuilder buff = new StringBuilder();
    int mask = (1 << chrsz) - 1;
    for (int i = 0; i < bin.length * 32; i += chrsz)
      buff.append((char) ((bin[i >> 5] >>> (i % 32)) & mask));
    return buff.toString();
  }

  /**
   * Convert an array of little-endian words to a hex string.
   */
  public static String binl2hex(int[] binarray, boolean hexcase) {
    String hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    StringBuilder buff = new StringBuilder();
    for (int i = 0; i < binarray.length * 4; i++) {
      buff.append((char) hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 0xF));
      buff.append(hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 0xF));
    }
    return buff.toString();
  }

  public static String binb2hex(int[] binarray, boolean hexcase) {
    String hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    StringBuilder buff = new StringBuilder();
    for (int i = 0; i < binarray.length * 4; i++) {
      buff.append(hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF));
      buff.append(hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xF));
    }
    return buff.toString();
  }

  /**
   * Convert an array of little-endian words to a base-64 string
   */
  public static String binl2b64(int[] binarray, String b64pad) {
    String tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    StringBuilder buff = new StringBuilder();
    for (int i = 0; i < binarray.length * 4; i += 3) {
      int triplet = (((binarray[i >> 2] >> 8 * (i % 4)) & 0xFF) << 16) | (((binarray[i + 1 >> 2] >> 8 * ((i + 1) % 4)) & 0xFF) << 8) | ((binarray[i + 2 >> 2] >> 8 * ((i + 2) % 4)) & 0xFF);
      for (int j = 0; j < 4; j++) {
        if (i * 8 + j * 6 > binarray.length * 32)
          buff.append(b64pad);
        else
          buff.append(tab.charAt((triplet >> 6 * (3 - j)) & 0x3F));
      }
    }
    return buff.toString();
  }

  public static int[] join(int[] a, int... b) {
    int[] joined = new int[a.length + b.length];
    for (int i = 0; i < a.length; ++i)
      joined[i] = a[i];
    for (int i = 0; i < b.length; ++i)
      joined[a.length + i] = b[i];
    return joined;
  }

  public static int[] extend(int[] x, int sz) {
    if (x.length < sz) {
      int[] y = new int[sz];
      for (int i = 0; i < x.length; ++i)
        y[i] = x[i];
      return y;
    }
    else
      return x;
  }

  public static String Utf8Encode(String string) {
    string = string.replaceAll("\r\n", "\n");
    StringBuilder utftext = new StringBuilder();

    for (int n = 0; n < string.length(); n++) {

      int c = string.charAt(n);

      if (c < 128) {
        utftext.append((char) (c));
      }
      else if ((c > 127) && (c < 2048)) {
        utftext.append((char) ((c >> 6) | 192));
        utftext.append((char) ((c & 63) | 128));
      }
      else {
        utftext.append((char) ((c >> 12) | 224));
        utftext.append((char) (((c >> 6) & 63) | 128));
        utftext.append((char) ((c & 63) | 128));
      }

    }

    return utftext.toString();
  }

}
