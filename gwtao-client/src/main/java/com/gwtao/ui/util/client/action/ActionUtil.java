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

import org.shu4j.utils.permission.Permission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.EventInfo;

public class ActionUtil {

  private static EventInfo lastEventInfo;

  // TODO evtl action interface erweiter als stdparameter oder ein IActionContext...?
  public static EventInfo getLastEventInfo() {
    return lastEventInfo;
  }

  public static void saveExecute(EventInfo eventInfo, IAction action, IDataSource<?> source) {
    if (source.getPermission() == Permission.ALLOWED) {
      if (source.getData() instanceof Object[])
        saveExecute(action, (Object[]) source.getData());
      else
        saveExecute(action, source.getData());
    }
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

  public static Permission getPermission(IAction action, IDataSource<?> source) {
    Permission result = source.getPermission();
    if (source.getData() instanceof Object[])
      return result.add(action.getPermission((Object[]) source.getData()));
    else
      return result.add(action.getPermission(source.getData()));
  }
}
