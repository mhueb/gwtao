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
package com.gwtao.ui.breadcrumb.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

public class BreadCrumbPanel extends ComplexPanel {

  public interface IBreadCrumbItem {
    String getTitle();

    String getToken();
    // IActionSoure getActions();
  }

  public BreadCrumbPanel() {
    setElement(DOM.createDiv());
    getElement().getStyle().setBackgroundColor("#f0f0f0");
  }

  public void updateTitle(String token, String title) {
    // TODO Auto-generated method stub

  }

}