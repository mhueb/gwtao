package com.gwtao.ui.model.client.editor;

import javax.validation.Validator;

import com.gwtao.ui.model.client.editor.AbstractModelEditor.State;
import com.gwtao.ui.model.client.source.IModelSource;
import com.gwtao.ui.util.client.ParameterList;

public interface IModelEditor<M> extends IModelSource<M> {

  public Validator getValidator();

  public State getState();

  public boolean isCreate();

  public boolean isDirty();

  public void start(ParameterList param);

  public void save();

  public void refresh();

  public void revert();

  public void remove();

}