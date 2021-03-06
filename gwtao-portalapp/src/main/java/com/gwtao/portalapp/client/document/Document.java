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
package com.gwtao.portalapp.client.document;

import com.gwtao.portalapp.client.i18n.PortalMessages;
import com.gwtao.portalapp.client.view.AbstractPortalView;


public abstract class Document extends AbstractPortalView implements IDocument {
  private final IDocumentDescriptor descr;
  private Object param;

  protected Document(IDocumentDescriptor descr) {
    this.descr = descr;
  }

  @Override
  public IDocumentDescriptor getDescriptor() {
    return descr;
  }

  @Override
  public String canClose() {
    return isDirty() ? PortalMessages.c.closeChangeDocument(getDisplayText()) : null;
  }

  @Override
  public boolean isClosable() {
    return true;
  }

  @Override
  public void onActivate() {
  }

  @Override
  public void onClose() {
  }

  @Override
  public void onDeactivate() {
  }

  @Override
  public String getDisplayIcon() {
    return descr.getDisplayIcon();
  }

  @Override
  public String getDisplayText() {
    return descr.getDisplayText();
  }

  @Override
  public String getDisplayTooltip() {
    return descr.getDisplayTooltip();
  }

  @Override
  public final void setParameter(Object parameter) {
    this.param = parameter;
  }

  @Override
  public Object getParameter() {
    return param;
  }

  @Override
  public boolean isDirty() {
    return false;
  }

  @Override
  public void onOpen() {
  }
}
