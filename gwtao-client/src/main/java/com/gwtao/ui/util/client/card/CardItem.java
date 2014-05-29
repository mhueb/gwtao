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

import org.apache.commons.lang.Validate;

import com.gwtao.ui.util.client.NavigationItem;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.displayable.IDisplayableItem;

public class CardItem implements IDisplayableItem {
  private Card set;
  private IAction action;
  private NavigationItem token;
  private IDisplayableItem display;

  public CardItem(Card card) {
    Validate.notNull(card);
    this.set = card;
    display = this.set;
  }

  public CardItem(IAction action) {
    Validate.notNull(action);
    this.action = action;
    display = this.action;
  }

  public CardItem(NavigationItem token) {
    Validate.notNull(token);
    this.token = token;
    display = this.token;
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

  public boolean isAction() {
    return action != null;
  }

  public boolean isToken() {
    return token != null;
  }
  
  public boolean isCard() {
    return set != null;
  }

  public IAction asAction() {
    Validate.notNull(action);
    return action;
  }

  public NavigationItem asToken() {
    Validate.notNull(token);
    return token;
  }

  public ICard asCard() {
    Validate.notNull(set);
    return set;
  }

}
