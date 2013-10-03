package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.gwtao.ui.util.client.action.SimpleActionSupplier;
import com.gwtao.ui.util.client.card.Card;
import com.gwtao.ui.util.client.card.CardItem;
import com.gwtao.ui.util.client.card.ICard;
import com.gwtao.ui.util.client.card.ICardSupplier;

/**
 * Displays a list of tasks to choose and start<br/>
 * TODO mru auf erster ebene - favoriten, "sticking" *
 * 
 * @author Matt
 * 
 */
public class TaskPanel extends ComplexPanel {
  private ICardSupplier supplier;

  public TaskPanel() {
    setElement(DOM.createDiv());
    setStylePrimaryName("gwtao-taskpanel");
  }

  public void update() {
    clear();

    if (supplier == null)
      return;

    ICard actions = supplier.getCard();
    Card rootSet = new Card(actions);

    for (CardItem item : actions) {
      if (item.isCard())
        getElement().appendChild(generateSet(item.asCard()));
      else
        rootSet.add(item);
    }

    if (!rootSet.isEmpty())
      getElement().appendChild(generateSet(rootSet));
  }

  private Element generateSet(ICard set) {
    Element root = DOM.createDiv();
    String styleName = getStyleName();
    root.setClassName(styleName + "-entry");

    Element title = DOM.createDiv();
    title.setClassName(styleName + "-title");
    title.setInnerText(set.getDisplayTitle());

    root.appendChild(title);

    Element itemGroup = DOM.createDiv();
    itemGroup.setClassName(styleName + "-group");
    root.appendChild(itemGroup);

    for (CardItem item : set) {
      if (item.isToken()) {
        AnchorElement link = AnchorElement.as(DOM.createAnchor());
        link.setClassName(styleName + "-item");
        link.setHref("#" + item.asToken().getToken());
        link.setInnerText(item.getDisplayTitle());
        itemGroup.appendChild(link);
        Element gap = DOM.createSpan();
        gap.setClassName(styleName + "-gap");
        gap.setInnerHTML("&nbsp;");
        itemGroup.appendChild(gap);
      }
    }

    return root;
  }

  public void setActions(SimpleActionSupplier supplier) {
    this.supplier = supplier;
    update();
  }
}
