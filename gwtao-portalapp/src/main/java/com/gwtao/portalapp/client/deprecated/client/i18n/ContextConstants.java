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
package com.gwtao.portalapp.client.deprecated.client.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface ContextConstants extends Constants {
  public final static ContextConstants c =   GWT.create(ContextConstants.class);

  String save();

  String revert();

  String refresh();

  String edit();

  String emptyTitle();

  String refreshing();

  String refreshQuestion();

  String reverting();

  String revertQuestion();

  String loading();

  String saving();

  String validateErrorsOnSave();

  String nothingToSave();

  String editorFailure();

  String serverValidateMessage();
}
