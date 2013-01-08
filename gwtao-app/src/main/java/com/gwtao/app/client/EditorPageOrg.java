package com.gwtao.app.client;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import com.google.gwt.editor.client.EditorDriver;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.location.client.LocationUtils;
import com.gwtao.ui.util.client.GlassPane;
import com.gwtao.ui.util.client.ParameterList;

public abstract class EditorPageOrg<T> extends AbstractPage {

  private EditorDriver<T> driver;
  private Validator validator;

  void initDriver(EditorDriver<T> driver) {
    Validate.notNull(driver);
    Validate.isTrue(this.driver == null);
    this.driver = driver;
  }

  void initValidator(javax.validation.Validator validator) {
    Validate.notNull(validator);
    Validate.isTrue(this.validator == null);
    this.validator = validator;
  }

  protected final EditorDriver<T> getDriver() {
    return driver;
  }

  @Override
  protected void deferedInit() {
    super.deferedInit();
    start();
  }

  public void start() {
    final GlassPane glass = GlassPane.create(this);
    ParameterList params = LocationUtils.parse(getCtx().getParameter());
    asyncRead(params, new AsyncCallback<T>() {

      @Override
      public void onFailure(Throwable caught) {
        throw new NotImplementedException(caught);
      }

      @Override
      public void onSuccess(T result) {
        glass.remove();
        edit(result);
      }
    });
  }

  public void accept() {
    T model = driver.flush();
    if (!driver.hasErrors() && validate(model)) {
      final GlassPane glass = GlassPane.create(this);
      AsyncCallback<T> callback = new AsyncCallback<T>() {
        @Override
        public void onFailure(Throwable caught) {
          throw new NotImplementedException(caught);
        }

        @Override
        public void onSuccess(T result) {
          glass.remove();
          // if(isCreate())
          // getCtx().switchParameter(parameter)
          edit(result);
        }
      };
      if (isCreate())
        asyncCreate(model, callback);
      else
        asyncUpdate(model, callback);
    }
    else {
      // show this driver.getErrors();
      // prompt for error
    }
  }

  private boolean isCreate() {
    // TODO Auto-generated method stub
    return false;
  }

  @SuppressWarnings({
      "unchecked",
      "rawtypes" })
  private boolean validate(T model) {
    if (validator != null) {
      Set<ConstraintViolation<T>> violations = validator.validate(model);
      if (!violations.isEmpty()) {
        driver.setConstraintViolations((Set) violations);
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  private void edit(T model) {
    getCtx().updateTitle(getTitle(model));
    if (driver instanceof SimpleBeanEditorDriver)
      ((SimpleBeanEditorDriver<T, ?>) driver).edit(model);
    else
      throw new NotImplementedException();
  }

  protected abstract String getTitle(T model);

  protected abstract void asyncCreate(T model, AsyncCallback<T> callback);

  protected abstract void asyncRead(ParameterList params, AsyncCallback<T> callback);

  protected abstract void asyncUpdate(T model, AsyncCallback<T> callback);

  protected abstract void asyncDelete(T model, AsyncCallback<T> callback);
}
