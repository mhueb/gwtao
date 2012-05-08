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
package com.gwtao.common.shared.message;

import java.io.Serializable;

public final class Message implements Serializable {
  private static final long serialVersionUID = 1L;

  private String msg;
  
  private MessageLevel level;

  public Message() {
  }

  public Message(String msg, MessageLevel level) {
    this.msg = msg;
    this.level = level;
  }

  public String getMsg() {
    return msg;
  }

  public MessageLevel getLevel() {
    return level;
  }

  @Override
  public String toString() {
    return "Message [level=" + level + ", msg=" + msg + "]";
  }
}
