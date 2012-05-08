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
package com.gwtao.ui.util.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

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
    DOM.setStyleAttribute(getElement(), "cursor", getCursor());
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
      DOM.setStyleAttribute(getElement(), "left", pos + "PX");
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
      DOM.setStyleAttribute(getElement(), "top", pos + "PX");
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
