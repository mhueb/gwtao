package com.gwtao.app.client;

import com.gwtao.ui.location.client.LocationUtils;
import com.gwtao.ui.model.client.editor.IEditorView;
import com.gwtao.ui.model.client.editor.IModelEditor;
import com.gwtao.ui.model.client.editor.IServiceAdapter;
import com.gwtao.ui.model.client.editor.ModelEditor;
import com.gwtao.ui.model.client.mgr.IViewManager;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.ParameterList;

public abstract class EditorPage<M> extends AbstractPage implements IEditorView {

  private IModelEditor<M> editor;

  protected void initEditor(IViewManager<M> viewMgr,IServiceAdapter<M> serviceAdapter) {
    ModelEditor<M> modelEditor = new ModelEditor<M>(viewMgr,serviceAdapter);
    modelEditor.initView(this);
    this.editor = modelEditor;
  }
  
  public IModelEditor<M> getEditor() {
    return editor;
  }

  @Override
  protected void deferedInit() {
    super.deferedInit();
    start();
  }

  protected void start() {
    ParameterList params = LocationUtils.parse(getCtx().getParameter());
    editor.start(params);
  }

  @Override
  public void alert(String title, String question, AsyncOKAnswere answere) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void confirm(String title, String question, AsyncYESNOAnswere answere) {
    // TODO Auto-generated method stub
    
  }
}
