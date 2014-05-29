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
package com.gwtao.ui.cellview.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class TextCellEx extends AbstractCell<String> {
  @Override
  public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
    SafeHtml s = SafeHtmlUtils.fromTrustedString("<div style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;' title='");
    sb.append(s);
    sb.appendEscapedLines(value!=null?value:"");
    sb.append(SafeHtmlUtils.fromTrustedString("'>"));
    sb.appendEscapedLines(value!=null?value:"");
    sb.appendHtmlConstant("</div>");

  }
}
