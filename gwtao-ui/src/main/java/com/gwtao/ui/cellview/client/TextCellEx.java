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
