package com.gwtao.ui.location.client;

public interface HasTokenConverter<P, M> extends HasTokenName {

  IParameterConverter<P, M> getConverter();

}
