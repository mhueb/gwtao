package com.gwtao.ui.model.client.adapter;

import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractFieldAdapter<M, T, F extends Widget> implements IFieldAdapter<M, F> {

  private Integer dirtyHash;

  public abstract T getValue();

  public abstract void setValue(T value);

  @Override
  public void clearDirty() {
    T value = getValue();
    dirtyHash = value == null ? null : value.hashCode();
  }

  @Override
  public boolean isDirty() {
    T value = getValue();
    Integer currentHash = value == null ? null : value.hashCode();
    return dirtyHash != currentHash || dirtyHash != null && currentHash != null && !dirtyHash.equals(currentHash);
  }

}
