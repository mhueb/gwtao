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

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.portal.client.layout.PortalLayout;
import com.gwtao.ui.portal.client.part.IPortalPart;

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
