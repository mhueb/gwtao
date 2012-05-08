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
package com.gwtao.ui.portal.client;

import java.util.List;

import com.google.gwt.activity.shared.ActivityMapper;
import com.gwtao.ui.portal.client.document.IDocument;
import com.gwtao.ui.portal.client.frame.IPortletManager;
import com.gwtao.ui.portal.client.layout.PortalLookRegistry;
import com.gwtao.ui.portal.client.part.IPortalPart;
import com.gwtao.ui.portal.client.portlet.IPortlet;
import com.gwtao.ui.portal.client.portlet.PortletRegistry;
import com.gwtao.ui.portal.client.view.IPortalViewStackFactory;
import com.gwtao.ui.util.client.action.IActionSupplier;

/**
 * A portal is the main application view
 * <p>
 * A portal combines documents and portlets in a customizable look.
 * 
 * @author mah
 * 
 */
public interface IPortal extends ActivityMapper {
  /**
   * @param lookId ID of the look that was registered in the {@link PortalLookRegistry}
   */
  void initLook(String lookId);

  /**
   * @see IPortalListener
   * @param listener
   */
  void addListener(IPortalListener listener);

  void removeListener(IPortalListener listener);

  /**
   * @param lookId ID of the look that was registered in the {@link PortalLookRegistry}
   */
  void applyLook(String lookId);

  IDocument openDocument(Object parameter);

  IDocument openDocument(String documentId, Object parameter);

  IDocument openDocument(Object parameter, boolean separate);

  IDocument openDocument(String documentId, Object parameter, boolean separate);

  IDocument findDocument(Object parameter);

  /**
   * @see {@link #addListener}
   * @return The current loaded document. This can also be <code>null</code>.
   */
  IDocument getActiveDocument();

  /**
   * Opens a portlet and attaches it to the frame
   * 
   * @see {@link IPortletManager}
   * @param portletId The unique id the portlet was registered at the {@link PortletRegistry} with
   * @return {@link IPortlet}
   */
  IPortlet openPortlet(String portletId);

  /**
   * @param portletId The unique id the portlet was registered at the {@link PortletRegistry} with
   */
  IPortlet getPortlet(String portletId);

  IPortlet getActivePortlet();

  List<IPortlet> getVisiblePortlets();

  IActionSupplier getPortletOpenActions();

  IPortalPart getPart(String id);

  void setViewStackFactory(IPortalViewStackFactory factory);
}
