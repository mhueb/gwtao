package com.gwtao.webapp.client.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Constants;

public interface WebAppConstants extends Constants {
  WebAppConstants CONST = GWT.create(WebAppConstants.class);

  String close();

  String back();

  String forward();

  String youAreLeaving();
}
