package com.gwtao.ui.taskpanel.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.gwtao.ui.util.client.card.CardItem;
import com.gwtao.ui.util.client.card.Card;
import com.gwtao.ui.util.client.card.ICardSupplier;
import com.gwtao.ui.util.client.card.ICard;

/**
 * Displays a list of tasks to choose and start - mru auf erster ebene - favoriten, "sticking" *
 * 
 * @author Matt
 * 
 */
public class TaskPanel extends ComplexPanel {
  private final ICardSupplier supplier;

  public TaskPanel(ICardSupplier supplier) {
    this.supplier = supplier;
    setElement(DOM.createDiv());
    update();
  }

  public void update() {
    clear();

    boolean containsItem = false;
    ICard actions = supplier.getCard();
    for (CardItem item : actions) {
      containsItem |= !item.isCard();
      if (item.isCard()) {
        add(new TaskPanelEntry(item.asCard()), getElement());
      }
    }

    if (containsItem) {
      Card rootSet = new Card(actions);
      for (CardItem item : actions) {
        if (!item.isCard()) {
          rootSet.add(item);
        }
        add(new TaskPanelEntry(rootSet), getElement());
      }
    }
  }
}
