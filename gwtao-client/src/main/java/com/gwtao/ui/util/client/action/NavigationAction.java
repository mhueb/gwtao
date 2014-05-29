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

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.NavigationItem;

public class NavigationAction extends AbstractAction {

  private NavigationItem item;

  public NavigationAction(NavigationItem item) {
    this.item = item;
  }

  @Override
  public void execute(Object... data) {
    History.newItem(item.getToken());
  }

  @Override
  public String getDisplayText() {
    return item.getDisplayText();
  }

  @Override
  public String getDisplayIcon() {
    return item.getDisplayIcon();
  }

  @Override
  public String getDisplayTooltip() {
    return item.getDisplayTooltip();
  }
}
