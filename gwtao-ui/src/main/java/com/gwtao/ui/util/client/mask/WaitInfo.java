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
package com.gwtao.ui.util.client.mask;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WaitInfo implements IWaitInfo {

  public interface Resource extends ClientBundle {

    public interface Style extends CssResource {
    };

    @Source("circle-ball.gif")
    ImageResource loadingImage();
  }

  private static final Resource defaultResource = GWT.create(Resource.class);

  private final VerticalPanel msg = new VerticalPanel();
  private final VerticalPanel inner = new VerticalPanel();
  private final HTML text = new HTML();
  private Resource resource;

  public WaitInfo() {

    this.msg.setStyleName("gwtaf-mask-msg");
    this.inner.setStyleName("gwtaf-mask-msg-inner");
    this.msg.add(inner);

    HorizontalPanel row = new HorizontalPanel();
    Image icon = new Image(GWT.getModuleBaseURL() + getResource().loadingImage(), 0, 0, 16, 16);
    row.add(icon);
    this.text.setStyleName("gwtaf-mask-msg-text");
    row.add(this.text);
    row.setCellVerticalAlignment(icon, HasVerticalAlignment.ALIGN_MIDDLE);
    row.setCellVerticalAlignment(this.text, HasVerticalAlignment.ALIGN_MIDDLE);
    add(row);
  }

  private Resource getResource() {
    if (resource == null)
      resource = defaultResource;
    return resource;
  }

  public void add(Widget row) {
    inner.add(row);
  }

  @Override
  public void setMessage(String msg) {
    text.setHTML(SafeHtmlUtils.fromString(msg));
  }

  @Override
  public Widget getWidget() {
    return msg;
  }

}