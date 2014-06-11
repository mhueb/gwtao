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
package com.gwtao.ui.util.client.mask;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.dialog.client.IPopupHandler;
import com.gwtao.ui.dialog.client.PopupManager;

public class WaitMask extends ComplexPanel implements IWaitMask {
  public interface Style extends CssResource {
    String waitMaskParent();

    String waitMaskGlass();

    String waitMaskFloater();

    String waitMaskFrame();

    String waitMaskContent();

    String waitMaskIcon();

    String waitMaskMsg();
  };

  public interface Resource extends ClientBundle {

    @Source("circle-ball.gif")
    ImageResource loadingImage();

    @Source("mask.css")
    @NotStrict
    Style styles();
  }

  private static final Resource defaultResource = GWT.create(Resource.class);

  private final IsWidget related;
  private int showLock;
  private Resource resource;
  private HTML msg;
  private boolean active;

  private Element parentElement;

  public WaitMask(IsWidget related) {
    this.related = related;

    PopupManager.get().addHandler(new IPopupHandler() {

      @Override
      public void show() {
        if (active)
          WaitMask.this.doShow();
      }

      @Override
      public void hide() {
        if (active)
          WaitMask.this.doHide();
      }

      @Override
      public String canHide() {
        return null;
      }
    });

    Element root = DOM.createDiv();
    Element glass = DOM.createDiv();
    Element floater = DOM.createDiv();
    Element frame = DOM.createDiv();
    Element content = DOM.createDiv();

    frame.appendChild(floater);
    frame.appendChild(content);
    root.appendChild(glass);
    root.appendChild(frame);
    setElement(root);

    msg = new HTML();
    Image icon = new Image(getResource().loadingImage());
    insert(icon, content, 0, true);
    insert(msg, content, 1, true);

    glass.setClassName(getResource().styles().waitMaskGlass());
    floater.setClassName(getResource().styles().waitMaskFloater());
    frame.setClassName(getResource().styles().waitMaskFrame());
    content.setClassName(getResource().styles().waitMaskContent());
    icon.setStyleName(getResource().styles().waitMaskIcon());
    msg.setStyleName(getResource().styles().waitMaskMsg());
  }

  private Resource getResource() {
    if (resource == null)
      resource = defaultResource;
    resource.styles().ensureInjected();
    return resource;
  }

  @Override
  public void show() {
    if (showLock++ == 0) {
      this.active = true;
      doShow();
    }
  }

  @Override
  public void hide() {
    if (showLock > 0) {
      if (--showLock == 0) {
        doHide();
        this.active = false;
        msg.setText("");
      }
    }
  }

  private void doShow() {
    parentElement = this.related.asWidget().getElement();
    if (parentElement != null) {
      parentElement.addClassName(getResource().styles().waitMaskParent());
      parentElement.appendChild(getElement());
    }
  }

  private void doHide() {
    if (parentElement != null) {
      parentElement.removeClassName(getResource().styles().waitMaskParent());
      parentElement.removeChild(getElement());
    }
  }

  public void setMessage(SafeHtml message) {
    msg.setHTML(message);
  }
}
