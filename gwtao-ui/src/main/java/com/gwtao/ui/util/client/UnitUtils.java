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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.aria.client.State;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public final class UnitUtils {

  private static final Map<String, Unit> unitMap = new HashMap<String, Style.Unit>();
  private static DivElement fixedRuler;
  private static DivElement relativeRuler;

  static {
    fixedRuler = createRuler(Unit.CM, Unit.CM);
    Document.get().getBody().appendChild(fixedRuler);
    for (Unit unit : Unit.values())
      unitMap.put(unit.getType(), unit);
  }

  private static DivElement createRuler(Unit widthUnit, Unit heightUnit) {
    DivElement ruler = Document.get().createDivElement();
    ruler.setInnerHTML("&nbsp;");
    Style style = ruler.getStyle();
    style.setPosition(Position.ABSOLUTE);
    style.setZIndex(-32767);

    // Position the ruler off the top edge, double the size just to be
    // extra sure it doesn't show up on the screen.
    style.setTop(-20, heightUnit);

    // Note that we are making the ruler element 10x10, because some browsers
    // generate non-integral ratios (e.g., 1em == 13.3px), so we need a little
    // extra precision.
    style.setWidth(10, widthUnit);
    style.setHeight(10, heightUnit);

    State.HIDDEN.set(ruler, true);
    return ruler;
  }

  public static int toPx(Element parent, String size, boolean vertical) {
    if (StringUtils.isBlank(size))
      return 0;

    int i;
    for( i=size.length()-1; i>0; --i ) {
      int c = size.charAt(i);
      if(c >= '0' && c <= '9' )
        break;
    }
    
    String unitString = size.substring(i+1);

    Unit unit = unitMap.get(unitString);
    if( unit == null )
      return 0;

    String valueString = size.substring(0,i+1);
    double value = Double.valueOf(valueString);

    double unitSizeInPixels = getUnitSizeInPixels(parent,unit,vertical);
    return (int) (unitSizeInPixels * value + 0.5);
  }

  public static double getUnitSizeInPixels(Element parent, Unit unit, boolean vertical) {
    if (unit == null) {
      return 1;
    }
    switch (unit) {
    case PCT:
      return (vertical ? parent.getClientHeight() : parent.getClientWidth()) / 100.0;
    case EM:
      return relativeRuler.getOffsetWidth() / 10.0;
    case EX:
      return relativeRuler.getOffsetHeight() / 10.0;
    case CM:
      return fixedRuler.getOffsetWidth() * 0.1; // 1.0 cm / cm
    case MM:
      return fixedRuler.getOffsetWidth() * 0.01; // 0.1 cm / mm
    case IN:
      return fixedRuler.getOffsetWidth() * 0.254; // 2.54 cm / in
    case PT:
      return fixedRuler.getOffsetWidth() * 0.00353; // 0.0353 cm / pt
    case PC:
      return fixedRuler.getOffsetWidth() * 0.0423; // 0.423 cm / pc
    default:
    case PX:
      return 1;
    }
  }
}
