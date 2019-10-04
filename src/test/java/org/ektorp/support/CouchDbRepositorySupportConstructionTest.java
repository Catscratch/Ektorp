package org.ektorp.support;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.ektorp.CouchDbConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.stubbing.answers.ThrowsException;


public class CouchDbRepositorySupportConstructionTest {

  CouchDbConnector db;

  @Before
  public void setUp() throws Exception {
    System.clearProperty(DesignDocument.UPDATE_ON_DIFF);
    System.clearProperty(DesignDocument.AUTO_UPDATE_VIEW_ON_CHANGE);

    db = mock(CouchDbConnector.class, new ThrowsException(
        new UnsupportedOperationException("This interaction was not expected on this mock")));
    doNothing().when(db).createDatabaseIfNotExists();
  }

  @After
  public void tearDown() {
    System.clearProperty(DesignDocument.UPDATE_ON_DIFF);
    System.clearProperty(DesignDocument.AUTO_UPDATE_VIEW_ON_CHANGE);
  }

  @Test
  public void given_design_doc_and_not_createIfExists_constructor_should_not_call_createDatabaseIfNotExists() {
    final String designDoc = "my_design_doc";

    new CouchDbRepositorySupport<CouchDbRepositorySupportTest.TestDoc>(
        CouchDbRepositorySupportTest.TestDoc.class, db, designDoc, false);

    verify(db, never()).createDatabaseIfNotExists();
  }

  @Test
  public void given_design_doc_andt_createIfExists_constructor_should_call_createDatabaseIfNotExists() {
    final String designDoc = "my_design_doc";

    new CouchDbRepositorySupport<CouchDbRepositorySupportTest.TestDoc>(
        CouchDbRepositorySupportTest.TestDoc.class, db, designDoc, true);

    verify(db, times(1)).createDatabaseIfNotExists();
  }
}
