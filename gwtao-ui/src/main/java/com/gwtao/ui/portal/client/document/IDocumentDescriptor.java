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
package com.gwtao.ui.portal.client.document;

import java.util.List;

import com.gwtao.ui.location.client.Token;
import com.gwtao.ui.portal.client.factory.IGenericDescriptor;

/**
 * Each document needs a descriptor to decide upon which gwt component should be used for displaying the
 * content.
 */
public interface IDocumentDescriptor extends IGenericDescriptor<IDocument> {
  /**
   * <li>{@link #PRIMARY}: The main editor <li>{@link #SECONDARY}: Not the main editor, but a component that
   * is able to display the document type, too
   */
  enum Accordance {
    PRIMARY,
    SECONDARY
  };

  void encodeParameter(List<Token.Parameter> params, Object param);

  Object decodeParameter(List<Token.Parameter> param);

  /**
   * Determine whether the implementing component is able to handle the given doc type
   * 
   * @param param The Object to check
   * @return {@link Accordance} (it's like a priority value)
   */
  Accordance canHandle(Object param);
}
