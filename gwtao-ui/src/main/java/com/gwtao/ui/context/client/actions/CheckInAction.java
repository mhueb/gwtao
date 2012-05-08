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
package com.gwtao.ui.context.client.actions;

import com.gwtao.common.shared.permission.Permission;
import com.gwtao.ui.context.client.editcontext.IEditContext;
import com.gwtao.ui.context.client.editcontext.IEditContextOwner;
import com.gwtao.ui.context.client.i18n.ContextConstants;
import com.gwtao.ui.util.client.action.Action;

public class CheckInAction extends Action {
  private final IEditContextOwner owner;

  public CheckInAction(IEditContextOwner owner) {
    super(ContextConstants.c.save(), GWTAFImageBundle.CHECKIN_ICON);
    this.owner = owner;
  }

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
        return perm.add(Permission.UNALLOWED);
    }
    return perm.add(Permission.INVISIBLE);
  }
}