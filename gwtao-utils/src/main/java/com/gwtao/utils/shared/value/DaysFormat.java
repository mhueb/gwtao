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
package com.gwtao.utils.shared.value;

import java.util.Date;

import org.shu4j.utils.util.DateUtil;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.gwtao.utils.shared.rc.CommonConstants;
import com.gwtao.utils.shared.rc.CommonMessages;

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