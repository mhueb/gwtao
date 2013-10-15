package com.gwtao.ui.cellview.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.AbstractCellTable;

public interface ColumnFactory<V> {

  class Default extends AbstractCell<Object> {
    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, Object value, SafeHtmlBuilder sb) {
    }
  }

  enum Align {
    DEFAULT,
    LEFT,
    CENTER,
    RIGHT;
  }

  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface GenColumn {
    Class<? extends Cell<?>> cellType() default Default.class;

    String title() default "";

    String dataStoreName() default "";

    boolean sortable() default false;

    double width() default 0.0;

    Unit unit() default Unit.PX;

    boolean editable() default false;

    Align align() default Align.DEFAULT;

  }

  void generateColumns(V view, AbstractCellTable<?> grid);
}
