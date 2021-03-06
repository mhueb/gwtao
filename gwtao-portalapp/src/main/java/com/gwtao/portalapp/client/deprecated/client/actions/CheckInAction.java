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
package com.gwtao.portalapp.client.deprecated.client.actions;

import org.shu4j.utils.permission.Permission;

import com.gwtao.portalapp.client.deprecated.client.ContextImageBundle;
import com.gwtao.portalapp.client.deprecated.client.editcontext.IEditContext;
import com.gwtao.portalapp.client.deprecated.client.editcontext.IEditContextOwner;
import com.gwtao.portalapp.client.deprecated.client.i18n.ContextConstants;
import com.gwtao.ui.util.client.action.Action;

public class CheckInAction extends Action {
  private final IEditContextOwner owner;

  public CheckInAction(IEditContextOwner owner) {
    super(ContextConstants.c.save(), ContextImageBundle.CHECKIN_ICON);
    this.owner = owner;
  }

  @Override
  public void execute(Object... data) {
    owner.getEditContext().checkIn();
  }

  @Override
  public Permission getPermission(Object... data) {
    Permission perm = super.getPermission(data);
    IEditContext<?> editContext = owner.getEditContext();
    if (editContext.supportsEditState() && owner.getEditContext().getState() == IEditContext.State.EDIT) {
      if (!editContext.wantValidToCheckIn() || editContext.isValid())
        return perm;
      else
        return perm.add(Permission.READONLY);
    }
    return perm.add(Permission.HIDDEN);
  }
}