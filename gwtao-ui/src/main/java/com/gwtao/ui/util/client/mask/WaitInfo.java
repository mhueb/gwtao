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