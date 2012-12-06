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
package com.gwtao.portalapp.client;

import com.gwtao.portalapp.client.document.IDocument;
import com.gwtao.portalapp.client.portlet.IPortlet;

public class PortalListenerAdapter implements IPortalListener {

  @Override
  public void onDocumentSwitch(IDocument doc) {
  }

  @Override
  public void onDocumentClose(IDocument doc) {
  }

  @Override
  public void onPortletSwitch(IPortlet portlet) {
  }

  @Override
  public void onPortletClose(IPortlet portlet) {
  }

}
