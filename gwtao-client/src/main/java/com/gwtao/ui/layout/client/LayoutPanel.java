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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.CSSUtils;

public class LayoutPanel extends ComplexPanel implements RequiresResize, ProvidesResize {
  private ILayout layout = DummyLayout.get();
  private static LayoutPanel ATTACH_ROOT;
  private boolean requiresLayout;

  public LayoutPanel() {
    Element div = DOM.createDiv();
    setElement(div);
  }

  public LayoutPanel(ILayout layout) {
    this();
    setLayout(layout);
  }

  public void setLayout(ILayout layout) {
    if (layout == null)
      throw new IllegalArgumentException("layout=null");
    if (this.layout != null)
      this.layout.exit();
    this.layout = layout;
    layout.init(this);
    if (isAttached()) {
      layout.measure();
      onResize();
    }
  }

  public ILayout getLayout() {
    return layout;
  }

  @Override
  public void add(Widget w) {
    insert(w, getWidgetCount());
  }

  public void insert(Widget w, int beforeIndex) {
    Element element = getElement();
    if (beforeIndex < getWidgetCount())
      super.insert(w, element, beforeIndex, true);
    else
      super.add(w, element);
    layout.onAddChild(w);
    adjustLayout();
  }

  @Override
  public boolean remove(Widget w) {
    layout.onRemoveChild(w);
    if (super.remove(w)) {
      adjustLayout();
      return true;
    }
    else
      return false;
  }

  private void adjustLayout() {
    if (isAttached()) {
      requiresLayout = true;
      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          if (requiresLayout) {
            requiresLayout = false;
            layout.measure();
            onResize();
          }
        }
      });
    }
  }

  @Override
  protected void onAttach() {
    if (ATTACH_ROOT == null) {
      ATTACH_ROOT = this;
    }
    super.onAttach();
  }

  @Override
  protected void onLoad() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        layout.measure();
      }
    });

    if (ATTACH_ROOT == this) {
      ATTACH_ROOT = null;
      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          if (getParent() instanceof RequiresResize)
            ((RequiresResize) getParent()).onResize();
          else {
            int w = getParent().getOffsetWidth();
            int h = getParent().getOffsetHeight();
            w += CSSUtils.calcPaddingWidth(getElement());
            h += CSSUtils.calcPaddingHeight(getElement());
            // w -= CSSUtils.calcMarginWidth(getElement());
            // h -= CSSUtils.calcMarginHeight(getElement());
            // w-=16;
            // h-=16;
            setSize(w + "PX", h + "PX");
            onResize();
          }
        }
      });
    }
  }

  @Override
  public void onResize() {
    layout.resize();
    for (Widget child : getChildren()) {
      if (child instanceof RequiresResize) {
        ((RequiresResize) child).onResize();
      }
    }
  }
}
