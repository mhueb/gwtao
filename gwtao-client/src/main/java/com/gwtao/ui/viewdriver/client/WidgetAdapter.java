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

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class WidgetAdapter<M, V> {

  private Widget widget;
  private Integer dirtyHash;
  private TakesValue<V> tv;
  private IValueAdapter<M, V> va;

  public WidgetAdapter(Widget widget, TakesValue<V> tv, IValueAdapter<M, V> va) {
    this.widget = widget;
    this.tv = tv;
    this.va = va;
  }

  public void clearDirty() {
    V value = tv.getValue();
    dirtyHash = value == null ? null : value.hashCode();
  }

  public boolean isDirty() {
    V value = tv.getValue();
    Integer currentHash = value == null ? null : value.hashCode();
    if (dirtyHash == null)
      return currentHash != null;
    else if (currentHash == null)
      return false;
    else
      return !dirtyHash.equals(currentHash);
  }

  public Widget getWidget() {
    return widget;
  }

  public void setPermission(Permission perm) {
    setReadonly(!perm.isAllowed());
    setVisible(perm.isVisible());
  }

  private void setVisible(boolean visible) {
    widget.setVisible(visible);
  }

  private void setReadonly(boolean readOnly) {
    if (widget instanceof ValueBoxBase) {
      ((ValueBoxBase<?>) widget).setReadOnly(readOnly);
    }
    else {
      widget.getElement().setPropertyBoolean("disabled", readOnly);
    }
  }

  public void updateView(M model) {
    tv.setValue(va.getValue(model));
  }

  public void updateModel(M model) {
    va.setValue(model, tv.getValue());
  }
}
