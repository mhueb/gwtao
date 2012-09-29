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
package com.gwtao.utils.shared.permission;

import java.io.Serializable;

public interface IPrivilegeProvider extends Serializable {
  final IPrivilegeProvider ALLOWED = new PrivilegeProviderDefault(Permission.ALLOWED);

  final IPrivilegeProvider UNALLOWED = new PrivilegeProviderDefault(Permission.UNALLOWED);

  final IPrivilegeProvider HIDDEN = new PrivilegeProviderDefault(Permission.HIDDEN);

  Permission getPermission(String privilegeId, Object... data);
}
