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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gwtao.ui.util.client.action.IActionInfo;

public class ActionManager implements IActionManager {
  private final List<IActionInfo> actions = new ArrayList<IActionInfo>();

  @Override
  public void updateActions() {
  }

  @Override
  public List<IActionInfo> getActions() {
    return Collections.unmodifiableList(actions);
  }

  @Override
  public void addAction(IActionInfo action) {
    actions.add(action);
  }
}