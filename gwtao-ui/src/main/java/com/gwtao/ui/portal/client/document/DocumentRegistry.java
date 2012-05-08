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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.catalina.util.ParameterMap;

import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.gwtao.ui.portal.client.document.IDocumentDescriptor.Accordance;
import com.gwtao.ui.portal.client.factory.GenericRegistry;

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
      throw new NotFoundException("No proper document found for parameter '" + (parameter == null ? "null" : parameter.toString()) + "'. Please register with DocumentRegistry::register(U descriptor).");
    return create(result.get(0).getDescriptor().getId(), parameter);
  }

  @Override
  public String buildToken(String id, Object parameter) {
    IDocumentDescriptor descriptor = lookup(id);
    if (descriptor == null)
      throw new NotFoundException("No proper document found for id '" + id + "'");

    ParameterMap map = new ParameterMap();
    descriptor.encodeParameter(map, parameter);
    return URLUtil.assembleToken(id, map);
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
