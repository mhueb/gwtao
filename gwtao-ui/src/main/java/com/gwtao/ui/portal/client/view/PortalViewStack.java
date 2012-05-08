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
package com.gwtao.ui.portal.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.common.shared.util.SafeIterator;
import com.gwtao.ui.layout.client.FlowLayout;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.LayoutPanel;
import com.gwtao.ui.portal.client.part.AbstractPortalPart;
import com.gwtao.ui.util.client.SwitchPanel;

public class PortalViewStack<T extends IPortalView> extends AbstractPortalPart implements IPortalViewStack<T> {
  public static final IPortalViewStackFactory FACTORY = new IPortalViewStackFactory() {
    @Override
    public <T extends IPortalView> IPortalViewStack<T> create(String id) {
      return new PortalViewStack<T>(id);
    }
  };

  private final List<IPortalViewStackListener> listeners = new ArrayList<IPortalViewStackListener>();
  private final LayoutPanel root = new LayoutPanel(new FlowLayout(false));
  private final SwitchPanel content = new SwitchPanel();
  private PortalViewStackHead<ViewStackItem<T>> header;

  private final Timer notifyTimer = new Timer() {
    @Override
    public void run() {
      doNotifyViewSwitch();
    }
  };

  public PortalViewStack(String id) {
    super(id);
  }

  @Override
  protected void init() {
    header = new PortalViewStackHead<ViewStackItem<T>>(this);
    root.add(header);
    header.setLayoutData(new FlowLayoutData(100, 20));
    content.setSize("100%", "100%");
    root.add(content);
    content.setLayoutData(new FlowLayoutData(100, 100, 1.0f));
  }

  @Override
  public Widget getWidget() {
    return root;
  }

  @Override
  public void activate(T view) {
    for (ViewStackItem<T> page : header.getViewItems())
      if (page.getView().equals(view))
        page.activate();
  }

  @Override
  public void add(T view) {
    ViewStackItem<T> page = new ViewStackItem<T>(content, view);
    header.add(page);
  }

  @Override
  public boolean contains(T view) {
    if (view != null) {
      for (ViewStackItem<T> item : header.getViewItems())
        if (item.getView().equals(view))
          return true;
    }
    return false;
  }

  @Override
  public T getActiveView() {
    ViewStackItem<T> activeItem = header.getActiveItem();
    if (activeItem != null)
      return activeItem.getView();
    return null;
  }

  @Override
  public List<T> getViews() {
    List<T> views = new ArrayList<T>(header.getViewItems().size());
    for (ViewStackItem<T> item : header.getViewItems())
      views.add(item.getView());
    return views;
  }

  @Override
  public void remove(T view) {
    for (ViewStackItem<T> item : header.getViewItems())
      if (item.getView().equals(view)) {
        header.remove(item);
        break;
      }
  }

  @Override
  public void onChangeViewState() {
    header.onChangeViewState();
  }

  private void notifyViewChange() {
    notifyTimer.cancel();
    notifyTimer.schedule(150);
  }

  protected void doNotifyViewSwitch() {
    IPortalView view = getActiveView();
    SafeIterator<IPortalViewStackListener> it = new SafeIterator<IPortalViewStackListener>(listeners);
    while (it.hasNext())
      it.next().onViewSwitch(view);
  }

  private void notifyViewClose(T view) {
    SafeIterator<IPortalViewStackListener> it = new SafeIterator<IPortalViewStackListener>(listeners);
    while (it.hasNext())
      it.next().onViewClose(view);
  }

  public void addListener(IPortalViewStackListener listener) {
    listeners.add(listener);
  }
}
