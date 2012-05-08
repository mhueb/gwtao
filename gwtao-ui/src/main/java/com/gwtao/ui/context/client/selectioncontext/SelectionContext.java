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
package com.gwtao.ui.context.client.selectioncontext;

import com.gwtao.ui.context.client.datacontext.DataContext;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionSupplier;

public abstract class SelectionContext extends DataContext<Object[]> implements ISelectionContext {
  private IAction doubleClickAction;
  private IAction openAction;
  private IActionSupplier contextActions;

  @Override
  public void setContextMenuBuilder(IActionSupplier actions) {
    contextActions = actions;
  }

  public IActionSupplier getContextActions() {
    return contextActions;
  }

  public void setDoubleClickAction(IAction action) {
    this.doubleClickAction = action;
  }

  @Override
  public void setOpenAction(IAction action) {
    openAction = action;
  }
  
  public IAction getDoubleClickAction() {
    return doubleClickAction;
  }

  public IAction getOpenAction() {
    return openAction;
  }
}
