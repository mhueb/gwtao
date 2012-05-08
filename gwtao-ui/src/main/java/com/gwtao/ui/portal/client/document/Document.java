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
package com.gwtao.ui.portal.client.document;

import com.gwtao.ui.portal.client.i18n.PortalMessages;
import com.gwtao.ui.portal.client.view.AbstractPortalView;


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
    return isDirty() ? PortalMessages.c.closeChangeDocument(getTitle()) : null;
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
  public String getIcon() {
    return descr.getIcon();
  }

  @Override
  public String getTitle() {
    return descr.getTitle();
  }

  @Override
  public String getTooltip() {
    return descr.getTooltip();
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
