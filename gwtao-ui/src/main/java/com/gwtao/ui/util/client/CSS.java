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

import org.apache.commons.lang.StringUtils;

import com.google.gwt.user.client.Element;

public class CSS {

  public static int calcHeightOffset(Element elem) {
    int yw = calcSize(elem, "marginTop", "marginBottom");
    yw += calcSize(elem, "paddingTop", "paddingBottom");
    return yw;
  }

  public static int calcWidthOffset(Element elem) {
    int xw = calcSize(elem, "marginLeft", "marginRight");
    xw += calcSize(elem, "paddingLeft", "paddingRight");
    return xw;
  }

  public static int calcTopOffset(Element elem) {
    return calcSize(elem, "marginTop", "paddingTop");
  }

  public static int calcLeftOffset(Element elem) {
    return calcSize(elem, "marginLeft", "paddingLeft");
  }

  private static int calcSize(Element elem, String a, String b) {
    String mls = CSS.getComputedStyle(elem, a);
    String mrs = CSS.getComputedStyle(elem, b);
    int ml = toPx(mls);
    ml = Math.max(0, ml);
    int mr = toPx(mrs);
    mr = Math.max(0, mr);
    int xw = ml + mr;
    return xw;
  }

  public static int toPx(String s) {
    if (StringUtils.isBlank(s))
      return 0;
    else if (s.contains("px")) {
      return Integer.parseInt(s.substring(0, s.length() - 2));
    }
    else
      throw new RuntimeException();
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
