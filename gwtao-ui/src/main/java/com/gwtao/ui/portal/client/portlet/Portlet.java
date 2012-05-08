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
package com.gwtao.ui.portal.client.portlet;

import org.apache.commons.lang.ObjectUtils;

import com.gwtao.ui.portal.client.Portal;
import com.gwtao.ui.portal.client.PortalListenerAdapter;
import com.gwtao.ui.portal.client.document.IDocument;
import com.gwtao.ui.portal.client.view.AbstractPortalView;

/**
 * <p>
 * <b>Hinweis</b>: In der Regel sollte jedes kontextabhängige Portlet {@link #onDocumentSwitch} überschreiben.
 * Damit kann sichergestellt werden, dass ein Dokumentenwechsel im Portlet auch bekannt wird und sich der
 * Inhalt gemäß der neuen Daten ändern kann.
 */
public abstract class Portlet extends AbstractPortalView implements IPortlet {
  private boolean visible = true;

  private final class ActivateListener extends PortalListenerAdapter {
    @Override
    public void onDocumentSwitch(IDocument doc) {
      if (getViewContext() != null && getViewContext().isVisible())
        Portlet.this.onDocumentSwitch(doc);
    }

    @Override
    public void onPortletSwitch(IPortlet portlet) {
      if (!visible && ObjectUtils.equals(Portlet.this, portlet)) {
        visible = true;
        Portlet.this.onDocumentSwitch(Portal.get().getActiveDocument());
      }
    }

    @Override
    public void onDocumentClose(IDocument doc) {
      Portlet.this.onDocumentClose(doc);
    }
  }

  private final IPortletDescriptor descr;

  private PortalListenerAdapter listener;

  protected Portlet(IPortletDescriptor descr) {
    this.descr = descr;
  }

  /**
   * Reaktion auf Wechsel des aktiven Dokuments.
   * <p>
   * Wird gerufen wenn {@link #monitorDocuments} aktiviert wurde.
   * 
   * @param doc Das {@link IDocument} das aktiviert wurde oder null, falls keines aktiv ist.
   */
  protected void onDocumentSwitch(IDocument doc) {
  }

  /**
   * Reaktion auf Schließen eines Dokuments
   * <p>
   * Wird gerufen wenn {@link #monitorDocuments} aktiviert wurde
   * 
   * @param doc
   */
  protected void onDocumentClose(IDocument doc) {
  }

  @Override
  public void onActivate() {
    if (listener != null)
      onDocumentSwitch(Portal.get().getActiveDocument());
  }

  /**
   * @param monitor <code>true</code> wenn man {@link #onDocumentActivate} überschrieben hat und mitbekommen
   *          will, dass sich das aktuell geladene Dokument geändert hat, <code>false</code> wenn bereits
   *          hinzugefügte Portal Listener entfernt werden sollen.
   */
  protected void monitorDocuments(boolean monitor) {
    if (listener == null) {
      if (monitor) {
        Portal.get().addListener(listener = new ActivateListener());
        IDocument activeDocument = Portal.get().getActiveDocument();
        onDocumentSwitch(activeDocument);
      }
    }
    else if (!monitor) {
      Portal.get().removeListener(listener);
      listener = null;
    }
  }

  @Override
  public String getId() {
    return descr.getId();
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
}
