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

public class PortalLayout implements IPortalLayout {

  private final List<PartLayout> partLayoutList = new ArrayList<PartLayout>();
  private boolean separate = true;
  private boolean documents = true;

  public PortalLayout() {
    partLayoutList.add(new DocumentLayout());
  }

  @Override
  public IPortletStackLayout createPortletStack(String id, Position pos, float ratio, String referenceId) {
    PortletStackLayout layout = new PortletStackLayout(id, pos, ratio, referenceId);
    partLayoutList.add(layout);
    return layout;
  }

  @Override
  public void showDocuments(boolean show) {
    this.documents = show;
  }

  @Override
  public void showDocumentsSeparate(boolean separate) {
    this.separate = separate;
  }

  public boolean isShowDocuments() {
    return documents;
  }

  public boolean isShowDocumentsSeparate() {
    return separate;
  }

  public List<PartLayout> getPartLayoutList() {
    return partLayoutList;
  }

  @Override
  public void addPart(String partId, Position pos, float ratio, String refId) {
    partLayoutList.add(new PartLayout(partId, pos, ratio, refId));
  }
}
