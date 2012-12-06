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
package com.gwtao.portalapp.client.frame;

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.layout.PortalLayout;
import com.gwtao.portalapp.client.part.IPortalPart;

public class NotstartedFrame implements IPortalFrame {

  private static final String INVALID_CALL_PORTAL_IS_NOT_STARTED = "Invalid call - Portal is not started";

  @Override
  public Widget getWidget() {
    throw new IllegalStateException(INVALID_CALL_PORTAL_IS_NOT_STARTED);
  }

  @Override
  public void applyLayout(PortalLayout layout) {
    throw new IllegalStateException(INVALID_CALL_PORTAL_IS_NOT_STARTED);
  }

  @Override
  public IDocumentManager getDocumentManager() {
    throw new IllegalStateException(INVALID_CALL_PORTAL_IS_NOT_STARTED);
  }

  @Override
  public IPortletManager getPortletManager() {
    throw new IllegalStateException(INVALID_CALL_PORTAL_IS_NOT_STARTED);
  }

  @Override
  public IPortalPart getPart(String id) {
    throw new IllegalStateException(INVALID_CALL_PORTAL_IS_NOT_STARTED);
  }
}
