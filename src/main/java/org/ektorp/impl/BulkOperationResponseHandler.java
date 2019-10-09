package org.ektorp.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.ektorp.DocumentOperationResult;
import org.ektorp.http.HttpResponse;
import org.ektorp.http.StdResponseHandler;
import org.ektorp.util.Documents;

public class BulkOperationResponseHandler extends
    StdResponseHandler<List<DocumentOperationResult>> {

  private final ObjectMapper objectMapper;
  private final Collection<?> objects;

  public BulkOperationResponseHandler(ObjectMapper om) {
    this(null, om);
  }

  public BulkOperationResponseHandler(Collection<?> objects, ObjectMapper om) {
    this.objects = objects;
    this.objectMapper = om;
  }

  @Override
  @SuppressFBWarnings(value = "SF_SWITCH_NO_DEFAULT")
  public List<DocumentOperationResult> success(HttpResponse hr)
      throws Exception {
    List<DocumentOperationResult> result = new ArrayList<>();

    try (JsonParser jp = objectMapper.getFactory().createParser(hr.getContent())) {
      Iterator<?> objectsIter = objects == null ? null : objects.iterator();
      while (jp.nextToken() != null) {
        switch (jp.getCurrentToken()) {
          case START_OBJECT:
            jp.nextToken();
            if ("ok".equals(jp.getCurrentName())) {
              jp.nextToken();
              jp.nextToken();
            }
            jp.nextToken();
            String id = jp.getText();
            jp.nextToken();
            String nextField = jp.getCurrentName();
            if ("error".equals(nextField)) {
              result.add(readError(jp, objectsIter, id));
            } else {
              setIdAndRevision(jp, objectsIter, id);
            }
        }
      }
    }
    return result;
  }

  private void setIdAndRevision(JsonParser jp, Iterator<?> objectsIter,
      String id) throws IOException, JsonParseException {
    jp.nextToken();
    String rev = jp.getText();
    if (objectsIter != null) {
      Object o = objectsIter.next();
      if (Documents.getId(o) == null) {
        Documents.setId(o, id);
      }
      Documents.setRevision(o, rev);
    }
  }

  private DocumentOperationResult readError(JsonParser jp,
      Iterator<?> objectsIter,
      String id) throws IOException, JsonParseException {
    jp.nextToken();
    String error = jp.getText();
    jp.nextToken();
    jp.nextToken();
    String reason = jp.getText();
    if (objectsIter != null) {
      objectsIter.next();
    }
    return DocumentOperationResult.newInstance(id, error, reason);
  }
}
