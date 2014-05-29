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
package com.gwtao.ui.util.client.card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.gwtao.ui.util.client.NavigationItem;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.displayable.DisplayableItem;
import com.gwtao.ui.util.client.displayable.IDisplayableItem;

/**
 * A set of displayable actions, organized in groups of cards
 * 
 * @author Matt
 * 
 */
public class Card implements ICard {
  private static final IDisplayableItem ROOT = new DisplayableItem("ROOT");

  private IDisplayableItem display;
  private List<CardItem> list = new ArrayList<CardItem>();

  public Card() {
    this(ROOT);
  }

  public Card(IDisplayableItem display) {
    Validate.notNull(display);
    this.display = display;
  }

  public void add(Card actions) {
    add(new CardItem(actions));
  }

  public void add(CardItem item) {
    this.list.add(item);
  }

  public void add(IAction action) {
    add(new CardItem(action));
  }

  public void add(NavigationItem token) {
    add(new CardItem(token));
  }

  @Override
  public String getDisplayIcon() {
    return display.getDisplayIcon();
  }

  @Override
  public String getDisplayText() {
    return display.getDisplayText();
  }

  public String getDisplayTooltip() {
    return display.getDisplayTooltip();
  }

  @Override
  public Iterator<CardItem> iterator() {
    return list.iterator();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

}
