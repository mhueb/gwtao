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
package com.gwtao.ui.portal.client.welcome;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.portal.client.portlet.IPortlet;
import com.gwtao.ui.portal.client.portlet.IPortletDescriptor;
import com.gwtao.ui.portal.client.portlet.Portlet;
import com.gwtao.ui.portal.client.portlet.PortletDescriptor;

public class WelcomePortlet extends Portlet {
  public static final String ID = "Welcome";

  public static final IPortletDescriptor descriptor = new PortletDescriptor(ID, "Welcome") {
    @Override
    public IPortlet create() {
      return new WelcomePortlet();
    }
  };

  private HTML html;

  public WelcomePortlet() {
    super(descriptor);
  }

  @Override
  public void init() {
    html = new HTML(makeInfo("Welcome to gwt-af portal application"));
  }

  @Override
  public Widget getWidget() {
    return html;
  }

  private String makeInfo(String info) {
    return "<div style='font:italic 11px tahoma,verdana,helvetica;white-space:nowrap;padding:4px;'>" + info + "</div>";
  }
}
