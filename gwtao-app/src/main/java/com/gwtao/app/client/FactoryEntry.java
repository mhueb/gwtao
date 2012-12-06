package com.gwtao.app.client;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface FactoryEntry {

  String token();

  Class<? extends IDocument> doc();
}
