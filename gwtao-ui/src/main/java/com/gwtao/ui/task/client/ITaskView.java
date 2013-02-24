package com.gwtao.ui.task.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;

public interface ITaskView extends IsWidget{

  void alert(String title, String question, AsyncOKAnswere answere);

  void confirm(String title, String question, AsyncYESNOAnswere answere);

}
