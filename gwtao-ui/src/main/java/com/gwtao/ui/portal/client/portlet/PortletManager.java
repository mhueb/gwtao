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
package com.gwtao.ui.portal.client.portlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.gwtao.common.shared.util.SafeIterator;
import com.gwtao.ui.portal.client.IPortalListener;
import com.gwtao.ui.portal.client.frame.IPortletManager;
import com.gwtao.ui.portal.client.frame.PortalFrame;
import com.gwtao.ui.portal.client.layout.PartLayout;
import com.gwtao.ui.portal.client.layout.PortletStackLayout;
import com.gwtao.ui.portal.client.layout.PortletStackLayout.PortletInfo;
import com.gwtao.ui.portal.client.view.IPortalView;
import com.gwtao.ui.portal.client.view.IPortalViewStack;
import com.gwtao.ui.portal.client.view.IPortalViewStackListener;

public final class PortletManager implements IPortletManager {
  private final List<IPortalViewStack<IPortlet>> portletStackList = new ArrayList<IPortalViewStack<IPortlet>>();
  private final List<IPortalListener> listeners;
  private final PortalFrame portalFrame;
  private IPortlet activePortlet;

  private final IPortalViewStackListener portletStackListener = new IPortalViewStackListener() {
    @Override
    public void onViewSwitch(IPortalView view) {
      IPortlet portlet = (IPortlet) view;
      if (!ObjectUtils.equals(portlet, activePortlet)) {
        activePortlet = portlet;
        SafeIterator<IPortalListener> it = new SafeIterator<IPortalListener>(listeners);
        while (it.hasNext())
          it.next().onPortletSwitch(portlet);
      }
    }

    public void onViewClose(IPortalView view) {
      IPortlet portlet = (IPortlet) view;
      if (!ObjectUtils.equals(portlet, activePortlet))
        activePortlet = null;
      SafeIterator<IPortalListener> it = new SafeIterator<IPortalListener>(listeners);
      while (it.hasNext())
        it.next().onPortletClose(portlet);
    }
  };

  public PortletManager(PortalFrame portalFrame, List<IPortalListener> listeners) {
    this.portalFrame = portalFrame;
    this.listeners = listeners;
  }

  @Override
  public IPortlet getActivePortlet() {
    return activePortlet;
  }

  @Override
  public List<IPortlet> getVisiblePortlets() {
    List<IPortlet> portlets = new ArrayList<IPortlet>();
    for (IPortalViewStack<IPortlet> stack : portletStackList) {
      IPortlet activeView = stack.getActiveView();
      if (activeView != null)
        portlets.add(activeView);
    }
    return portlets;
  }

  @Override
  public IPortlet findPortlet(String id) {
    for (IPortalViewStack<IPortlet> stack : portletStackList) {
      for (IPortlet p : stack.getViews())
        if (p.getId().equals(id))
          return p;
    }
    return null;
  }

  private IPortalViewStack<IPortlet> findPortletStack(String id) {
    for (IPortalViewStack<IPortlet> stack : portletStackList) {
      if (stack.getId().equals(id))
        return stack;
    }
    return null;
  }

  @Override
  public void addPortlet(IPortlet portlet) {
    if (portlet == null)
      throw new IllegalArgumentException("portlet==null");

    IPortlet old = findPortlet(portlet.getId());
    if (old != null)
      throw new IllegalStateException("Duplicate portlets not allowed! id=" + portlet.getId());

    for (PartLayout pl : portalFrame.getPartLayoutList()) {
      if (pl instanceof PortletStackLayout) {
        PortletStackLayout ls = (PortletStackLayout) pl;
        for (PortletInfo info : ls.getInfos()) {
          if (info.getId().equals(portlet.getId())) {
            IPortalViewStack<IPortlet> stack = findPortletStack(ls.getId());
            if (stack != null) {
              stack.add(portlet);
              return;
            }
          }
        }
      }
    }
    if (portletStackList.size() >= 1) {
      portletStackList.get(0).add(portlet);
    }
  }

  public void addPortlet(IPortlet portlet, String stackId) {
    if (portlet == null)
      throw new IllegalArgumentException("portlet==null");

    IPortlet old = findPortlet(portlet.getId());
    if (old != null)
      throw new IllegalStateException("Duplicate portlets not allowed! id=" + portlet.getId());

    IPortalViewStack<IPortlet> stack = findPortletStack(stackId);
    if (stack == null)
      throw new IllegalArgumentException("Unknown stack with id=" + stackId);

    stack.add(portlet);
  }

  private IPortalViewStack<IPortlet> findPortletStack(IPortlet portlet) {
    for (IPortalViewStack<IPortlet> stack : portletStackList) {
      if (stack.contains(portlet))
        return stack;
    }
    return null;
  }

  @Override
  public void removePortlet(IPortlet portlet) {
    IPortalViewStack<IPortlet> stack = findPortletStack(portlet);
    if (stack != null)
      stack.remove(portlet);
  }

  @Override
  public List<IPortlet> getAllPortlets() {
    List<IPortlet> portlets = new ArrayList<IPortlet>();
    for (IPortalViewStack<IPortlet> stack : portletStackList)
      portlets.addAll(stack.getViews());
    return portlets;
  }

  @Override
  public void clear() {
    portletStackList.clear(); // TODO ...

  }

  @Override
  public void addStack(IPortalViewStack<IPortlet> stack) {
    portletStackList.add(stack);
    stack.addListener(portletStackListener);
  }
}