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
