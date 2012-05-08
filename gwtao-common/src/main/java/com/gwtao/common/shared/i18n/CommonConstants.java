/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.common.shared.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.gwtao.common.shared.util.GWTCreator;

public interface CommonConstants extends Constants {
  public final static CommonConstants c = new GWTCreator<CommonConstants>() {
    @Override
    protected CommonConstants gwtCreate() {
      return GWT.create(CommonConstants.class);
    }

    @Override
    protected CommonConstants srvCreate() {
      return I18N.create(CommonConstants.class);
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
