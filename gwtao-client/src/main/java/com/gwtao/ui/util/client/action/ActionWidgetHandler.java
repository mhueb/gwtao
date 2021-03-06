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
package com.gwtao.ui.util.client.action;

import java.util.ArrayList;
import java.util.List;


public class ActionWidgetHandler implements IActionWidgetHandler {
  private final List<IActionWidgetAdapter> adapter = new ArrayList<IActionWidgetAdapter>();

  @Override
  public void addAdapter(IActionWidgetAdapter adapter) {
    this.adapter.add(adapter);
  }

  public void removeAdapter(IActionWidgetAdapter adapter) {
    this.adapter.remove(adapter);
  }

  @Override
  public void update() {
    for (IActionWidgetAdapter adp : adapter)
      adp.update();
  }
}
