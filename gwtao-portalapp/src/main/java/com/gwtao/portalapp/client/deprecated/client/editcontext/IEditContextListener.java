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
package com.gwtao.portalapp.client.deprecated.client.editcontext;

import org.shu4j.utils.message.IMessageSource;


public interface IEditContextListener {
  void onStateChange();

  void onDataStateChange();

  void onSetData();

  void onStartPre();

  void onRefreshPre();

  void onCheckInPre();

  void onCheckOutPre();

  void onRevertPre();

  void onStartPost();

  void onRefreshPost();

  void onCheckInPost();

  void onCheckOutPost();

  void onRevertPost();

  void onServiceFailed();

  void onServicePost();

  void onValidatePost(IMessageSource msgs);
}
