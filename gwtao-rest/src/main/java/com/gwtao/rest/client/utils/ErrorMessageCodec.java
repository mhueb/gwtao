package com.gwtao.rest.client.utils;

import org.fusesource.restygwt.client.JsonEncoderDecoder;
import org.fusesource.restygwt.client.JsonEncoderDecoder.DecodingException;
import org.fusesource.restygwt.client.Method;
import org.shu4j.utils.message.ErrorMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ErrorMessageCodec {
  public interface Codec extends JsonEncoderDecoder<ErrorMessage> {
  }

  private static Codec CODEC = GWT.create(Codec.class);

  public static ErrorMessage decode(Method method) {
    return decode(method.getResponse().getText());
  }

  public static ErrorMessage decode(String raw) {
    try {
      JSONValue json = JSONParser.parseStrict(raw);
      return CODEC.decode(json);
    }
    catch (DecodingException | JSONException e) {
      return null;
    }
  }
}
