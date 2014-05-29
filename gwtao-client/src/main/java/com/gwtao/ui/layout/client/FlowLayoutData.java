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
package com.gwtao.ui.layout.client;

public class FlowLayoutData extends LayoutData {
  private float ratio;

  public FlowLayoutData(int minWidth, int minHeight) {
    super(minWidth, minHeight);
  }

  public FlowLayoutData(int minWidth, int minHeight, float ratio) {
    super(minWidth, minHeight);
    this.ratio = ratio;
  }

  public FlowLayoutData(FlowLayoutData o) {
    super(o);
    this.ratio = o.ratio;
  }

  public float getRatio() {
    return ratio;
  }

  public boolean isSizable() {
    return ratio != 0.0f;
  }

}
