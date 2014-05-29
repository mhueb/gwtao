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
package com.gwtao.ui.dialog.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PopupManager {
	private static final PopupManager INSTANCE = new PopupManager();

	public static PopupManager get() {
		return INSTANCE;
	}

	private final Map<Object, List<IPopupHandler>> hiddenPopups = new HashMap<Object, List<IPopupHandler>>();
	private List<IPopupHandler> handlers = new ArrayList<IPopupHandler>();

	private PopupManager() {
	}

	public void addHandler(IPopupHandler handler) {
		handlers.add(handler);
	}

	public void removeHandler(IPopupHandler handler) {
		handlers.remove(handler);
	}

	public String canHide() {
		StringBuilder buff = new StringBuilder();
		for (IPopupHandler h : handlers) {
			String msg = h.canHide();
			if (StringUtils.isNotBlank(msg))
				buff.append(msg).append("\n");
		}
		return StringUtils.trimToNull(buff.toString());
	}

	public void hide(Object context) {
		List<IPopupHandler> popups = handlers;
		handlers = new ArrayList<IPopupHandler>();
		hiddenPopups.put(context, popups);
		for (IPopupHandler h : popups)
			h.hide();
	}

	public void show(Object context) {
		List<IPopupHandler> popups = hiddenPopups.remove(context);
		if (popups != null) {
			handlers.addAll(popups);
			for (IPopupHandler h : popups)
				h.show();
		}
	}

	public void removeAll() {
		hiddenPopups.clear();
		handlers.clear();
	}

	public void removeHidden(String context) {
		hiddenPopups.remove(context);
	}
}
