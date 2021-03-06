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
package com.gwtao.portalapp.client.portlet;

import org.apache.commons.lang.ObjectUtils;

import com.gwtao.portalapp.client.IPortalListener;
import com.gwtao.portalapp.client.PortalApp;
import com.gwtao.portalapp.client.PortalListenerAdapter;
import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.view.AbstractPortalView;

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
        Portlet.this.onDocumentSwitch(PortalApp.get().getActiveDocument());
      }
    }

    @Override
    public void onDocumentClose(IDocument doc) {
      Portlet.this.onDocumentClose(doc);
    }
  }

  private final IPortletDescriptor descr;

  private IPortalListener listener;

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
      onDocumentSwitch(PortalApp.get().getActiveDocument());
  }

  /**
   * @param monitor <code>true</code> wenn man {@link #onDocumentActivate} überschrieben hat und mitbekommen
   *          will, dass sich das aktuell geladene Dokument geändert hat, <code>false</code> wenn bereits
   *          hinzugefügte Portal Listener entfernt werden sollen.
   */
  protected void monitorDocuments(boolean monitor) {
    if (listener == null) {
      if (monitor) {
        PortalApp.get().addListener(listener = new ActivateListener());
        IDocument activeDocument = PortalApp.get().getActiveDocument();
        onDocumentSwitch(activeDocument);
      }
    }
    else if (!monitor) {
      PortalApp.get().removeListener(listener);
      listener = null;
    }
  }

  @Override
  public String getId() {
    return descr.getId();
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
}
