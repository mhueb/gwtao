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
