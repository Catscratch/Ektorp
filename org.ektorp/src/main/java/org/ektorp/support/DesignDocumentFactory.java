package org.ektorp.support;

import org.ektorp.CouchDbConnector;

public interface DesignDocumentFactory {

  /**
   * Generates a design document with views, lists, shows and filters generated and loaded according
   * to the annotations found in the metaDataSource object.
   */
  DesignDocument generateFrom(Object metaDataSource);

  /**
   * Retrieves the DesignDocument instance from the database
   *
   * @param db               The couch database connector
   * @param designDocumentId The id of the document
   */
  DesignDocument getFromDatabase(CouchDbConnector db, String designDocumentId);

  /**
   * Create a new empty instance of a DesignDocument
   */
  DesignDocument newDesignDocumentInstance();

}