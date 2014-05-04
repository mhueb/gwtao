package com.gwtao.ui.util.client.action;

import com.gwtao.ui.util.client.card.ICard;
import com.gwtao.ui.util.client.card.ICardSupplier;

public class SimpleActionSupplier implements ICardSupplier {

  private final ICard actions;

  public SimpleActionSupplier(ICard actions) {
    this.actions = actions;
  }

  @Override
  public ICard getCard(Object... selection) {
    return actions;
  }
}
