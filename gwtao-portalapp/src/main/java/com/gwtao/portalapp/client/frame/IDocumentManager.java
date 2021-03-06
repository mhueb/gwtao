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
package com.gwtao.portalapp.client.frame;

import java.util.List;

import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.view.IPortalViewStack;

public interface IDocumentManager {
  List<IDocument> getAllDocuments();

  IDocument getActiveDocument();

  void addDocument(IDocument doc, boolean separat);

  void removeDocument(IDocument doc);

  void addStack(IPortalViewStack<IDocument> stack);

  void clear();

  String canClose();
}
