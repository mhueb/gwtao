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
package com.gwtao.ui.layout.client;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.Size;
import com.gwtao.ui.widgets.client.SplitBar;
import com.gwtao.ui.widgets.client.SplitBar.ISizeable;

public class SplitFlowLayout extends FlowLayout {
  private final class SizerAdapter implements ISizeable {
    private final int left;
    private final int right;
    int ls, rp, rs;

    private SizerAdapter(int left, int right) {
      this.right = right;
      this.left = left;
    }

    @Override
    public void start() {
      final Widget l = getLayoutPanel().getWidget(left);
      final Widget r = getLayoutPanel().getWidget(right);

      if (isHorizontal()) {
        ls = l.getOffsetWidth();
        rp = r.getAbsoluteLeft();
        rs = r.getOffsetWidth();
      }
      else {
        ls = l.getOffsetHeight();
        rp = r.getAbsoluteTop();
        rs = r.getOffsetHeight();
      }
    }

    @Override
    public void finish() {
      recalcRatio();
    }

    @Override
    public int resize(int delta, int barsize) {
      final Widget l = getLayoutPanel().getWidget(left);
      FlowLayoutData ld = (FlowLayoutData) l.getLayoutData();
      final Widget r = getLayoutPanel().getWidget(right);
      FlowLayoutData rd = (FlowLayoutData) r.getLayoutData();

      int lsdm = -(ls - ld.getMinSize().get(isHorizontal()));
      int rsdm = (rs - rd.getMinSize().get(isHorizontal()));

      delta = Math.max(delta, lsdm);
      delta = Math.min(delta, rsdm);

      if (isHorizontal()) {
        l.setWidth((ls + delta) + "px");
        DOM.setStyleAttribute(r.getElement(), "left", (rp + delta) + "px");
        r.setWidth((rs - delta) + "px");
      }
      else {
        l.setHeight((ls + delta) + "px");
        DOM.setStyleAttribute(r.getElement(), "top", (rp + delta) + "px");
        r.setHeight((rs - delta) + "px");
      }
      if (l instanceof RequiresResize)
        ((RequiresResize) l).onResize();
      if (r instanceof RequiresResize)
        ((RequiresResize) r).onResize();

      return delta;
    }
  }

  private int barsize;

  public SplitFlowLayout(boolean horizontal) {
    this(horizontal, 4);
  }

  public SplitFlowLayout(boolean horizontal, int barsize) {
    super(horizontal);
    this.barsize = barsize;
  }

  @Override
  protected void init() {
    super.init();
    if (getLayoutPanel().getWidgetCount() > 0)
      addSplitBars();
  }

  @Override
  public void exit() {
    removeSplitBars();
    super.exit();
  }

  private void removeSplitBars() {
    Iterator<Widget> it = iterateWidgets();
    while (it.hasNext()) {
      Widget w = it.next();
      if (w instanceof SplitBar)
        it.remove();
    }
  }

  private void addSplitBars() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onAddChild(Widget child) {
    super.onAddChild(child);

    if (child instanceof SplitBar)
      return;

    int count = getLayoutPanel().getWidgetCount();
    if (count > 1) {
      int idx = getLayoutPanel().getWidgetIndex(child);
      if (idx == 0) {
        addSplitBar(idx, idx + 1);
      }
      else if (idx == count - 1) {
        addSplitBar(idx - 1, idx);
      }
      else {
        if (getLayoutPanel().getWidget(idx - 1) instanceof SplitBar)
          addSplitBar(idx, idx + 1);
        else
          addSplitBar(idx - 1, idx);
      }
    }
  }

  private void addSplitBar(final int left, final int right) {
    Widget l = getLayoutPanel().getWidget(left);
    Widget r = getLayoutPanel().getWidget(right);

    if (!getWidgetData(l).isSizable() || !getWidgetData(r).isSizable())
      return;

    SplitBar bar = SplitBar.create(new SizerAdapter(left, right + 1), isHorizontal(), barsize);
    bar.setLayoutData(new FlowLayoutData(barsize, barsize));

    getLayoutPanel().insert(bar, right);
  }

  protected void recalcRatio() {
    int minSize = getMinSize().get(isHorizontal());
    int totalSize = 0;
    Iterator<Widget> it = iterateWidgets();
    while (it.hasNext()) {
      if (isHorizontal())
        totalSize += it.next().getOffsetWidth();
      else
        totalSize += it.next().getOffsetHeight();
    }

    int freeSize = totalSize - minSize;
    if (freeSize < 0)
      freeSize = totalSize;

    it = iterateWidgets();
    while (it.hasNext()) {
      Widget child = it.next();
      FlowLayoutData widgetData = getWidgetData(child);
      if (widgetData.getRatio() == 0.0f)
        continue;
      int min = widgetData.getMinSize().get(isHorizontal());
      int size = getWidgetSize(child).get(isHorizontal());
      int wfs = size - min;
      float newRatio = 1.0f * wfs / freeSize;
      child.setLayoutData(new FlowLayoutData(widgetData.getMinWidth(), widgetData.getMinHeight(), newRatio));
    }

    measure();
  }

  private Size getWidgetSize(Widget child) {
    return new Size(child.getElement().getOffsetWidth(), child.getElement().getOffsetHeight());
  }

  @Override
  public void onRemoveChild(Widget child) {
    if (child instanceof SplitBar)
      return;

    int count = getLayoutPanel().getWidgetCount();
    if (count > 1) {
      int idx = getLayoutPanel().getWidgetIndex(child);
      if (idx == 0) {
        removeSplitBar(idx + 1);
      }
      else if (idx == count - 1) {
        removeSplitBar(idx - 1);
      }
      else if (!removeSplitBar(idx - 1)) {
        removeSplitBar(idx + 1);
      }
    }
    super.onRemoveChild(child);
  }

  private boolean removeSplitBar(int i) {
    Widget w = getLayoutPanel().getWidget(i);
    if (w instanceof SplitBar) {
      w.removeFromParent();
      return true;
    }
    return false;
  }
}
