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

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.gwtao.portalapp.client.part.IPortalPart;
import com.gwtao.portalapp.client.part.IPortalPartContext;
import com.gwtao.ui.dialog.client.AsyncOkAnswere;
import com.gwtao.ui.dialog.client.MessageDialog;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.card.ICard;
import com.gwtao.ui.widgets.client.ToolPanel;

public class PortalViewStackHead<T extends IViewNavigatorItem> extends Composite implements IViewNavigator {

  private final class MaximizeAction extends Action {
    private final IPortalPart owner;

    private MaximizeAction(String title, String icon, IPortalPart owner) {
      super(title, icon);
      this.owner = owner;
    }

    @Override
    public void execute(Object... data) {
      owner.getPartContext().setViewState(IPortalPartContext.State.MAXIMIZED);
    }

    @Override
    public Permission getPermission(Object... data) {
      return owner.getPartContext().getViewState() != IPortalPartContext.State.MAXIMIZED ? Permission.ALLOWED : Permission.HIDDEN;
    }
  }

  private final class NormalizeAction extends Action {
    private final IPortalPart owner;

    private NormalizeAction(String title, String icon, IPortalPart owner) {
      super(title, icon);
      this.owner = owner;
    }

    @Override
    public void execute(Object... data) {
      owner.getPartContext().setViewState(IPortalPartContext.State.NORMAL);
    }

    @Override
    public Permission getPermission(Object... data) {
      return owner.getPartContext().getViewState() != IPortalPartContext.State.NORMAL ? Permission.ALLOWED : Permission.HIDDEN;
    }
  }

  private class TabField extends HorizontalPanel {
    private final HTML title = new HTML();
    private final T item;

    public TabField(T item) {
      this.item = item;
      title.getElement().getStyle().setProperty("whiteSpace", "nowrap");
      add(title);
      update();
    }

    public T getItem() {
      return item;
    }

    public boolean onDeselect() {
      return item.onDeactivate();
    }

    public void onActivate() {
      item.onActivate();
    }

    private void update() {
      title.setHTML(SafeHtmlUtils.fromString(item.getDisplayInfo().getDisplayText()));
    }
  }

  private final HorizontalPanel root = new HorizontalPanel();

  private final ToolPanel actionbar = new ToolPanel();
  private final ToolPanel partbar = new ToolPanel();

  private final TabBar tabbar = new TabBar() {
    @Override
    protected SimplePanel createTabTextWrapper() {
      return null;
    };
  };

  private final List<TabField> fields = new ArrayList<TabField>();

  private NormalizeAction normalizeAction;

  private MaximizeAction maximizeAction;

  public PortalViewStackHead(final IPortalPart owner) {
    root.add(tabbar);
    root.add(actionbar);
    root.add(partbar);

    normalizeAction = new NormalizeAction(null, "gwtaf-normalize-icon", owner);
    partbar.add(normalizeAction);

    maximizeAction = new MaximizeAction(null, "gwtaf-maximize-icon", owner);
    partbar.add(maximizeAction);

    initWidget(root);

    tabbar.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        int selectedTab = tabbar.getSelectedTab();
        if (selectedTab != -1 && event.getItem() != selectedTab) {
          TabField activeField = fields.get(selectedTab);
          if (!activeField.onDeselect())
            event.cancel();
        }
      }
    });

    tabbar.addSelectionHandler(new SelectionHandler<Integer>() {
      @Override
      public void onSelection(SelectionEvent<Integer> event) {
        TabField activeField = fields.get(event.getSelectedItem());
        activeField.onActivate();
      }
    });
  }

  public void add(T item) {
    TabField tab = new TabField(item);
    item.init(this);
    tabbar.addTab(tab);
    fields.add(tab);
    if (fields.size() == 1)
      tabbar.selectTab(0);
  }

  public void remove(T item) {
    int idx = indexOf(item);
    if (idx == -1)
      throw new IllegalArgumentException("Invalid item");

    if (item.isCloseable()) {
      String closeMessage = item.canClose();
      if (closeMessage == null) {
        item.onClose();

        fields.remove(idx);
        tabbar.removeTab(idx);
        if (idx >= tabbar.getTabCount())
          idx = tabbar.getTabCount() - 1;
        if (idx >= 0)
          tabbar.selectTab(idx);
      }
      else
        MessageDialog.alert("Close", closeMessage, AsyncOkAnswere.OK);
    }
  }

  private int indexOf(IViewNavigatorItem item) {
    int idx;
    for (idx = 0; idx < fields.size(); ++idx) {
      TabField field = fields.get(idx);
      if (field.getItem().equals(item)) {
        return idx;
      }
    }
    return -1;
  }

  public void onChangeViewState() {
    normalizeAction.getWidgetHandler().update();
    maximizeAction.getWidgetHandler().update();
  }

  @Override
  public void activate(IViewNavigatorItem item) {
    if (!isActivate(item)) {
      int idx = indexOf(item);
      if (idx != -1)
        tabbar.selectTab(idx);
    }
  }

  public boolean isActivate(IViewNavigatorItem item) {
    if (item == null)
      return false;
    IViewNavigatorItem active = getActiveItem();
    return active != null && active.equals(item);
  }

  public T getActiveItem() {
    int selected = tabbar.getSelectedTab();
    if (selected >= 0)
      return fields.get(selected).getItem();
    return null;
  }

  @Override
  public void updateActions(IViewNavigatorItem item) {
    if (isActivate(item)) {
      actionbar.update(item.getActions());
    }
  }

  @Override
  public void updateTitle(IViewNavigatorItem item) {
    int idx = fields.indexOf(item);
    if (idx >= 0)
      tabbar.selectTab(idx);

  }

  public void updateActions(ICard actions) {
    actionbar.update(actions);
  }

  public List<T> getViewItems() {
    List<T> result = new ArrayList<T>(fields.size());
    for (TabField field : fields)
      result.add(field.getItem());
    return result;
  }
}
