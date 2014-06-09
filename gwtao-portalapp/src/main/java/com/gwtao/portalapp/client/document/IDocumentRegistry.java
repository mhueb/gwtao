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

import java.util.List;

import com.gwtao.portalapp.client.document.IDocumentDescriptor.Accordance;
import com.gwtao.portalapp.client.factory.IGenericRegistry;
import com.gwtao.ui.history.client.Token;

public interface IDocumentRegistry extends IGenericRegistry<IDocument, IDocumentDescriptor> {

  public static final class LookupItem {
    private final Accordance accordance;
    private final IDocumentDescriptor descriptor;

    public LookupItem(Accordance prio, IDocumentDescriptor descriptor) {
      this.accordance = prio;
      this.descriptor = descriptor;
    }

    public Accordance getAccordance() {
      return accordance;
    }

    public IDocumentDescriptor getDescriptor() {
      return descriptor;
    }
  }

  List<LookupItem> lookup(Object parameter);

  IDocument create(Object parameter);

  IDocument create(String id, Object parameter);

  Token buildToken(String id, Object parameter);
}
