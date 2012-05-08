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

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.gwtao.common.shared.i18n.CommonConstants;
import com.gwtao.common.shared.i18n.CommonMessages;

public class DaysFormat {
	private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	private static final DateTimeFormat timeFormat = DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT);

	public String format(Date date) {
		if (date != null) {
			long days = DateUtil.calcDays(new Date(), date);
			if (days == 0)
				return CommonConstants.c.today() + " " + timeFormat.format(date);
			else if (days == 1)
				return CommonConstants.c.yesterday() + " " + timeFormat.format(date);
			else if (days > 0 && days <= 5)
				return CommonMessages.c.daysAgo(days) + " " + timeFormat.format(date);
			else if (days < 0 && days >= -5)
				return CommonMessages.c.inDays(-days) + " " + timeFormat.format(date);
			else
				return dateFormat.format(date) + " " + timeFormat.format(date);
		} else
			return null;
	}
}