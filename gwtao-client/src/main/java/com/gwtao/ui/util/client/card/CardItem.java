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
