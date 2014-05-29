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
package com.gwtao.ui.viewdriver.client;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.Widget;

public class ViewDriver<M> implements IViewDriver<M> {

  private final List<WidgetAdapter<M, ?>> widgets = new ArrayList<WidgetAdapter<M, ?>>();

  public ViewDriver() {
  }

  public <T extends Widget> void add(WidgetAdapter<M, ?> adp) {
    widgets.add(adp);
  }

  @Override
  public void updateView(M model, Permission perm) {
    for (WidgetAdapter<M, ?> widget : widgets) {
      if (model != null)
        widget.updateView(model);
      widget.setPermission(perm);
    }
  }

  @Override
  public boolean isDirty() {
    for (WidgetAdapter<M, ?> widget : widgets) {
      if (widget.isDirty())
        return true;
    }
    return false;
  }

  @Override
  public void clearDirty() {
    for (WidgetAdapter<M, ?> widget : widgets) {
      widget.clearDirty();
    }
  }

  @Override
  public void updateModel(M data) {
    for (WidgetAdapter<M, ?> widget : widgets) {
      widget.updateModel(data);
    }
  }

}
