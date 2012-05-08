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
package com.gwtao.ui.util.client.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.gwtao.common.shared.permission.Permission;
import com.gwtao.ui.util.client.EventInfo;

public class ActionUtil {

  private static EventInfo lastEventInfo;

  // TODO evtl action interface erweiter als stdparameter oder ein IActionContext...?
  public static EventInfo getLastEventInfo() {
    return lastEventInfo;
  }

  public static void saveExecute(EventInfo eventInfo, IAction action, Object... data) {
    lastEventInfo = eventInfo;
    try {
      saveExecute(action, data);
    }
    finally {
      lastEventInfo = null;
    }
  }

  protected static void saveExecute(IAction action, Object... data) {
    try {
      if (action != null && action.getPermission(data) == Permission.ALLOWED)
        action.execute(data);
    }
    catch (Exception e) {
      UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
      if (uncaughtExceptionHandler != null)
        uncaughtExceptionHandler.onUncaughtException(e);
    }
  }
}
