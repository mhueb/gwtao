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

import java.util.ArrayList;
import java.util.List;

import com.gwtao.portalapp.client.layout.IPortalLayout.Position;

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
