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
package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.GlassPane;

public abstract class SplitBar extends Widget {
  public interface ISizeable {
    void start();

    int resize(int delta, int barsize);

    void finish();
  }

  private final ISizeable sizer;
  private int offset;
  private boolean mouseDown;
  private final int barsize;
  private int splitpos;
  private GlassPane shield;

  private Timer mover = new Timer() {
    @Override
    public void run() {
      asyncMove();
    }
  };
  private int delta;

  public SplitBar(ISizeable sizer, int barsize) {
    this.sizer = sizer;
    this.barsize = barsize;
    setElement(Document.get().createDivElement());
    sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEMOVE | Event.ONDBLCLICK);
    getElement().getStyle().setProperty("cursor", getCursor());
  }

  @Override
  public void onBrowserEvent(Event event) {
    switch (event.getTypeInt()) {
    case Event.ONMOUSEDOWN:
      mouseDown = true;
      offset = getEventPosition(event);
      splitpos = getAbsolutePosition();
      shield = GlassPane.createGlassPane(this, getCursor());
      Event.setCapture(getElement());
      event.preventDefault();
      sizer.start();
      break;

    case Event.ONMOUSEUP:
      mouseDown = false;
      shield.remove();
      shield = null;
      Event.releaseCapture(getElement());
      event.preventDefault();
      sizer.finish();
      break;

    case Event.ONMOUSEMOVE:
      if (mouseDown) {
        mover.cancel();
        delta = getEventPosition(event) - offset;
        event.preventDefault();
        mover.schedule(50);
      }
      break;
    }
  }

  private void asyncMove() {
    resize(delta);
  }

  private void resize(int delta) {
    delta = sizer.resize(delta, barsize);
    movePosition(splitpos + delta);
  }

  protected abstract void movePosition(int delta);

  protected abstract int getAbsolutePosition();

  protected abstract int getEventPosition(Event event);

  protected abstract String getCursor();

  private static class HSplitBar extends SplitBar {
    public HSplitBar(ISizeable sizer, int barsize) {
      super(sizer, barsize);
      getElement().getStyle().setPropertyPx("width", barsize);
      setStyleName("gwtaf-HorizontalSplitBar");
    }

    @Override
    protected int getAbsolutePosition() {
      return getAbsoluteLeft();
    }

    @Override
    protected int getEventPosition(Event event) {
      return event.getClientX();
    }

    @Override
    public void movePosition(int pos) {
      getElement().getStyle().setProperty("left", pos + "PX");
    }

    @Override
    protected String getCursor() {
      return "ew-resize";
    }
  }

  private static class VSplitBar extends SplitBar {
    public VSplitBar(ISizeable sizer, int barsize) {
      super(sizer, barsize);
      getElement().getStyle().setPropertyPx("height", barsize);
      setStyleName("gwtaf-VerticalSplitBar");
    }

    @Override
    protected int getAbsolutePosition() {
      return getAbsoluteTop();
    }

    @Override
    protected int getEventPosition(Event event) {
      return event.getClientY();
    }

    @Override
    public void movePosition(int pos) {
      getElement().getStyle().setProperty("top", pos + "PX");
    }

    @Override
    protected String getCursor() {
      return "ns-resize";
    }
  }

  public static SplitBar create(ISizeable sizer, boolean horizontal, int barsize) {
    if (horizontal)
      return new HSplitBar(sizer, barsize);
    else
      return new VSplitBar(sizer, barsize);
  }
}
