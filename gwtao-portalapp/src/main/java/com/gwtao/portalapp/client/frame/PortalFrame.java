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
package com.gwtao.portalapp.client.frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.IPortalListener;
import com.gwtao.portalapp.client.document.DocumentManager;
import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.layout.DocumentLayout;
import com.gwtao.portalapp.client.layout.IPortalLayout;
import com.gwtao.portalapp.client.layout.PartLayout;
import com.gwtao.portalapp.client.layout.PortalLayout;
import com.gwtao.portalapp.client.layout.PortletStackLayout;
import com.gwtao.portalapp.client.layout.PortletStackLayout.PortletInfo;
import com.gwtao.portalapp.client.part.IPortalPart;
import com.gwtao.portalapp.client.part.IPortalPartContext;
import com.gwtao.portalapp.client.part.IPortalPartDescriptor;
import com.gwtao.portalapp.client.part.PortalPartRegistry;
import com.gwtao.portalapp.client.portlet.IPortlet;
import com.gwtao.portalapp.client.portlet.PortletManager;
import com.gwtao.portalapp.client.portlet.PortletRegistry;
import com.gwtao.portalapp.client.view.IPortalViewStack;
import com.gwtao.portalapp.client.view.IPortalViewStackFactory;
import com.gwtao.ui.layout.client.FlowLayout;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.LayoutPanel;
import com.gwtao.ui.layout.client.SplitFlowLayout;
import com.gwtao.ui.widgets.client.SwitchPanel;

public class PortalFrame implements IPortalFrame {
  private final IDocumentManager documentManager;
  private final IPortletManager portletManager;
  private final SwitchPanel root = new SwitchPanel();
  private final Map<String, IPortalPart> partMap = new HashMap<String, IPortalPart>();

  private PortalFrameLayouter layout;
  private IPortalPart lastPart;

  private IPortalPart maximizedPart;
  private LayoutPanel originalLayout;
  private int originalPos;
  private final IPortalViewStackFactory viewStackFactory;

  private final class PPC implements IPortalPartContext {
    private final IPortalPart part;
    private State state = State.NORMAL;

    public PPC(IPortalPart part) {
      this.part = part;
    }

    @Override
    public void close() {
      closePart(part);
    }

    @Override
    public State getViewState() {
      return state;
    }

    @Override
    public void setViewState(State state) {
      if (!this.state.equals(state)) {
        switch (state) {
        case MAXIMIZED:
          if (maximizedPart != null)
            return;
          if (this.state == State.NORMAL) {
            maximizedPart = part;
            originalLayout = (LayoutPanel) part.getWidget().getParent();
            originalPos = originalLayout.getWidgetIndex(part.getWidget());
            root.add(part.getWidget());
            root.showWidget(1);
            this.state = State.MAXIMIZED;
            part.onChangeViewState();
          }
          else if (this.state == State.MINIMIZED)
            ;
          break;
        case MINIMIZED:
          if (maximizedPart != null)
            return;
          if (this.state == State.NORMAL) {
          }
          else if (this.state == State.MAXIMIZED)
            ;
          break;
        case NORMAL:
          if (this.state == State.MAXIMIZED) {
            originalLayout.insert(part.getWidget(), originalPos);
            originalLayout = null;
            maximizedPart = null;
            root.showWidget(0);
            this.state = State.NORMAL;
            part.onChangeViewState();
          }
          else if (this.state == State.MINIMIZED)
            ;
          break;
        }
      }

    }

  }

  public PortalFrame(IPortalViewStackFactory stackFactory, List<IPortalListener> listeners) {
    this.viewStackFactory = stackFactory;
    this.documentManager = new DocumentManager(listeners);
    this.portletManager = new PortletManager(this, listeners);

    root.sinkEvents(Event.FOCUSEVENTS);

    Event.addNativePreviewHandler(new NativePreviewHandler() {
      boolean goLock = true;

      @Override
      public void onPreviewNativeEvent(NativePreviewEvent event) {
        if (goLock && (event.getTypeInt() & Event.BUTTON_LEFT | event.getTypeInt() & Event.ONFOCUS) != 0) {
          try {
            goLock = false;
            EventTarget target = event.getNativeEvent().getEventTarget();
            if (!Element.is(target))
              return;
            Element e = Element.as(target);
            for (IPortalPart part : partMap.values()) {
              if (part.getWidget().getElement().isOrHasChild(e)) {
                if (!ObjectUtils.equals(part, lastPart)) {
                  lastPart = part;
                  if (part != null)
                    part.setActive();
                }
                break;
              }
            }
          }
          catch (Exception e) {
            GWT.log(e.getMessage(), e);
          }
          finally {
            goLock = true;
          }
        }
      }
    });
  }

  @Override
  public Widget getWidget() {
    return root;
  }

  @Override
  public void applyLayout(PortalLayout layout) {
    if (layout == null)
      layout = createDefaultLayout();

    this.layout = new PortalFrameLayouter(layout);

    root.clear();
    portletManager.clear();
    documentManager.clear();

    createParts();

    for (PortletStackLayout sl : this.layout.getPortletLayoutList()) {
      for (PortletInfo pi : sl.getInfos()) {
        if (!pi.isSpaceHolder()) {
          IPortlet portlet = PortletRegistry.get().create(pi.getId());
          portletManager.addPortlet(portlet, sl.getId());
        }
      }
    }
  }

