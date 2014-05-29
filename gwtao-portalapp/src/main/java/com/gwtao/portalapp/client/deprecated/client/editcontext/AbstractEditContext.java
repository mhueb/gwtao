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
package com.gwtao.portalapp.client.deprecated.client.editcontext;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.data.client.source.SimpleDataSource;

public abstract class AbstractEditContext<T> extends SimpleDataSource<T> implements IEditContext<T> {
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
    fireDataChanged();
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
