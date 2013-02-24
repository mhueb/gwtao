package com.gwtao.ui.viewdriver.client.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.Widget;

public interface IFieldAdapter<M, F extends Widget> {

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.TYPE })
  @interface WidgetType {
    Class<? extends Widget>[] value();
  }

  void init(F field);

  void setPermission(Permission perm);

  void updateView(M model);

  void updateModel(M model);

  boolean isDirty();

  void clearDirty();

  F getWidget();
}
