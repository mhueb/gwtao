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
package com.gwtao.portalapp.client.actionmanager;

import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.card.Card;
import com.gwtao.ui.util.client.card.ICard;

public class ActionManager implements IActionManager {
  private final Card actions = new Card();

  @Override
  public void updateActions() {
  }

  @Override
  public ICard getActions() {
    return actions;
  }

  @Override
  public void addAction(Card actions) {
    actions.add(actions);
  }

  @Override
  public void addAction(IAction action) {
    actions.add(action);
  }
}