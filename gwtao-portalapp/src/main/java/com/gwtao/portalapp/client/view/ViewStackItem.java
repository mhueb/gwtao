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
package com.gwtao.portalapp.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.portalapp.client.actionmanager.IActionManager;
import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.SwitchPanel;
import com.gwtao.ui.util.client.action.IActionInfo;

public class ViewStackItem<T extends IPortalView> implements IViewNavigatorItem, IPortalViewContext, IActionManager {

  private static class WrapperPanel extends SimplePanel implements RequiresResize {
    public void onResize() {
      Widget content = getWidget();
      if (content != null) {
        Element wrapperEl = getElement();
        content.setSize(wrapperEl.getClientWidth() + "px", wrapperEl.getClientHeight() + "px");
        if (content instanceof RequiresResize)
          ((RequiresResize) content).onResize();
      }
    }
  }

  private final WrapperPanel wrapper = new WrapperPanel();
  private final T view;
  private final SwitchPanel target;
  private final List<IActionInfo> actions = new ArrayList<IActionInfo>();
  private boolean created;
  private IViewNavigator navigator;

  public ViewStackItem(SwitchPanel target, T view) {
    this.target = target;
    this.view = view;
    view.setViewContext(this);
  }

  @Override
  public void init(IViewNavigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void activate() {
    navigator.activate(this);
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub

  }

  @Override
  public IActionManager getActionManager() {
    return this;
  }

  @Override
  public boolean isVisible() {
    return wrapper.isVisible();
  }

  @Override
  public void updateTitle() {
    navigator.updateTitle(this);
  }

  @Override
  public void onActivate() {
    show();
    updateTitle();
    updateActions();
    view.onActivate();
  }

  @Override
  public void addAction(IActionInfo action) {
    actions.add(action);
  }

  @Override
  public void updateActions() {
    navigator.updateActions(this);
  }

  private void show() {
    createContent();
    target.showWidget(wrapper);
    target.onResize();
  }

  private void createContent() {
    if (!created) {
      created = true;
      view.init();
      wrapper.setWidget(view.getWidget());
      target.add(wrapper);
    }
  }

  @Override
  public boolean onDeactivate() {
    view.onDeactivate();
    return true;
  }

  @Override
  public IDisplayableItem getDisplayInfo() {
    return view;
  }

  @Override
  public List<IActionInfo> getActions() {
    return actions;
  }

  @Override
  public String canClose() {
    return null;
  }

  @Override
  public boolean isCloseable() {
    return true;
  }

  @Override
  public void onOpen() {
    view.onOpen();
  }

  @Override
  public void onClose() {
    try {
      view.onClose();
    }
    catch (Exception e) {
      GWT.log(e.getMessage(), e);
    }
    wrapper.removeFromParent();
  }

  public T getView() {
    return view;
  }
}