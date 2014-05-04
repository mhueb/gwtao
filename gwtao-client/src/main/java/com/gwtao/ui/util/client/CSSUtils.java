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

  public static int calcMarginHeight(Element elem) {
    return calcSize(elem, "marginTop", true) + calcSize(elem, "marginBottom", true);
  }

  public static int calcMarginWidth(Element elem) {
    return calcSize(elem, "marginLeft", false) + calcSize(elem, "marginRight", false);
  }

  public static int calcMarginLeft(Element elem) {
    return calcSize(elem, "marginLeft", true);
  }

  public static int calcMarginTop(Element elem) {
    return calcSize(elem, "marginTop", true);
  }

  public static int calcBorderHeight(Element elem) {
    return calcSize(elem, "borderTopWidth", true) + calcSize(elem, "borderBottomWidth", true);
  }

  public static int calcBorderWidth(Element elem) {
    return calcSize(elem, "borderLeftWidth", false) + calcSize(elem, "borderRightWidth", false);
  }

  public static int calcBorderLeft(Element elem) {
    return calcSize(elem, "borderLeftWidth", true);
  }

  public static int calcBorderTop(Element elem) {
    return calcSize(elem, "borderTopWidth", true);
  }

  public static int calcPaddingHeight(Element elem) {
    return calcSize(elem, "paddingTop", true) + calcSize(elem, "paddingBottom", true);
  }

  public static int calcPaddingWidth(Element elem) {
    return calcSize(elem, "paddingLeft", false) + calcSize(elem, "paddingRight", false);
  }
  
  private static int calcSize(Element elem, String style, boolean vertical) {
    String mls = CSSUtils.getComputedStyle(elem, style);
    Element parent = elem.getParentElement().cast();
    int ml = UnitUtils.toPx(parent, mls, vertical);
    ml = Math.max(0, ml);
    return ml;
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
