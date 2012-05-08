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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageBag implements IMessageReceiver, IMessageSource, Serializable {
  private static final long serialVersionUID = 1L;

  private List<Message> messages = new ArrayList<Message>();

  public MessageBag() {
  }

  public MessageBag(IMessageSource in) {
    messages.addAll(in.getMessages());
  }

  public List<Message> getMessages() {
    return Collections.unmodifiableList(messages);
  }

  public MessageLevel getWorstLevel() {
    MessageLevel worst = null;
    for (Message msg : messages) {
      if (worst == null || worst.getNr() < msg.getLevel().getNr())
        worst = msg.getLevel();
    }
    return worst;
  }

  public void add(Message msg) {
    messages.add(msg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtaf.common.message.IMessages#clear()
   */
  public void clear() {
    messages.clear();
  }

  public boolean hasMessages() {
    return !messages.isEmpty();
  }

  public void add(IMessageSource msgs) {
    if (msgs != null)
      messages.addAll(msgs.getMessages());
  }

  @Override
  public String toString() {
    return "MessageBag [messages=" + messages + "]";
  }
}
