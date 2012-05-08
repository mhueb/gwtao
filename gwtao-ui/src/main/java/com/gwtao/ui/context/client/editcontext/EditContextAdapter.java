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
package com.gwtao.ui.context.client.editcontext;

import com.gwtao.common.shared.message.IMessageSource;
import com.gwtao.common.shared.permission.Permission;
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