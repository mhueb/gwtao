package com.gwtao.ui.util.client.card;

import org.apache.commons.lang.Validate;

import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.Token;
import com.gwtao.ui.util.client.action.IAction;

public class CardItem implements IDisplayableItem {
  private Card set;
  private IAction action;
  private Token token;
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

  public CardItem(Token token) {
    Validate.notNull(token);
    this.token = token;
    display = this.token;
  }

  @Override
  public String getIcon() {
    return display.getIcon();
  }

  @Override
  public String getTitle() {
    return display.getTitle();
  }

  public String getTooltip() {
    return display.getTooltip();
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

  public Token asToken() {
    Validate.notNull(token);
    return token;
  }

  public ICard asCard() {
    Validate.notNull(set);
    return set;
  }

}
