package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import org.apache.http.HttpEntity;
import org.ektorp.DocumentOperationResult;
import org.ektorp.http.JacksonableEntity;
import org.ektorp.http.RestTemplate;
import org.ektorp.http.URI;

/**
 * This is designed to replace the BulkOperationCollectionBulkExecutor
 */
public class EntityCollectionBulkExecutor implements BulkExecutor<Collection<?>> {

  protected URI dbURI;

  protected RestTemplate restTemplate;

  protected ObjectMapper objectMapper;

  public EntityCollectionBulkExecutor() {

  }

  public EntityCollectionBulkExecutor(URI dbURI, RestTemplate restTemplate,
      ObjectMapper objectMapper) {
    this.dbURI = dbURI;
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<DocumentOperationResult> executeBulk(Collection<?> objects, boolean allOrNothing) {
    BulkDocumentBean<?> bulkDocumentBean = new BulkDocumentBean(objects, allOrNothing);
    return restTemplate.post(
        dbURI.append("_bulk_docs").toString(),
        createHttpEntity(bulkDocumentBean),
        new BulkOperationResponseHandler(objects, objectMapper));
  }

  protected HttpEntity createHttpEntity(Object o) {
    return new JacksonableEntity(o, objectMapper);
  }

}
