package com.gwtao.ui.location.client;

import com.gwtao.ui.util.client.ParameterList;

public interface IParameterConverter<P, M> {
  P extract(M m);

  P decode(ParameterList parameter);

  void encode(ParameterList.Builder builder, P param);
}
