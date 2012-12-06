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
package com.gwtao.ui.context.client.editcontext;

import org.shu4j.utils.message.IMessageSource;
import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.context.client.datacontext.IDataChangeListener;

public abstract class EditContextAdapter<T> implements IEditContext<T> {
  private final IEditContext<?> inner;

  public EditContextAdapter(IEditContext<?> inner) {
    this.inner = inner;
  }

  @Override
  public void checkIn() {
    inner.checkIn();
  }

  @Override
  public void checkOut() {
    inner.checkOut();
  }

  @Override
  public IMessageSource getServerMessages() {
    return inner.getServerMessages();
  }

  @Override
  public IEditContext.State getState() {
    return inner.getState();
  }

  @Override
  public boolean isDirty() {
    return inner.isDirty();
  }

  @Override
  public boolean isEditable() {
    return inner.isEditable();
  }

  @Override
  public boolean isNew() {
    return inner.isNew();
  }

  @Override
  public boolean isValid() {
    return inner.isValid();
  }

  @Override
  public void refresh() {
    inner.refresh();
  }

  @Override
  public void revert() {
    inner.revert();
  }

  @Override
  public void setDirty() {
    inner.setDirty();
  }

  @Override
  public void start(Object parameter) {
    inner.start(parameter);
  }

  @Override
  public boolean supportsEditState() {
    return inner.supportsEditState();
  }

  @Override
  public IMessageSource validate(boolean checkin) {
    return inner.validate(checkin);
  }

  @Override
  public boolean wantCheckInBeforeLeave() {
    return inner.wantCheckInBeforeLeave();
  }

  @Override
  public boolean wantExplicitCheckOut() {
    return inner.wantExplicitCheckOut();
  }

  @Override
  public boolean wantValidateOnChange() {
    return inner.wantValidateOnChange();
  }

  @Override
  public void addChangeListener(IDataChangeListener listener) {
    inner.addChangeListener(listener);
  }

  @Override
  public Permission getPermission() {
    return inner.getPermission();
  }

  @Override
  public boolean isDataNull() {
    return getData() == null;
  }

  @Override
  public void notifyChange() {
    inner.notifyChange();
  }

  @Override
  public void removeChangeListener(IDataChangeListener listener) {
    inner.removeChangeListener(listener);
  }

  @Override
  public void addStateListener(IEditContextListener listener) {
    inner.addStateListener(listener);
  }

  @Override
  public void addStateProvider(IEditStateProvider provider) {
    inner.addStateProvider(provider);
  }

  @Override
  public void removeStateListener(IEditContextListener listener) {
    inner.removeStateListener(listener);
  }

  @Override
  public void removeStateProvider(IEditStateProvider provider) {
    inner.removeStateProvider(provider);
  }

  @Override
  public void updateState() {
    inner.updateState();
  }

  @Override
  public boolean wantValidToCheckIn() {
    return inner.wantValidToCheckIn();
  }
}