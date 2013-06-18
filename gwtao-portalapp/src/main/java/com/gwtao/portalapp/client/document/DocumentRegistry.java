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
package com.gwtao.portalapp.client.document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gwtao.portalapp.client.document.IDocumentDescriptor.Accordance;
import com.gwtao.portalapp.client.factory.GenericRegistry;
import com.gwtao.ui.location.client.TokenUtils;
import com.gwtao.ui.location.client.Token;
import com.gwtao.ui.util.client.ParameterList;

public class DocumentRegistry extends GenericRegistry<IDocument, IDocumentDescriptor> implements IDocumentRegistry {
  private static final DocumentRegistry instance = new DocumentRegistry();

  public static DocumentRegistry get() {
    return instance;
  }

  public DocumentRegistry() {
  }

  @Override
  public IDocument create(String id, Object parameter) {
    IDocument doc = super.create(id);
    doc.setParameter(parameter);
    return doc;
  }

  @Override
  public IDocument create(Object parameter) {
    List<LookupItem> result = lookup(parameter);
    if (result.isEmpty())
      throw new IllegalArgumentException("No proper document found for parameter '" + (parameter == null ? "null" : parameter.toString()) + "'. Please register with DocumentRegistry::register(U descriptor).");
    return create(result.get(0).getDescriptor().getId(), parameter);
  }

  @Override
  public Token buildToken(String id, Object parameter) {
    IDocumentDescriptor descriptor = lookup(id);
    if (descriptor == null)
      throw new DocumentFoundException("No proper document found for id '" + id + "'");

    ParameterList.Builder builder = ParameterList.getBuilder();
    descriptor.encodeParameter(builder, parameter);
    return TokenUtils.buildToken(id, builder.build());
  }

  @Override
  public List<LookupItem> lookup(Object parameter) {
    List<LookupItem> result = new ArrayList<LookupItem>();
    for (IDocumentDescriptor descriptor : getDescriptors()) {
      Accordance prio = descriptor.canHandle(parameter);
      if (prio != null)
        result.add(new LookupItem(prio, descriptor));
    }

    sortByAccordance(result);
    return result;
  }

  private void sortByAccordance(List<LookupItem> result) {
    Collections.sort(result, new Comparator<LookupItem>() {
      @Override
      public int compare(LookupItem o1, LookupItem o2) {
        return o1.getAccordance().ordinal() - o2.getAccordance().ordinal();
      }
    });
  }
}
