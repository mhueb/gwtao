package com.gwtao.cdi.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {
    ElementType.METHOD,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Inject {

}
