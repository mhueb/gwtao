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
package com.gwtao.ui.portal.client.layout;

import org.apache.commons.lang.StringUtils;

import com.gwtao.ui.portal.client.layout.IPortalLayout.Position;

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
