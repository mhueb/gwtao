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

import java.util.ArrayList;
import java.util.List;

import com.gwtao.ui.portal.client.layout.IPortalLayout.Position;

public class PortletStackLayout extends PartLayout implements IPortletStackLayout {

  public static final class PortletInfo {
    private final String id;
    private final boolean spaceHolder;

    public PortletInfo(String id, boolean spaceHolder) {
      this.id = id;
      this.spaceHolder = spaceHolder;
    }

    public String getId() {
      return id;
    }

    public boolean isSpaceHolder() {
      return spaceHolder;
    }
  }

  public final List<PortletInfo> infos = new ArrayList<PortletInfo>();

  public PortletStackLayout(String id, Position pos, float ratio, String referenceId) {
    super(id, pos, ratio, referenceId);
  }

  @Override
  public void addPortlet(String portletId) {
    infos.add(new PortletInfo(portletId, false));
  }

  @Override
  public void addPortletSpaceHolder(String portletId) {
    infos.add(new PortletInfo(portletId, true));
  }

  public List<PortletInfo> getInfos() {
    return infos;
  }
}
