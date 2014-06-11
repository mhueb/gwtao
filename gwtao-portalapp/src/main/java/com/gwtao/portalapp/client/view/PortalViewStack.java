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
package com.gwtao.portalapp.client.view;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.util.SafeIterator;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.part.AbstractPortalPart;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.LayoutPanel;
import com.gwtao.ui.layout.client.VerticalFlowLayout;
import com.gwtao.ui.widgets.client.SwitchPanel;

public class PortalViewStack<T extends IPortalView> extends AbstractPortalPart implements IPortalViewStack<T> {
  public static final IPortalViewStackFactory FACTORY = new IPortalViewStackFactory() {
    @Override
    public <T extends IPortalView> IPortalViewStack<T> create(String id) {
      return new PortalViewStack<T>(id);
    }
  };

  private final List<IPortalViewStackListener> listeners = new ArrayList<IPortalViewStackListener>();
  private final LayoutPanel root = new LayoutPanel(new VerticalFlowLayout());
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

  protected void notifyViewChange() {
    notifyTimer.cancel();
    notifyTimer.schedule(150);
  }

  protected void doNotifyViewSwitch() {
    IPortalView view = getActiveView();
    SafeIterator<IPortalViewStackListener> it = new SafeIterator<IPortalViewStackListener>(listeners);
    while (it.hasNext())
      it.next().onViewSwitch(view);
  }

  protected void notifyViewClose(T view) {
    SafeIterator<IPortalViewStackListener> it = new SafeIterator<IPortalViewStackListener>(listeners);
    while (it.hasNext())
      it.next().onViewClose(view);
  }

  public void addListener(IPortalViewStackListener listener) {
    listeners.add(listener);
  }
}
