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
package com.gwtao.portalapp.client.layout;

import org.apache.commons.lang.StringUtils;

import com.gwtao.portalapp.client.layout.IPortalLayout.Position;

public class PartLayout {
  private final String id;
  private final Position pos;
  private final float ratio;
  private final String refId;

  public PartLayout(String id, Position pos, float ratio, String refId) {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("id=null");
    if (pos == null)
      throw new IllegalArgumentException("pos=null");
    if (ratio != 0.0 && (ratio < 0.01f || ratio > 1.0f))
      throw new IllegalArgumentException("illegal ratio=" + ratio);
    if (StringUtils.isEmpty(refId))
      throw new IllegalArgumentException("refId=null");
    this.id = id;
    this.pos = pos;
    this.ratio = ratio;
    this.refId = refId;
  }

  public String getId() {
    return id;
  }

  public Position getPos() {
    return pos;
  }

  public boolean isHorizontal() {
    return pos == Position.LEFT || pos == Position.RIGHT;
  }

  public boolean isVertical() {
    return pos == Position.TOP || pos == Position.BOTTOM;
  }

  public float getRatio() {
    return ratio;
  }

  public String getRefId() {
    return refId;
  }
}
