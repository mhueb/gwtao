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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.util.SafeIterator;

import com.gwtao.portalapp.client.IPortalListener;
import com.gwtao.portalapp.client.frame.IDocumentManager;
import com.gwtao.portalapp.client.layout.IPortalLayout;
import com.gwtao.portalapp.client.view.IPortalView;
import com.gwtao.portalapp.client.view.IPortalViewStack;
import com.gwtao.portalapp.client.view.IPortalViewStackListener;

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

  @Override
  public String canClose() {
    String message = activeDocument == null ? null : activeDocument.canClose();
    if (StringUtils.isBlank(message)) {
      for (IDocument doc : getAllDocuments()) {
        message = doc.canClose();
        if (StringUtils.isNotBlank(message))
          break;
      }
    }
    return message;
  }
}