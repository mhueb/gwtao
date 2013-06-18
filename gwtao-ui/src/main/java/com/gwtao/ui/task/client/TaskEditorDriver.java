package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang.NotImplementedException;

import com.google.gwt.editor.client.EditorDriver;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.gwtao.ui.location.client.IParameterConverter;

public final class TaskEditorDriver<P, M> extends AbstractTaskController<P, M> {

  private EditorDriver<M> driver;

  public TaskEditorDriver(EditorDriver<M> driver, IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    super(converter, reader, performer);
    this.driver = driver;
  }

  public final EditorDriver<M> getDriver() {
    return driver;
  }

  public boolean isDirty() {
    return driver.isDirty();
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void onEdit() {
    if (driver instanceof SimpleBeanEditorDriver)
      ((SimpleBeanEditorDriver<M, ?>) driver).edit(getData());
    else
      throw new NotImplementedException();
  }

  protected void handleDriverErrors() {
    // TODO
  }

  @Override
  protected M flush() {
    return driver.flush();
  }

  @Override
  protected boolean hasErrors() {
    return driver.hasErrors();
  }

  @SuppressWarnings({
      "rawtypes",
      "unchecked" })
  @Override
  protected void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    Set raw = constraintViolations;
    driver.setConstraintViolations(raw);
  }

}
