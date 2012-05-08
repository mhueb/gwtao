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
package com.gwtao.ui.portal.client.frame;

import java.util.ArrayList;
import java.util.List;

import com.gwtao.ui.portal.client.layout.PartLayout;
import com.gwtao.ui.portal.client.layout.PortalLayout;
import com.gwtao.ui.portal.client.layout.PortletStackLayout;

public class PortalFrameLayouter {
  private final boolean documents;
  private final List<PortletStackLayout> portletLayoutList = new ArrayList<PortletStackLayout>();
  private final List<PartLayout> partLayoutList = new ArrayList<PartLayout>();

  public PortalFrameLayouter(PortalLayout layout) {
    documents = layout.isShowDocuments();
    partLayoutList.addAll(layout.getPartLayoutList());

    for (PartLayout part : partLayoutList) {
      if (part instanceof PortletStackLayout)
        portletLayoutList.add((PortletStackLayout) part);
    }
  }

  public boolean isShowDocuments() {
    return documents;
  }

  public List<PartLayout> getPartLayoutList() {
    return partLayoutList;
  }

  public List<PortletStackLayout> getPortletLayoutList() {
    return portletLayoutList;
  }
}
