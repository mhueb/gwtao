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
package com.gwtao.ui.util.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;

public class GWTUtil {
	/**
	 * This function calculates scrollbarWidth and scrollbarHeight
	 * 
	 * 
	 * @see http://www.alexandre-gomes.com/?p=115
	 */
	public static int[] calcScrollbarSize() {
		ParagraphElement p = Document.get().createPElement();
		p.getStyle().setWidth(100, Unit.PCT);
		p.getStyle().setHeight(200, Unit.PX);

		DivElement div = Document.get().createDivElement();
		div.getStyle().setPosition(Position.ABSOLUTE);
		div.getStyle().setLeft(0, Unit.PX);
		div.getStyle().setTop(0, Unit.PX);
		div.getStyle().setWidth(200, Unit.PX);
		div.getStyle().setHeight(150, Unit.PX);
		div.getStyle().setVisibility(Visibility.HIDDEN);
		div.getStyle().setOverflow(Overflow.HIDDEN);
		div.appendChild(p);

		Document.get().getBody().appendChild(div);

		int w1 = p.getOffsetWidth();
		int h1 = p.getOffsetHeight();
		div.getStyle().setOverflow(Overflow.SCROLL);
		int w2 = p.getOffsetWidth();
		int h2 = p.getOffsetHeight();
		if (w1 == w2)
			w2 = div.getClientWidth();
		if (h1 == h2)
			h2 = div.getClientWidth();

		Document.get().getBody().removeChild(div);

		int sw = w1 - w2;
		int sh = h1 - h2;

		return new int[] { sw, sh };
	};
}
