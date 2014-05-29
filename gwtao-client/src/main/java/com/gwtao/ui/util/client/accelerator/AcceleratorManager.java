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
package com.gwtao.ui.util.client.accelerator;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.gwtao.ui.util.client.EventInfo;
import com.gwtao.ui.util.client.GWTUtils;
import com.gwtao.ui.util.client.GlobalExceptionHandler;
import com.gwtao.ui.util.client.KeyInfo;

public class AcceleratorManager {
  private final List<IAcceleratorHandler> handlerList = new ArrayList<IAcceleratorHandler>();

  public AcceleratorManager() {
    Event.addNativePreviewHandler(new NativePreviewHandler() {

      public void onPreviewNativeEvent(NativePreviewEvent event) {
        int type = event.getTypeInt();
        if (type == Event.ONKEYDOWN) {
          KeyInfo keyInfo = GWTUtils.createKeyInfo(event.getNativeEvent());
          EventInfo eventInfo = GWTUtils.createEventInfo(event.getNativeEvent());

          try {
            // EventTarget target = event.getNativeEvent().getEventTarget();
            // if (root != null && root.getElement() != null &&
            // root.getElement().isOrHasChild(Element.as(target)))
            if (onAcceleratorKey(keyInfo, eventInfo)) {
              event.cancel();
              event.getNativeEvent().stopPropagation();
              event.getNativeEvent().preventDefault();
            }
          }
          catch (Exception e) {
            GlobalExceptionHandler.get().onUncaughtException(e);
            // event.cancel();
          }
        }
        // event.cancel();
      }

    });
  }

  private boolean onAcceleratorKey(KeyInfo key, EventInfo eventInfo) {
    for (IAcceleratorHandler handler : handlerList)
      if (handler.onKey(key, eventInfo))
        return true;
    return false;
  }

  public void addHandler(IAcceleratorHandler handler) {
    handlerList.add(0, handler);
  }

  public void removeHandler(IAcceleratorHandler handler) {
    handlerList.remove(handler);
  }

  public void removeAllHandler() {
    handlerList.clear();
  }
}
