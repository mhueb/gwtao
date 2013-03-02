package com.gwtao.app.client;

import com.gwtao.ui.location.client.LocationUtils;
import com.gwtao.ui.task.client.IServiceAdapter;
import com.gwtao.ui.task.client.ITaskController;
import com.gwtao.ui.task.client.ITaskView;
import com.gwtao.ui.task.client.TaskController;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.ParameterList;
import com.gwtao.ui.viewdriver.client.IViewDriver;

public abstract class TaskPage<M> extends AbstractPage implements ITaskView {

  private ITaskController<M> editor;

  protected void initEditor(IViewDriver<M> viewMgr,IServiceAdapter<M> serviceAdapter) {
    TaskController<M> modelEditor = new TaskController<M>(viewMgr,serviceAdapter);
    modelEditor.initView(this);
    this.editor = modelEditor;
  }
  
  public ITaskController<M> getEditor() {
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
