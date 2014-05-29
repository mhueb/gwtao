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
package com.gwtao.ui.util.client.theme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThemeManager implements Iterable<ITheme> {

  private List<ITheme> themes = new ArrayList<ITheme>();

  public void registerTheme(ITheme theme) {
    themes.add(theme);
  }

  @Override
  public Iterator<ITheme> iterator() {
    return themes.iterator();
  }
}
