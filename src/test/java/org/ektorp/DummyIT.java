package org.ektorp;

import static org.junit.Assert.assertTrue;

import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class DummyIT {

  @ClassRule
  public static GenericContainer couchDb = new CouchDbContainer();

  @Test
  public void testDummy() throws Exception {
    HttpClient httpClient = new StdHttpClient.Builder()
        .url(((CouchDbContainer)couchDb).getCouchDbUrl())
        .build();

    CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
    CouchDbConnector connector = new StdCouchDbConnector("test", dbInstance);

    connector.createDatabaseIfNotExists();

    try {

//      dbInstance.setConfiguration("query_server_config", "reduce_limit", "false");

    } catch (DocumentNotFoundException e) {
      e.printStackTrace();
//      assertTrue(false);
    }


  }

}
