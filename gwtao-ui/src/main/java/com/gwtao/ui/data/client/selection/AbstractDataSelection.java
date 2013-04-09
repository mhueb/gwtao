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

import com.gwtao.ui.data.client.source.AbstractDataSource;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.card.ICardSupplier;

public abstract class AbstractDataSelection extends AbstractDataSource<Object[]> implements IDataSelection {
  private IAction doubleClickAction;
  private IAction openAction;
  private ICardSupplier contextActions;

  @Override
  public void setContextMenuBuilder(ICardSupplier actions) {
    contextActions = actions;
  }

  public ICardSupplier getContextActions() {
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
