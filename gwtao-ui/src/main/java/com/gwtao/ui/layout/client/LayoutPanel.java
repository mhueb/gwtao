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
