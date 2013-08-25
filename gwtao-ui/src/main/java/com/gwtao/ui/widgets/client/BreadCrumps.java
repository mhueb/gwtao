package com.gwtao.ui.widgets.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.card.ICardSupplier;

public class BreadCrumps extends ComplexPanel {
  interface IBreadCrumb extends IAction {
    ICardSupplier getCard();
  }

  public static abstract class BreadCrumb extends Action implements IBreadCrumb {

    public BreadCrumb() {
      super();
    }

    public BreadCrumb(IDisplayableItem item) {
      super(item);
    }

    public BreadCrumb(String title) {
      super(title);
    }

    @Override
    public ICardSupplier getCard() {
      return null;
    }
  }

  public BreadCrumps() {
    setElement(DOM.createDiv());
    setStylePrimaryName("gwtao_breadcrumbs");
  }

  public void set(IBreadCrumb... breadCrumbs) {
    set(Arrays.asList(breadCrumbs));
  }

  public void set(Iterable<? extends IBreadCrumb> breadCrumbs) {
    clear();
    for (IBreadCrumb breadCrumb : breadCrumbs)
      add(breadCrumb);
  }

  private void add(IBreadCrumb breadCrumb) {
    Button button = new Button();
    button.setStylePrimaryName("gwtao_breadcrumb");
    button.setText(breadCrumb.getDisplayTitle());
    super.insert(button, getElement(), getWidgetCount(), true);
  }

  public void remove() {
    List<IBreadCrumb> emptyList = Collections.emptyList();
    set(emptyList);
  }
}
