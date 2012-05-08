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

import com.gwtao.ui.portal.client.portlet.IPortletDescriptor;
import com.gwtao.ui.portal.client.portlet.Portlet;
import com.gwtao.ui.portal.client.portlet.PortletRegistry;


public interface IPortletStackLayout {
  String getId();

  /**
   * @param portletId The id that must be registered by the {@link IPortletDescriptor} of the corresponding
   *          {@link Portlet}s in the {@link PortletRegistry}
   */
  void addPortlet(String portletId);

  /**
   * @param portletId The id that must be registered by the {@link IPortletDescriptor} of the corresponding
   *          {@link Portlet}s in the {@link PortletRegistry}
   */
  void addPortletSpaceHolder(String portletId);
}
