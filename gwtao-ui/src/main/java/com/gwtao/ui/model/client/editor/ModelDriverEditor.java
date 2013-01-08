package com.gwtao.ui.model.client.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang.NotImplementedException;

import com.google.gwt.editor.client.EditorDriver;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;

public final class ModelDriverEditor<M> extends AbstractModelEditor<M> {

  private EditorDriver<M> driver;

  public ModelDriverEditor(EditorDriver<M> driver, IServiceAdapter<M> serviceAdapter) {
    super(serviceAdapter);
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
      ((SimpleBeanEditorDriver<M, ?>) driver).edit(getModel());
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
