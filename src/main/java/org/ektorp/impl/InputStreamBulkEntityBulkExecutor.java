package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.ektorp.DocumentOperationResult;
import org.ektorp.http.InputStreamBulkEntity;
import org.ektorp.http.RestTemplate;
import org.ektorp.http.URI;

public class InputStreamBulkEntityBulkExecutor implements BulkExecutor<InputStream> {

  protected RestTemplate restTemplate;

  protected ObjectMapper objectMapper;

  protected String bulkDocsUri;

  public InputStreamBulkEntityBulkExecutor(URI dbURI, RestTemplate restTemplate,
      ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.bulkDocsUri = dbURI.append("_bulk_docs").toString();
  }

  @Override
  public List<DocumentOperationResult> executeBulk(InputStream inputStream, boolean allOrNothing) {
    return restTemplate.post(
        bulkDocsUri,
        new InputStreamBulkEntity(inputStream, allOrNothing),
        new BulkOperationResponseHandler(objectMapper));
  }


}
