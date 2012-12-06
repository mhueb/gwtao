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
package com.gwtao.portalapp.client.actionmanager;

import java.util.List;

import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IActionInfo;

public interface IActionManager {

  /**
   * Adds a new action to the action panel. If this is done after your component was initialized you have to
   * call {@link #updateActions}.
   * 
   * @param action Action
   */
  void addAction(IActionInfo action);

  /**
   * Call this if you add new {@link IAction}s (via {@link #addAction}) after your component was initialized
   * (e.g. context sensitive actions)
   */
  void updateActions();

  List<IActionInfo> getActions();
}
