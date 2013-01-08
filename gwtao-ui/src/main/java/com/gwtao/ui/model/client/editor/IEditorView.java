package com.gwtao.ui.model.client.editor;

import com.google.gwt.user.client.ui.IsWidget;
import com.gwtao.ui.util.client.AsyncOKAnswere;
import com.gwtao.ui.util.client.AsyncYESNOAnswere;

public interface IEditorView extends IsWidget{

  void alert(String title, String question, AsyncOKAnswere answere);

  void confirm(String title, String question, AsyncYESNOAnswere answere);

}
