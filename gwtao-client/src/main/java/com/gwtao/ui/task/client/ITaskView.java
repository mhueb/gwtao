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
package com.gwtao.ui.task.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.dialog.client.AsyncOkAnswere;
import com.gwtao.ui.dialog.client.AsyncOkCancelAnswere;

public interface ITaskView extends IsWidget {

  void alert(String title, String message, AsyncOkAnswere answere);

  void confirm(String title, String message, AsyncOkCancelAnswere answere);

}
