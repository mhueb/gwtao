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

import com.google.gwt.user.client.Element;

public final class CSSUtils {

  public static int calcHeightOffset(Element elem) {
    int yw = calcSize(elem, "marginTop", "marginBottom", true);
    yw += calcSize(elem, "paddingTop", "paddingBottom", true);
    return yw;
  }

  public static int calcWidthOffset(Element elem) {
    int xw = calcSize(elem, "marginLeft", "marginRight", false);
    xw += calcSize(elem, "paddingLeft", "paddingRight", false);
    return xw;
  }

  public static int calcTopOffset(Element elem) {
    return calcSize(elem, "marginTop", "paddingTop", true);
  }

  public static int calcLeftOffset(Element elem) {
    return calcSize(elem, "marginLeft", "paddingLeft", false);
  }

  private static int calcSize(Element elem, String a, String b, boolean vertical) {
    String mls = CSSUtils.getComputedStyle(elem, a);
    String mrs = CSSUtils.getComputedStyle(elem, b);
    Element parent = elem.getParentElement().cast();
    int ml = UnitUtils.toPx(parent, mls, vertical);
    ml = Math.max(0, ml);
    int mr = UnitUtils.toPx(parent, mrs, vertical);
    mr = Math.max(0, mr);
    int xw = ml + mr;
    return xw;
  }

  public static native String getComputedStyle(Element el, String prop) /*-{
		var computedStyle;
		if (document.defaultView && document.defaultView.getComputedStyle) {
			computedStyle = document.defaultView.getComputedStyle(el, null)[prop];
		} else if (el.currentStyle) {
			computedStyle = el.currentStyle[prop];
		} else {
			computedStyle = el.style[prop];
		}
		return computedStyle;
  }-*/;

}
