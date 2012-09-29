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
package com.gwtao.ui.portal.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.gwtao.ui.layout.client.RootLayoutPanel;
import com.gwtao.ui.location.client.IPresenterManager;
import com.gwtao.ui.location.client.LocationManager;
import com.gwtao.ui.location.client.Token;
import com.gwtao.ui.portal.client.document.DocumentRegistry;
import com.gwtao.ui.portal.client.document.IDocument;
import com.gwtao.ui.portal.client.document.IDocumentDescriptor;
import com.gwtao.ui.portal.client.document.InvalidDocumentView;
import com.gwtao.ui.portal.client.frame.IPortalFrame;
import com.gwtao.ui.portal.client.frame.NotstartedFrame;
import com.gwtao.ui.portal.client.frame.PortalFrame;
import com.gwtao.ui.portal.client.layout.IPortalLookFactory;
import com.gwtao.ui.portal.client.layout.PortalLayout;
import com.gwtao.ui.portal.client.layout.PortalLookRegistry;
import com.gwtao.ui.portal.client.part.IPortalPart;
import com.gwtao.ui.portal.client.portlet.IPortlet;
import com.gwtao.ui.portal.client.portlet.IPortletDescriptor;
import com.gwtao.ui.portal.client.portlet.PortletRegistry;
import com.gwtao.ui.portal.client.util.PortletOpenAction;
import com.gwtao.ui.portal.client.view.IPortalViewStackFactory;
import com.gwtao.ui.portal.client.view.PortalViewStack;
import com.gwtao.ui.util.client.SplashManager;
import com.gwtao.ui.util.client.action.IActionInfo;
import com.gwtao.ui.util.client.action.IActionSupplier;

public class Portal implements IPortal {

  private static final IPortal INSTANCE = new Portal();

  public static IPortal get() {
    return INSTANCE;
  }

  private final LocationManager<IDocument> manager;

  private IPortalViewStackFactory stackFactory = PortalViewStack.FACTORY;

  private IPortalFrame frame = new NotstartedFrame();
  private final List<IPortalListener> listeners = new ArrayList<IPortalListener>();
  private String lookId;

  private IActionSupplier portletOpenActions = new IActionSupplier() {

    @Override
    public String getTooltip() {
      return null;
    }

    @Override
    public String getTitle() {
      return "Portlets";
    }

    @Override
    public String getIcon() {
      return null;
    }

    @Override
    public List<IActionInfo> getActions(Object... selection) {
      List<IActionInfo> actions = new ArrayList<IActionInfo>();
      for (IPortletDescriptor pd : PortletRegistry.get().getDescriptors()) {
        actions.add(new PortletOpenAction(pd));
      }
      return actions;
    }
  };

  private boolean showDocumentsSeparate = true;

  private Portal() {
    manager = new LocationManager<IDocument>(new IPresenterManager<IDocument>() {

      @Override
      public boolean deactivate(IDocument location) {
        return true;
      }

      @Override
      public IDocument createLocation(Token token) {
        String id = token.getId();
        if (StringUtils.isEmpty(id))
          return null;

        IDocumentDescriptor descr = DocumentRegistry.get().lookup(id);
        if (descr == null)
          throw new java.lang.IllegalArgumentException("Document with id=" + token.getId() + " does not exists!");
        Object parameter = descr.decodeParameter(token.getParameters());
        return openDocument(token.getId(), parameter);
      }

      @Override
      public IDocument createErrorLocation(Token token, String errorMessage) {
        // TODO das sollte eigentlich unnötig sein....
        if (frame instanceof NotstartedFrame)
          throw new IllegalStateException();

        InvalidDocumentView invalidDocumentRequest = new InvalidDocumentView(token, errorMessage);
        frame.getDocumentManager().addDocument(invalidDocumentRequest, true);
        return invalidDocumentRequest;
      }

      @Override
      public String canClose(IDocument location) {
        return frame.getDocumentManager().canClose();
      }

      @Override
      public void activate(IDocument location) {
        location.getViewContext().activate();
      }
    });
  }

  public void initLook(String lookId) {
    if (!(frame instanceof NotstartedFrame))
      throw new IllegalStateException("Portal already started!");
    this.lookId = lookId;
  }

