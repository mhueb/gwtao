package com.gwtao.ui.task.client;

import com.google.gwt.core.shared.GWT;
import com.gwtao.ui.task.client.i18n.DataConstants;

public abstract class AsyncSavePerformer<M> implements IAsyncTaskPerformer<M> {
  private static final DataConstants CONSTANTS = GWT.create(DataConstants.class);

  @Override
  public String getDisplayTitle() {
    return CONSTANTS.save();
  }

  @Override
  public String getWaitMessage() {
    return CONSTANTS.saving();
  }
}
