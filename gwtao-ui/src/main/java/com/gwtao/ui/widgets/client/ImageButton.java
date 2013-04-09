package com.gwtao.ui.widgets.client;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CustomButton;
import com.gwtao.ui.util.client.GWTUtil;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class ImageButton extends CustomButton {
  public void setAction(final IAction action) {
    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (action.getPermission() == Permission.ALLOWED)
          ActionUtil.saveExecute(GWTUtil.createEventInfo(event), action);
      }
    });

    action.getWidgetHandler().addAdapter(new ActionFocusWidgetAdapter(action, this, null));
    
//    getUpFace().set
  }

}
