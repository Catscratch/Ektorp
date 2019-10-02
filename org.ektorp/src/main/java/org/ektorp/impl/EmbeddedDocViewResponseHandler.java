package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.ektorp.http.HttpResponse;
import org.ektorp.http.StdResponseHandler;
import org.ektorp.util.Assert;

/**
 * Reads view result and extracts documents and maps them to the specified type.
 */
public class EmbeddedDocViewResponseHandler<T> extends
    StdResponseHandler<List<T>> {

  private QueryResultParser<T> parser;

  public EmbeddedDocViewResponseHandler(Class<T> docType, ObjectMapper om) {
    Assert.notNull(om, "ObjectMapper may not be null");
    Assert.notNull(docType, "docType may not be null");
    parser = new QueryResultParser<T>(docType, om);
  }

  public EmbeddedDocViewResponseHandler(Class<T> docType, ObjectMapper om,
      boolean ignoreNotFound) {
    Assert.notNull(om, "ObjectMapper may not be null");
    Assert.notNull(docType, "docType may not be null");
    parser = new QueryResultParser<T>(docType, om);
    parser.setIgnoreNotFound(ignoreNotFound);
  }

  @Override
  public List<T> success(HttpResponse hr) throws Exception {
    parser.parseResult(hr.getContent());
    return parser.getRows();
  }

}
