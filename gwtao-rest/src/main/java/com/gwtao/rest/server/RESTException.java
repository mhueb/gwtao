package com.gwtao.rest.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shu4j.utils.message.ErrorMessage;

public class RESTException extends WebApplicationException {
  private static final long serialVersionUID = 1L;

  public RESTException(Status status, int errorCode) {
    this(status, new ErrorMessage(errorCode));
  }

  public RESTException(Status status, int errorCode, String message) {
    this(status, new ErrorMessage(errorCode, message));
  }

  public RESTException(Status status, ErrorMessage entity) {
    super(Response.status(status).entity(entity).type(MediaType.APPLICATION_JSON).build());
  }
}
