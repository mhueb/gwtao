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
