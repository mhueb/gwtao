package com.gwtao.app.client;

import com.gwtao.ui.layout.client.FlowLayout;
import com.gwtao.ui.layout.client.FlowLayoutData;
import com.gwtao.ui.layout.client.LayoutPanel;

public abstract class SearchPage<T> extends AbstractPage {

  public SearchPage() {
  }

  protected void init(SearchParamView<T> paramView, SearchResultView resultView) {
    LayoutPanel frame = new LayoutPanel(new FlowLayout(false));
    paramView.setLayoutData(new FlowLayoutData(100, 100));
    frame.add(paramView);
    resultView.setLayoutData(new FlowLayoutData(10, 10, 1.0f));
    frame.add(resultView);
    initWidget(frame);
  }

}
