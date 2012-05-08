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

import com.gwtao.ui.context.client.datacontext.IDataContext;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionSupplier;

/**
 * A selection context reflects the selection of some data.
 * <p>
 * Grids widgets f.e. usses this context to signal selection changes.
 * 
 * @author mah
 * 
 */
public interface ISelectionContext extends IDataContext<Object[]> {

  /**
   * Set an action to handle double click.
   * 
   * @param action
   */
  void setDoubleClickAction(IAction action);

  /**
   * Set an action to handle hyper link.
   * 
   * @param action
   */
  void setOpenAction(IAction action);

  /**
   * @param builder
   */
  void setContextMenuBuilder(IActionSupplier builder);
}
