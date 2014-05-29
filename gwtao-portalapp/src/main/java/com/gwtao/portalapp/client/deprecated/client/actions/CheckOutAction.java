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

public final class CheckOutAction extends Action {
  private final IEditContextOwner owner;

  public CheckOutAction(IEditContextOwner owner) {
    super(ContextConstants.c.edit(), ContextImageBundle.EDIT_ICON);
    this.owner = owner;
  }

  public void execute(Object... data) {
    owner.getEditContext().checkOut();
  }

  @Override
  public Permission getPermission(Object... data) {
    return owner.getEditContext().supportsEditState() && owner.getEditContext().getState() == IEditContext.State.VIEW ? super.getPermission(data) : Permission.HIDDEN;
  }
}