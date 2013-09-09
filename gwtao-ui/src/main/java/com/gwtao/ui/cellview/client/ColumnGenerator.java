package com.gwtao.ui.cellview.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public interface ColumnGenerator<V> {

  public final class Default extends AbstractCell<Object> {
    public Default() {
      
    }
    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, Object value, SafeHtmlBuilder sb) {
    }
  }

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface GenColumn {
    Class<? extends Cell<?>> cellType() default Default.class;

    String title() default "";
    boolean sortable() default false;
  }

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface GenGrid {
  }

  void generateColumns(V view);
}
