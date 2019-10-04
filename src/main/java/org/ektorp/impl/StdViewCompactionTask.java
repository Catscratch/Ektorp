package org.ektorp.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.ViewCompactionTask;

public class StdViewCompactionTask extends StdActiveTask implements ViewCompactionTask {

  private String databaseName;
  private String designDocumentId;

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

  @JsonProperty(required = false, value = "database")
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  @Override
  public String getDesignDocumentId() {
    return designDocumentId;
  }

  @JsonProperty(required = false, value = "design_document")
  public void setDesignDocumentId(String designDocumentId) {
    this.designDocumentId = designDocumentId;
  }

}
