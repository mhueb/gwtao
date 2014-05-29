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
package java.util.regex;

import java.util.Objects;

import org.apache.commons.lang.NotImplementedException;

import com.google.gwt.regexp.shared.RegExp;

public final class Matcher implements MatchResult {
  private RegExp regexp;
  private com.google.gwt.regexp.shared.MatchResult result;
  
  Matcher(String pattern, String text) {
    this.regexp = RegExp.compile(pattern);
    result = regexp.exec(text);
  }
  
  public boolean matches() {
    return result != null;
  }

  public int start() {
    return result.getIndex();
  }

  public int start(int group) {
    throw new NotImplementedException();
  }

  public int end() {
    throw new NotImplementedException();
  }

  public int end(int group) {
    throw new NotImplementedException();
  }

  public String group() {
    return group(0);
  }

  public String group(int group) {
    return result.getGroup(group);
  }

  public int groupCount() {
    return result.getGroupCount();
  }
}
