/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.ui.portal.client.layout;

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
  public String getIcon() {
    return icon;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getTooltip() {
    return tooltip;
  }

}
