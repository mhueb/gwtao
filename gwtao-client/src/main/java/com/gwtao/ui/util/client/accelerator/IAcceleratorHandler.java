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
package com.gwtao.ui.util.client.accelerator;

import com.gwtao.ui.util.client.EventInfo;
import com.gwtao.ui.util.client.KeyInfo;

@Deprecated
public interface IAcceleratorHandler {
  // TODO global registrieren per id mit default-key + description, dann auch manage shortcuts dialog machbar...
  boolean onKey(KeyInfo key, EventInfo eventInfo);
}
