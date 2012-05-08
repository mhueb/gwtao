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
package com.gwtao.ui.layout.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.Size;

public class LayoutPanel extends ComplexPanel implements RequiresResize, ProvidesResize, ILayoutContainer {
	private ILayout layout = DummyLayout.get();
	private boolean attaching;

	public LayoutPanel() {
		setElement(DOM.createDiv());
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
		if (isAttached())
			layout();
	}

	public ILayout getLayout() {
		return layout;
	}

	@Override
	public void add(Widget w) {
		insert(w, getWidgetCount());
	}

	public void insert(Widget w, int beforeIndex) {
		if (beforeIndex < getWidgetCount())
			super.insert(w, getElement(), beforeIndex, true);
		else
			super.add(w, getElement());
		layout.onAddChild(w);
		adjustLayout();
	}

	@Override
	public boolean remove(Widget w) {
		layout.onRemoveChild(w);
		if (super.remove(w)) {
			adjustLayout();
			return true;
		} else
			return false;
	}

	private void adjustLayout() {
		if (isAttached())
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					layout.measure();
					onResize();
				}
			});
	}

	@Override
	protected void onAttach() {
		attaching = true;
		super.onAttach();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		layout.measure();
		if (getParent() instanceof ILayoutContainer)
			((ILayoutContainer) getParent()).layout();
		else
			onResize();
		attaching = false;
	}

	public void layout() {
		if (attaching)
			return;
		layout.measure();
		if (getParent() instanceof ILayoutContainer)
			((ILayoutContainer) getParent()).layout();
		else
			onResize();
	}

	@Override
	public void onResize() {
		layout.layout();
		for (Widget child : getChildren()) {
			if (child instanceof RequiresResize) {
				((RequiresResize) child).onResize();
			}
		}
	}

	@Override
	public Size getMinSize() {
		return layout.getMinSize();
	}
}
