package com.gwtao.ui.task.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;

public interface ITaskView extends IsWidget {

  void alert(String title, String message, AsyncOKAnswere answere);

  void confirm(String title, String message, AsyncYESNOAnswere answere);

}