  private void createParts() {
    LayoutPanel portalPanel = new LayoutPanel();
    portalPanel.setLayout(new SplitFlowLayout(true));

    for (PartLayout pl : layout.getPartLayoutList()) {
      IPortalPart pp = createPart(pl);

      IPortalPartDescriptor descriptor = PortalPartRegistry.get().lookup(pl.getId());
      int minWidth = descriptor == null ? 64 : descriptor.getMinWidth();
      int minHeight = descriptor == null ? 64 : descriptor.getMinHeight();
      pp.getWidget().setLayoutData(new FlowLayoutData(minWidth, minHeight, pl.getRatio()));

      IPortalPart refPart = partMap.get(pl.getRefId());
      LayoutPanel target = getPanel(portalPanel, pl.getRefId());
      int refIdx = refPart == null ? 0 : target.getWidgetIndex(refPart.getWidget());

      if (pl.isHorizontal() && !((FlowLayout) target.getLayout()).isHorizontal()) {
        if (target.getWidgetCount() > 1) {
          LayoutPanel sub = new LayoutPanel();
          sub.setLayout(new SplitFlowLayout(true));
          if (refPart.getWidget().getLayoutData() != null) {
            FlowLayoutData layoutData = (FlowLayoutData) refPart.getWidget().getLayoutData();
            FlowLayoutData copy = new FlowLayoutData(layoutData.getMinWidth(), layoutData.getMinHeight(), 1.0f);
            sub.setLayoutData(layoutData);
            refPart.getWidget().setLayoutData(copy);
          }
          sub.add(refPart.getWidget());
          target.insert(sub, refIdx);
          target = sub;
        }
        else
          target.setLayout(new SplitFlowLayout(true));
      }
      else if (pl.isVertical() && ((FlowLayout) target.getLayout()).isHorizontal()) {
        if (target.getWidgetCount() > 1) {
          LayoutPanel sub = new LayoutPanel();
          sub.setLayout(new SplitFlowLayout(false));
          if (refPart.getWidget().getLayoutData() != null) {
            FlowLayoutData layoutData = (FlowLayoutData) refPart.getWidget().getLayoutData();
            FlowLayoutData copy = new FlowLayoutData(layoutData.getMinWidth(), layoutData.getMinHeight(), 1.0f);
            sub.setLayoutData(layoutData);
            refPart.getWidget().setLayoutData(copy);
          }
          target.insert(sub, refIdx);
          sub.add(refPart.getWidget());
          target = sub;
        }
        else
          target.setLayout(new SplitFlowLayout(false));
      }

      if (target.getWidgetCount() > 0) {
        switch (pl.getPos()) {
        case TOP:
        case LEFT:
          target.insert(pp.getWidget(), refIdx);
          break;
        case BOTTOM:
        case RIGHT:
          target.insert(pp.getWidget(), refIdx + 1);
          break;
        }
      }
      else
        target.add(pp.getWidget());
    }

    this.root.add(portalPanel);
    this.root.showWidget(0);
  }

  private LayoutPanel getPanel(LayoutPanel root, String refId) {
    if (IPortalLayout.PORTAL.equals(refId))
      return root;
    IPortalPart refPart = partMap.get(refId);
    return (LayoutPanel) refPart.getWidget().getParent();
  }

  private IPortalPart createPart(PartLayout pl) {
    try {
      IPortalPart part;
      if (pl instanceof DocumentLayout) {
        IPortalViewStack<IDocument> stack = viewStackFactory.create(pl.getId());
        documentManager.addStack(stack);
        part = stack;
      }
      else if (pl instanceof PortletStackLayout) {
        IPortalViewStack<IPortlet> stack = viewStackFactory.create(pl.getId());
        portletManager.addStack(stack);
        part = stack;
      }
      else
        part = PortalPartRegistry.get().create(pl.getId());

      part.init(new PPC(part));
      partMap.put(part.getId(), part);
      return part;
    }
    catch (Exception e) {
      GWT.log("Failed to create portal part!", e);
      BrokenPortalPart part = new BrokenPortalPart(pl.getId(), e);
      part.init(new PPC(part));
      partMap.put(pl.getId(), part);
      return part;
    }
  }

  public void closePart(IPortalPart part) {
    part.getWidget().removeFromParent();
  }

  private PortalLayout createDefaultLayout() {
    PortalLayout defaultLayout = new PortalLayout();
    defaultLayout.showDocuments(true);
    return defaultLayout;
  }

  @Override
  public IDocumentManager getDocumentManager() {
    return documentManager;
  }

  @Override
  public IPortletManager getPortletManager() {
    return portletManager;
  }

  public IPortalPart getPart(String id) {
    return partMap.get(id);
  }

  public List<PartLayout> getPartLayoutList() {
    return layout.getPartLayoutList();
  }
}
