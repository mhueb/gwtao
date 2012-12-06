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
package com.gwtao.portalapp.client;

import java.util.List;

import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.frame.IPortletManager;
import com.gwtao.portalapp.client.layout.PortalLookRegistry;
import com.gwtao.portalapp.client.part.IPortalPart;
import com.gwtao.portalapp.client.portlet.IPortlet;
import com.gwtao.portalapp.client.portlet.PortletRegistry;
import com.gwtao.portalapp.client.view.IPortalViewStackFactory;
import com.gwtao.ui.util.client.action.IActionSupplier;

/**
 * A portal is the main application view
 * <p>
 * A portal combines documents and portlets in a customizable look.
 * 
 * @author mah
 * 
 */
public interface IPortal {
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
  
  Object mapObject2Parameter(Object obj);

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

  void startup();

  void reset();
}
