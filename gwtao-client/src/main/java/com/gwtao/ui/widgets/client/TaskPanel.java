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
package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.gwtao.ui.util.client.GWTUtils;
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
    setElement(GWTUtils.createDiv());
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
    title.setInnerText(set.getDisplayText());

    root.appendChild(title);

    Element itemGroup = DOM.createDiv();
    itemGroup.setClassName(styleName + "-group");
    root.appendChild(itemGroup);

    for (CardItem item : set) {
      if (item.isToken()) {
        AnchorElement link = AnchorElement.as(DOM.createAnchor());
        link.setClassName(styleName + "-item");
        link.setHref("#" + item.asToken().getToken());
        link.setInnerText(item.getDisplayText());
        itemGroup.appendChild(link);
        Element gap = DOM.createDiv();
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
