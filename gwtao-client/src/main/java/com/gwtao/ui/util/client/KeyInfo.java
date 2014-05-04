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
package com.gwtao.ui.util.client;

import org.shu4j.utils.util.Hasher;

public final class KeyInfo {
  public static final int NONE = 0;
  public static final int SHIFT = 1;
  public static final int CTRL = 2;
  public static final int ALT = 4;

  public static final int BACKSPACE = 8;
  public static final int TAB = 9;
  public static final int RETURN = 13;

  public static final int PAGE_UP = 33;
  public static final int PAGE_DOWN = 34;

  public static final int END = 35;
  public static final int POS1 = 36;

  public static final int CURSOR_LEFT = 37;
  public static final int CURSOR_UP = 38;
  public static final int CURSOR_RIGHT = 39;
  public static final int CURSOR_DOWN = 40;

  public static final int INSERT = 45;
  public static final int DELETE = 46;

  public static final int F1 = 112;
  public static final int F2 = 113;
  public static final int F3 = 114;
  public static final int F4 = 115;
  public static final int F5 = 116;
  public static final int F6 = 117;
  public static final int F7 = 118;
  public static final int F8 = 119;
  public static final int F9 = 120;
  public static final int F10 = 121;
  public static final int F11 = 122;
  public static final int F12 = 123;

  private int key;
  private boolean shift;
  private boolean ctrl;
  private boolean alt;

  public KeyInfo(int key, boolean shift, boolean ctrl, boolean alt) {
    this.key = key;
    this.shift = shift;
    this.ctrl = ctrl;
    this.alt = alt;
  }

  public int getKey() {
    return key;
  }

  public boolean isShift() {
    return shift;
  }

  public boolean isCtrl() {
    return ctrl;
  }

  public boolean isAlt() {
    return alt;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof KeyInfo) {
      KeyInfo o = (KeyInfo) obj;
      return key == o.key && shift == o.shift && ctrl == o.ctrl && alt == o.alt;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new Hasher(key).add(shift).add(ctrl).add(alt).getHash();
  }
}
