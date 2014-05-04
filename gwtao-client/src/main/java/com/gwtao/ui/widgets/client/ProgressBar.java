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
package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.gwtao.ui.util.client.ProgressBarRenderer;

public class ProgressBar extends ComplexPanel {
  private ProgressBarRenderer render = new ProgressBarRenderer();
  private int max;

  public ProgressBar() {
    this(100);
  }

  public ProgressBar(int max) {
    setElement(Document.get().createDivElement());
    this.max = max;
    update(0, "");
  }

  public void update(int percent, String msg) {
    getElement().setInnerHTML(render.generate(percent * 100 / max, msg));
  }

  public void setWidth(String width) {
    super.setWidth(width);
    getElement().getStyle().setFloat(Float.LEFT);
    DOM.setStyleAttribute(getElement(), "display", "inline");
  }

  public void setFloat(String style) {
    DOM.setStyleAttribute(getElement(), "float", style);
  }
}