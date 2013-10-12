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
package com.gwtao.ui.cellview.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;
import com.gwtao.ui.cellview.client.i18n.CellViewConstants;

public class SimplePagerEx extends SimplePager {
  private static final SimplePager.Resources RESOURCES = GWT.create(SimplePager.Resources.class);

  public SimplePagerEx(TextLocation location, boolean showFastForwardButton, int fastForwardRows, boolean showLastPageButton) {
    super(location, RESOURCES, showFastForwardButton, fastForwardRows, showLastPageButton);
  }

  public SimplePagerEx(TextLocation location, Resources resources, boolean showFastForwardButton, int fastForwardRows, boolean showLastPageButton) {
    super(location, resources, showFastForwardButton, fastForwardRows, showLastPageButton);
  }

  protected String createText() {
    NumberFormat formatter = NumberFormat.getFormat("#,###");
    HasRows display = getDisplay();
    Range range = display.getVisibleRange();
    int pageStart = range.getStart() + 1;
    int pageSize = range.getLength();
    int dataSize = display.getRowCount();
    int endIndex = Math.min(dataSize, pageStart + pageSize - 1);
    endIndex = Math.max(pageStart, endIndex);
    boolean exact = display.isRowCountExact();
    return formatter.format(pageStart) + "-" + formatter.format(endIndex) + " " + (exact ? CellViewConstants.consts.from() + " " : CellViewConstants.consts.fromCa()) + formatter.format(dataSize);
  }
}