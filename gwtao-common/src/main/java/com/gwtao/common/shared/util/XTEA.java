/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.common.shared.util;

/**
 * XTEA - eXtended Tiny Encryption Algorithm<br>
 * Based on XTEA designed by David Wheeler and Roger Needham of the Cambridge Computer Laboratory.
 * 
 * @author Matthias Huebner
 * 
 * @see <a href="http://en.wikipedia.org/wiki/XTEA">http://en.wikipedia.org/wiki/XTEA</a>
 */
public class XTEA {

  /**
   * Primary encryption algorithm<br>
   * 
   * @author matthuebner
   */
  public static class Engine {
    private static final int delta = 0x9E3779B9;

    private final int rounds;
    private final int[] key;
    private final int keybits;

    public Engine(int[] key) {
      this(key, 32);
    }

    public Engine(int[] key, int rounds) {
      if (rounds <= 0)
        throw new IllegalArgumentException();
      if (key.length == 0 || (key.length & 3) != 0)
        throw new IllegalArgumentException();
      this.key = key;
      this.rounds = rounds;
      this.keybits = key.length - 1;
    }

    public void encrypt(int[] data) {
      if (data.length == 0 || (data.length & 1) != 0)
        throw new IllegalArgumentException();

      for (int idx = 0; idx < data.length; idx += 2)
        encrypt(data, idx);
    }

    public void encrypt(int[] data, int idx) {
      if (data.length < idx + 1)
        throw new IllegalArgumentException();

      int sum = 0;
      int v0 = data[idx];
      int v1 = data[idx + 1];

      for (int round = 0; round < rounds; ++round) {
        v0 += (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + key[(idx + sum) & keybits]);
        sum += delta;
        v1 += (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + key[(idx + (sum >>> 11)) & keybits]);
      }

      data[idx] = v0;
      data[idx + 1] = v1;
    }

    public void decrypt(int[] data) {
      if (data.length == 0 || (data.length & 1) != 0)
        throw new IllegalArgumentException();

      for (int idx = 0; idx < data.length; idx += 2)
        decrypt(data, idx);
    }

    public void decrypt(int[] data, int idx) {
      if (data.length < idx + 1)
        throw new IllegalArgumentException();

      int sum = delta * rounds;
      int v0 = data[idx];
      int v1 = data[idx + 1];

      for (int round = 0; round < rounds; ++round) {
        v1 -= (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ (sum + key[(idx + (sum >>> 11)) & keybits]);
        sum -= delta;
        v0 -= (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ (sum + key[(idx + sum) & keybits]);
      }

      data[idx] = v0;
      data[idx + 1] = v1;
    }
  }

  private final int[] key;

  // GWT kann kein byte <-> String übergang
  // public XTEA(String key) {
  // this(key.getBytes());
  // }

  public XTEA(byte[] key) {
    if (key.length < 1)
      throw new IllegalArgumentException();

    this.key = new int[alignInt(alignX(key.length, 16))];
    for (int i = 0; i < this.key.length; i += 1)
      this.key[i] = makeInt(key, i << 2);
  }

  public byte[] encrypt(byte[] data) {
    int[] buffer = toIntArray(data);
    new Engine(key).encrypt(buffer);
    return toByteArray(buffer);
  }

  public byte[] decrypt(byte[] data) {
    int[] buffer = toIntArray2(data);
    new Engine(key).decrypt(buffer);
    return toByteArray2(buffer);
  }

  // public byte[] encryptString(String text) {
  // return encrypt(text.getBytes());
  // }
  //
  // public String decryptString(byte[] data) {
  // return new String(decrypt(data));
  // }

  private static int alignX(int length, int i) {
    int r = length / i * i;
    if (r != length)
      r += i;
    return r;
  }

  private static int alignInt(int length) {
    int r = length >> 2;
    if ((r << 2) != length)
      r += 1;
    return r;
  }

  private static int[] toIntArray(byte[] data) {
    int[] buffer = new int[alignInt(alignX(data.length + 4, 8))];
    buffer[0] = data.length;
    for (int i = 1; i < buffer.length; i++)
      buffer[i] = makeInt(data, (i - 1) << 2);
    return buffer;
  }

  private static int[] toIntArray2(byte[] data) {
    int[] buffer = new int[alignInt(alignX(data.length, 8))];
    for (int i = 0; i < buffer.length; i++)
      buffer[i] = makeInt(data, i << 2);
    return buffer;
  }

  private static int makeInt(byte[] bytes, int offset) {
    int r = 0;
    for (int n = 0; n < 4; ++n)
      r = (r << 8) | (bytes[offset++ % bytes.length] & 0xff);
    return r;
  }

  private static byte[] toByteArray(int[] buffer) {
    byte[] encrypted = new byte[buffer.length << 2];
    for (int i = 0; i < buffer.length; ++i)
      toByteArray(encrypted, i << 2, buffer[i]);
    return encrypted;
  }

  private static byte[] toByteArray2(int[] buffer) {
    byte[] encrypted = new byte[buffer[0]];
    for (int i = 0; i < encrypted.length; i += 4)
      toByteArray(encrypted, i, buffer[i / 4 + 1]);
    return encrypted;
  }

  private static void toByteArray(byte[] encrypted, int idx, int val) {
    encrypted[idx++] = (byte) (((val >>> 24)) & 0xff);
    if (idx < encrypted.length) {
      encrypted[idx++] = (byte) (((val >>> 16)) & 0xff);
      if (idx < encrypted.length) {
        encrypted[idx++] = (byte) (((val >>> 8)) & 0xff);
        if (idx < encrypted.length) {
          encrypted[idx] = (byte) (((val)) & 0xff);
        }
      }
    }
  }
}
