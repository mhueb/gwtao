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
package com.gwtao.ui.util.client.displayable;

public class DisplayableItem implements IDisplayableItem {
  public static final DisplayableItem EMPTY = new DisplayableItem("");

  private String icon;
  private String title;
  private String tooltip;

  /**
   * @param title Title of the action
   */
  public DisplayableItem(String title) {
    this.title = title;
  }

  /**
   * @param title Title of the action
   * @param icon Icon css class
   */
  public DisplayableItem(String title, String icon) {
    this.title = title;
    this.icon = icon;
  }

  /**
   * @param title Title of the action
   * @param icon Icon css class
   * @param tooltip
   */
  public DisplayableItem(String title, String icon, String tooltip) {
    this.title = title;
    this.icon = icon;
    this.tooltip = tooltip;
  }

  public String getDisplayIcon() {
    return icon;
  }

  public String getDisplayText() {
    return title;
  }

  public String getDisplayTooltip() {
    return tooltip;
  }
}
