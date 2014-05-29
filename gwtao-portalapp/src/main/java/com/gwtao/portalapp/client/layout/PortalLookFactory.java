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
package com.gwtao.portalapp.client.layout;

public abstract class PortalLookFactory implements IPortalLookFactory {
  private String id;
  private String title;
  private String icon;
  private String tooltip;

  protected PortalLookFactory(String id, String title, String icon, String tooltip) {
    this.id = id;
    this.icon = icon;
    this.title = title;
    this.tooltip = tooltip;
  }

  protected PortalLookFactory(String id, String title, String icon) {
    this(id, title, icon, null);
  }

  protected PortalLookFactory(String id, String title) {
    this(id, title, null, null);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getDisplayIcon() {
    return icon;
  }

  @Override
  public String getDisplayText() {
    return title;
  }

  @Override
  public String getDisplayTooltip() {
    return tooltip;
  }

}
