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
package com.gwtao.ui.model.client.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface ModelConstants extends Constants {
  public final static ModelConstants c =   GWT.create(ModelConstants.class);

  String save();

  String revert();

  String refresh();

  String remove();

  String edit();

  String refreshing();

  String refreshQuestion();

  String reverting();

  String revertQuestion();
  
  String removeQuestion();

  String loading();

  String saving();

  String validateErrorsOnSave();

  String nothingToSave();
}
