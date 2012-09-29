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
package com.gwtao.utils.shared.rc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.gwtao.utils.shared.util.GWTCreator;
import com.gwtao.utils.shared.util.TextGetterFactory;

public interface CommonConstants extends Constants {
  public final static CommonConstants c = new GWTCreator<CommonConstants>() {
    @Override
    protected CommonConstants gwtCreate() {
      return GWT.create(CommonConstants.class);
    }

    @Override
    protected CommonConstants srvCreate() {
      return TextGetterFactory.create(CommonConstants.class);
    }
  }.create();

  String january();

  String february();

  String march();

  String april();

  String mai();

  String june();

  String july();

  String august();

  String september();

  String october();

  String november();

  String december();

  String abbreviationDays();

  String abbreviationHours();

  String abbreviationMinutes();

  String abbreviationSeconds();

  String today();

  String yesterday();

  String info();

  String warn();

  String decide();

  String error();

  String fatal();
}
