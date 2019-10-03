package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ektorp.DocumentOperationResult;
import org.ektorp.http.HttpResponse;
import org.ektorp.http.StdResponseHandler;
import org.ektorp.util.Assert;

/**
 * Extracts the document revision if the operation was successful
 */
public class RevisionResponseHandler extends StdResponseHandler<DocumentOperationResult> {

  ObjectMapper objectMapper;

  public RevisionResponseHandler(ObjectMapper om) {
    Assert.notNull(om, "ObjectMapper cannot be null");
    objectMapper = om;
  }

  @Override
  public DocumentOperationResult success(HttpResponse hr) throws Exception {
    return objectMapper.readValue(hr.getContent(), DocumentOperationResult.class);
  }
}
