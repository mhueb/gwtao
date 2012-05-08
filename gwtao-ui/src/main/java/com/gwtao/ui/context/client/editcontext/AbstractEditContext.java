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

import java.util.ArrayList;
import java.util.List;

import com.gwtao.common.shared.permission.Permission;
import com.gwtao.ui.context.client.datacontext.ConcreteDataContext;

public abstract class AbstractEditContext<T> extends ConcreteDataContext<T> implements IEditContext<T> {
  private EditContextListenerCaller listeners = new EditContextListenerCaller();
  private List<IEditStateProvider> providers;

  private boolean dirtyFlag = false;
  private boolean validFlag = true;
  private boolean updateFlag = true;
  private State state = State.INIT;
  private boolean dirtyFlagForce;

  public AbstractEditContext() {
  }

  public void addStateListener(IEditContextListener listener) {
    listeners.add(listener);
  }

  public void removeStateListener(IEditContextListener listener) {
    listeners.remove(listener);
  }

  private void notify(boolean dirty, boolean valid) {
    if (isDirty() != dirty || isValid() != valid) {
      dirtyFlag = dirty;
      validFlag = valid;
      getStateListener().callDataStateChange();
    }
  }

  @Override
  public void onSetData() {
    super.onSetData();
    getStateListener().callSetData();
  }

  public void addStateProvider(IEditStateProvider beacon) {
    if (providers == null)
      providers = new ArrayList<IEditStateProvider>();
    providers.remove(beacon);
    providers.add(beacon);
  }

  public void removeStateProvider(IEditStateProvider beacon) {
    if (providers != null)
      providers.remove(beacon);
  }

  public void updateState() {
    if (providers == null)
      return;

    if (updateFlag) {
      updateFlag = false;

      try {
        boolean dirtyFlag = false;
        boolean validFlag = true;
        for (IEditStateProvider beacon : providers) {
          dirtyFlag |= beacon.isDirty();
          if (isEditable())
            validFlag &= beacon.isValid();
        }
        notify(dirtyFlag, validFlag);
      }
      finally {
        updateFlag = true;
      }
    }
  }

  @Override
  public Permission getPermission() {
    Permission perm = super.getPermission();
    if (perm.isAllowed() && getState() == State.EDIT)
      return Permission.ALLOWED;
    return Permission.READONLY;
  }

  public boolean isDirty() {
    return this.dirtyFlagForce || dirtyFlag;
  }

  public void setDirty() {
    this.dirtyFlagForce = true;
    notifyChange();
  }

  public boolean isValid() {
    return validFlag;
  }

  public boolean supportsEditState() {
    return true;
  }

  public boolean wantCheckInBeforeLeave() {
    return true;
  }

  public boolean wantExplicitCheckOut() {
    return true;
  }

  public boolean wantValidateOnChange() {
    return true;
  }

  @Override
  public boolean wantValidToCheckIn() {
    return false;
  }

  public State getState() {
    return state;
  }

  public boolean isEditable() {
    return getState() == State.EDIT;
  }

  protected void setState(State state) {
    setState(state, true);
  }

  protected void setState(State state, boolean notify) {
    if (this.state != state) {
      resetFlags();
      this.state = state;
      if (notify)
        getStateListener().callStateChange();
    }
  }

  /**
   * TODO workaround - refactoring needed
   */
  @Deprecated
  protected void resetFlags() {
    this.dirtyFlagForce = false;
    dirtyFlag = false;
    validFlag = true;
  }

  protected EditContextListenerCaller getStateListener() {
    return listeners;
  }
}
