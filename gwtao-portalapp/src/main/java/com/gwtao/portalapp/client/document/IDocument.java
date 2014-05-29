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

import com.gwtao.portalapp.client.view.IPortalView;

/**
 * A document presents information as main part of a portal.
 * <p>
 * 
 * 
 * @author mah
 * 
 */
public interface IDocument extends IPortalView {
  IDocumentDescriptor getDescriptor();

  void setParameter(Object parameter);

  Object getParameter();

  boolean isDirty();
}
