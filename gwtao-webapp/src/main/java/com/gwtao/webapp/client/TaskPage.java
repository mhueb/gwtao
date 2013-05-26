package com.gwtao.webapp.client;

import com.google.gwt.user.client.History;
import com.gwtao.ui.dialog.client.MessageDialog;
import com.gwtao.ui.location.client.LocationUtils;
import com.gwtao.ui.task.client.IServiceAdapter;
import com.gwtao.ui.task.client.ITaskController;
import com.gwtao.ui.task.client.ITaskView;
import com.gwtao.ui.task.client.TaskController;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.ParameterList;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.viewdriver.client.IViewDriver;

public abstract class TaskPage<M> extends AbstractPage implements ITaskView {

  private ITaskController<M> editor;
  private IAction execAction;
  private Action refreshAction;
  private Action closeAction;

  protected void initEditor(IViewDriver<M> viewMgr, IServiceAdapter<M> serviceAdapter) {
    TaskController<M> modelEditor = new TaskController<M>(viewMgr, serviceAdapter);
    modelEditor.initView(this);
    this.editor = modelEditor;

    execAction = new Action("Save") {

      @Override
      public void execute(Object... data) {
        editor.execute();
      }
    };

    refreshAction = new Action("Refresh") {

      @Override
      public void execute(Object... data) {
        editor.refresh();
      }
    };

    closeAction = new Action("Close") {

      @Override
      public void execute(Object... data) {
        History.back();
      }
    };
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
  public void alert(String title, String message, AsyncOKAnswere answere) {
    MessageDialog.alert(title, message, answere);
  }

  @Override
  public void confirm(String title, String message, AsyncYESNOAnswere answere) {
    MessageDialog.confirm(title, message, answere);
  }

  protected IAction getExecuteAction() {
    return execAction;
  }

  protected IAction getRefreshAction() {
    return refreshAction;
  }

  protected IAction getCloseAction() {
    return closeAction;
  }
}
