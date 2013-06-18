package com.gwtao.webapp.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.History;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;
import com.gwtao.ui.dialog.client.MessageDialog;
import com.gwtao.ui.location.client.IParameterConverter;
import com.gwtao.ui.location.client.TokenUtils;
import com.gwtao.ui.task.client.IAsyncDataReader;
import com.gwtao.ui.task.client.IAsyncTaskPerformer;
import com.gwtao.ui.task.client.ITaskController;
import com.gwtao.ui.task.client.ITaskView;
import com.gwtao.ui.task.client.TaskController;
import com.gwtao.ui.task.client.i18n.DataConstants;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;
import com.gwtao.ui.util.client.IDisplayableItem;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.viewdriver.client.IViewDriver;
import com.gwtao.webapp.client.i18n.WebAppConstants;

public abstract class TaskPage<P, M> extends AbstractPage implements ITaskView {
  private static final DataConstants DATA_CONSTS = GWT.create(DataConstants.class);
  private static final WebAppConstants WEBAPP_CONSTS = GWT.create(WebAppConstants.class);

  private TaskController<P, M> editor;
  private IAction performAction;
  private Action refreshAction;
  private Action closeAction;
  private IParameterConverter<P, M> converter;

  protected void initEditor(IViewDriver<M> viewMgr, IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    this.editor = new TaskController<P, M>(viewMgr, converter, reader, performer);
    this.editor.initView(this);
    this.editor.addHandler(new DataChangedEvent.Handler() {

      @Override
      public void onDataChanged() {
        getCtx().updateTitle();
      }
    }, DataChangedEvent.TYPE);

    this.converter = converter;

    performAction = new Action(performer.getDisplayTitle()) {

      @Override
      public void execute(Object... data) {
        editor.perform();
      }
    };

    refreshAction = new Action(DATA_CONSTS.refresh()) {

      @Override
      public void execute(Object... data) {
        editor.refresh();
      }
    };

    closeAction = new Action(WEBAPP_CONSTS.close()) {

      @Override
      public void execute(Object... data) {
        History.back();
      }
    };
  }

  public ITaskController<P, M> getEditor() {
    return editor;
  }

  @Override
  protected void deferedInit() {
    super.deferedInit();
    start();
  }

  protected void start() {
    editor.start(converter.decode(TokenUtils.parse(getCtx().getParameter())));
  }

  @Override
  public void alert(String title, String message, AsyncOKAnswere answere) {
    MessageDialog.alert(title, message, answere);
  }

  @Override
  public void confirm(String title, String message, AsyncYESNOAnswere answere) {
    MessageDialog.confirm(title, message, answere);
  }

  protected IAction getPerformAction() {
    return performAction;
  }

  protected IAction getRefreshAction() {
    return refreshAction;
  }

  protected IAction getCloseAction() {
    return closeAction;
  }
}
