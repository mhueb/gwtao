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
package com.gwtao.ui.util.client.action;

import org.apache.commons.lang.Validate;

import com.gwtao.ui.util.client.displayable.DisplayableItem;
import com.gwtao.ui.util.client.displayable.IDisplayableItem;

public abstract class Action extends AbstractAction {
  private final IDisplayableItem item;

  protected Action() {
    this(DisplayableItem.EMPTY);
  }

  public Action(IDisplayableItem item) {
    Validate.notNull(item);
    this.item = item;
  }

  /**
   * @param title Title of the action
   */
  public Action(String title) {
    this(new DisplayableItem(title));
  }

  /**
   * @param title Title of the action
   * @param icon Icon css class
   */
  public Action(String title, String icon) {
    this(new DisplayableItem(title, icon));
  }

  public Action(String title, String icon, String tooltip) {
    this(new DisplayableItem(title, icon, tooltip));
  }

  public String getDisplayIcon() {
    return item.getDisplayIcon();
  }

  public String getDisplayText() {
    return item.getDisplayIcon();
  }

  public String getDisplayTooltip() {
    return item.getDisplayTooltip();
  }
}
