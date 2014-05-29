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
package com.gwtao.ui.widgets.client;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CustomButton;
import com.gwtao.ui.util.client.GWTUtils;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class ImageButton extends CustomButton {
  public void setAction(final IAction action) {
    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (action.getPermission() == Permission.ALLOWED)
          ActionUtil.saveExecute(GWTUtils.createEventInfo(event.getNativeEvent()), action);
      }
    });

    action.getWidgetHandler().addAdapter(new ActionFocusWidgetAdapter(action, this, null));
    
//    getUpFace().set
  }

}
