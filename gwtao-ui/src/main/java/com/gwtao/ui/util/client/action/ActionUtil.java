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
package com.gwtao.ui.util.client.action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.gwtao.ui.util.client.EventInfo;
import com.gwtao.utils.shared.permission.Permission;

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
