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
package com.gwtao.ui.util.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class JSUtil {
  public static void loadScript(String source) {
    Element ss = Document.get().createElement("script");
    ss.setAttribute("type", "text/javascript");
    ss.setAttribute("src", source);
    Element head = Document.get().getElementsByTagName("head").getItem(0);
    head.appendChild(ss);
  }
}
