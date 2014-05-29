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
package com.gwtao.ui.util.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Button;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class ButtonAdapter {
  private ActionFocusWidgetAdapter adapter;

  public ButtonAdapter(Button button, final IAction action, final IDataSource<?> source) {
    this.adapter = new ActionFocusWidgetAdapter(action, button, source);

    if (button.isAttached())
      action.getWidgetHandler().addAdapter(adapter);

    button.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        ActionUtil.saveExecute(GWTUtils.createEventInfo(event.getNativeEvent()), action, source.getData());
      }
    });

    button.addAttachHandler(new AttachEvent.Handler() {

      @Override
      public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached())
          action.getWidgetHandler().addAdapter(adapter);
        else
          action.getWidgetHandler().removeAdapter(adapter);
      }
    });
  }
}
