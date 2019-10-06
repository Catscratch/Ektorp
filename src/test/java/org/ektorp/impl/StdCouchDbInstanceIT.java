package org.ektorp.impl;

import static org.junit.Assert.assertEquals;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbContainer;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.MembershipInfo;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;


public class StdCouchDbInstanceIT {

  @ClassRule
  public static GenericContainer couchDb = new CouchDbContainer();

  @Test
  public void testDescribeCluster() throws Exception {
    HttpClient httpClient = new StdHttpClient.Builder()
        .url(((CouchDbContainer)couchDb).getCouchDbUrl())
        .build();

    CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
    CouchDbConnector connector = new StdCouchDbConnector("test", dbInstance);

    connector.createDatabaseIfNotExists();

    try {
      MembershipInfo info = dbInstance.describeCluster();
      assertEquals(1, info.getAllNodes().size());
      assertEquals("nonode@nodhost", info.getAllNodes().get(0));
      assertEquals(1, info.getClusterNodes().size());
      assertEquals("nonode@nodhost", info.getClusterNodes().get(0));
    } catch (DocumentNotFoundException e) {
      e.printStackTrace();
    }
  }

}
