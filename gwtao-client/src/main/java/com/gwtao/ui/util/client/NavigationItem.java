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
package com.gwtao.ui.util.client;

import com.gwtao.ui.util.client.displayable.IDisplayableItem;

public class NavigationItem implements IDisplayableItem {
  private String token;
  private String title;
  private String icon;
  private String tooltip;

  public NavigationItem(String token, String title) {
    this.token = token;
    this.title = title;
  }

  public NavigationItem(String token, String title, String icon) {
    this(token, title);
    this.icon = icon;
  }

  public NavigationItem(String token, String title, String icon, String tooltip) {
    this(token, title, icon);
    this.tooltip = tooltip;
  }

  public String getToken() {
    return token;
  }

  @Override
  public String getDisplayTooltip() {
    return tooltip;
  }

  @Override
  public String getDisplayIcon() {
    return icon;
  }

  @Override
  public String getDisplayText() {
    return title;
  }

}
