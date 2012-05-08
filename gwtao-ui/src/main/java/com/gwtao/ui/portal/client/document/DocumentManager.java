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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.gwtao.common.shared.util.SafeIterator;
import com.gwtao.ui.portal.client.IPortalListener;
import com.gwtao.ui.portal.client.frame.IDocumentManager;
import com.gwtao.ui.portal.client.layout.IPortalLayout;
import com.gwtao.ui.portal.client.view.IPortalView;
import com.gwtao.ui.portal.client.view.IPortalViewStack;
import com.gwtao.ui.portal.client.view.IPortalViewStackListener;

public final class DocumentManager implements IDocumentManager {
  private final List<IPortalViewStack<IDocument>> docStackList = new ArrayList<IPortalViewStack<IDocument>>();
  private final List<IDocument> freeDocs = new LinkedList<IDocument>();
  private final List<IPortalListener> listeners;
  protected IDocument activeDocument;

  private final IPortalViewStackListener documentStackListener = new IPortalViewStackListener() {
    @Override
    public void onViewSwitch(IPortalView view) {
      IDocument doc = (IDocument) view;
      if (!ObjectUtils.equals(doc, activeDocument) || doc == null && activeDocument == null) {
        activeDocument = doc;
        SafeIterator<IPortalListener> it = new SafeIterator<IPortalListener>(listeners);
        while (it.hasNext())
          it.next().onDocumentSwitch(activeDocument);
      }
    }

    public void onViewClose(IPortalView view) {
      IDocument doc = (IDocument) view;
      freeDocs.remove(doc);
      if (ObjectUtils.equals(doc, activeDocument))
        activeDocument = null;
      SafeIterator<IPortalListener> it = new SafeIterator<IPortalListener>(listeners);
      while (it.hasNext())
        it.next().onDocumentClose(doc);
    }
  };

  public DocumentManager(List<IPortalListener> listeners) {
    this.listeners = listeners;
  }

  @Override
  public void addDocument(IDocument doc, boolean separat) {
    if (!separat) {
      SafeIterator<IDocument> it = new SafeIterator<IDocument>(freeDocs);
      while (it.hasNext()) {
        IDocument free = it.next();
        if (!free.isDirty()) {
          removeDocument(free);
        }
      }
      freeDocs.add(doc);
    }

    IPortalViewStack<IDocument> stack = findDocumentStack(doc);
    if (stack == null)
      stack = getDefaultDocumentStack();
    stack.add(doc);
  }

  private IPortalViewStack<IDocument> findDocumentStack(IDocument doc) {
    for (IPortalViewStack<IDocument> stack : docStackList) {
      if (stack.contains(doc))
        return stack;
    }
    return null;
  }

  private IPortalViewStack<IDocument> getDefaultDocumentStack() {
    if (docStackList.isEmpty()) {
      // DocumentStack stack = new DocumentStack(IPortalLayout.DOCUMENTS);
      // Widget w = stack.getWidget();
      // xxx.add(w);
      // // frame.setWidgetPosition(w, 0, 0);
      // w.setSize("600px", "400px");
      // addStack(stack);
      // return stack;
      throw new IllegalStateException("Documents not part of portal layout");
    }
    for (IPortalViewStack<IDocument> stack : docStackList) {
      if (stack.getId().equals(IPortalLayout.DOCUMENTS))
        return stack;
    }
    return docStackList.get(0);
  }

  @Override
  public void removeDocument(IDocument doc) {
    IPortalViewStack<IDocument> stack = findDocumentStack(doc);
    if (stack == null)
      return;

    stack.remove(doc);
  }

  @Override
  public IDocument getActiveDocument() {
    return activeDocument;
  }

  @Override
  public List<IDocument> getAllDocuments() {
    List<IDocument> docs = new ArrayList<IDocument>();
    for (IPortalViewStack<IDocument> stack : docStackList)
      docs.addAll(stack.getViews());
    return docs;
  }

  @Override
  public void addStack(IPortalViewStack<IDocument> stack) {
    docStackList.add(stack);
    stack.addListener(documentStackListener);
  }

  @Override
  public void clear() {
    docStackList.clear();
    freeDocs.clear();
  }

}