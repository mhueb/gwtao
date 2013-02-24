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
package com.gwtao.ui.data.client.selection;

import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.card.ICardSupplier;

/**
 * A selection context reflects the selection of some data.
 * <p>
 * Grids widgets f.e. uses this context to signal selection changes.
 * 
 * @author mah
 * 
 */
public interface IModelSelection extends IDataSource<Object[]> {

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
  void setContextMenuBuilder(ICardSupplier builder);
}