  public void startup() {
    if (!(frame instanceof NotstartedFrame))
      throw new IllegalStateException("Portal already started!");

    SplashManager.removeSplash();

    listeners.add(new IPortalListener() {
      @Override
      public void onPortletSwitch(IPortlet portlet) {
      }

      @Override
      public void onPortletClose(IPortlet portlet) {
      }

      @Override
      public void onDocumentSwitch(IDocument doc) {
        manager.notifyActivation(doc);
      }

      @Override
      public void onDocumentClose(IDocument doc) {
        manager.notifyRemove(doc);
      }
    });

    frame = new PortalFrame(stackFactory, listeners);
    RootLayoutPanel.get().add(frame.getWidget());
    applyLook(lookId);

    manager.startup();
  }

  public void reset() {
    RootLayoutPanel.get().clear();
    frame = new NotstartedFrame();
    // lookId = null; TODO hie initiale wird nur bei startup übergeben. die sollte ggf. hier wieder verwen det
    // werdeen, da die aktuelle was anderes sein kann...
    listeners.clear();
  }

  @Override
  public IDocument openDocument(Object parameter) {
    return openDocument(null, parameter, false);
  }

  @Override
  public IDocument openDocument(Object parameter, boolean separate) {
    return openDocument(null, parameter, separate);
  }

  @Override
  public IDocument openDocument(String documentId, Object parameter) {
    return openDocument(documentId, parameter, false);
  }

  @Override
  public IDocument openDocument(String documentId, Object parameter, boolean separate) {
    IDocument doc = lookupDocument(documentId, parameter);
    if (doc == null) {
      doc = createDocument(documentId, parameter);
      frame.getDocumentManager().addDocument(doc, separate | showDocumentsSeparate);
    }
    doc.getViewContext().activate();

    return doc;
  }

  private IDocument createDocument(String documentId, Object parameter) {
    if (documentId != null)
      return DocumentRegistry.get().create(documentId, parameter);
    return DocumentRegistry.get().create(parameter);
  }

  public IDocument findDocument(Object parameter) {
    return lookupDocument(null, parameter);
  }

  private IDocument lookupDocument(String id, Object parameter) {
    if (id == null && parameter == null)
      return null;

    for (IDocument doc : frame.getDocumentManager().getAllDocuments()) {
      if (id != null) {
        if (id.equals(doc.getDescriptor().getId()) && ObjectUtils.equals(parameter, doc.getParameter()))
          return doc;
      }
      else if (ObjectUtils.equals(parameter, doc.getParameter()))
        return doc;
    }
    return null;
  }

  @Override
  public IDocument getActiveDocument() {
    return frame.getDocumentManager().getActiveDocument();
  }

  @Override
  public void applyLook(String lookId) {
    IPortalLookFactory look = PortalLookRegistry.get().getLayoutFactory(lookId);
    PortalLayout layout = new PortalLayout();
    look.createLook(layout);
    frame.applyLayout(layout);
    this.showDocumentsSeparate = layout.isShowDocumentsSeparate();
  }

  @Override
  public void addListener(IPortalListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(IPortalListener listener) {
    listeners.remove(listener);
  }

  @Override
  public IPortlet openPortlet(String portletId) {
    IPortlet portlet = frame.getPortletManager().findPortlet(portletId);
    if (portlet == null) {
      portlet = PortletRegistry.get().create(portletId);
      frame.getPortletManager().addPortlet(portlet);
      portlet.getViewContext().activate();
    }
    else if (!portlet.getViewContext().isVisible())
      portlet.getViewContext().activate();

    return portlet;
  }

  @Override
  public IPortlet getPortlet(String portletId) {
    return frame.getPortletManager().findPortlet(portletId);
  }

  @Override
  public IPortlet getActivePortlet() {
    return frame.getPortletManager().getActivePortlet();
  }

  @Override
  public List<IPortlet> getVisiblePortlets() {
    return frame.getPortletManager().getVisiblePortlets();
  }

  @Override
  public IActionSupplier getPortletOpenActions() {
    return portletOpenActions;
  }

  @Override
  public IPortalPart getPart(String id) {
    return frame.getPart(id);
  }

  @Override
  public void setViewStackFactory(IPortalViewStackFactory factory) {
    stackFactory = factory;
  }

  @Override
  public Object mapObject2Parameter(Object obj) {
    throw new IllegalStateException("Not Implemented Yet");
  }
}
