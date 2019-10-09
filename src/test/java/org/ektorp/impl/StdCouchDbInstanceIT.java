package org.ektorp.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;
import jdk.internal.util.xml.impl.Input;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbContainer;
import org.ektorp.CouchDbInstance;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.Options;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.support.CouchDbDocument;
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

    MembershipInfo info = dbInstance.describeCluster();
    assertEquals(1, info.getAllNodes().size());
    assertEquals("nonode@nodhost", info.getAllNodes().get(0));
    assertEquals(1, info.getClusterNodes().size());
    assertEquals("nonode@nodhost", info.getClusterNodes().get(0));
  }

  @Test
  public void testCreateAttachement() throws Exception {
    HttpClient httpClient = new StdHttpClient.Builder()
        .url(((CouchDbContainer)couchDb).getCouchDbUrl())
        .build();

    CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
    CouchDbConnector connector = new StdCouchDbConnector("test", dbInstance);

    connector.createDatabaseIfNotExists();

    try(InputStream is = getClass().getResourceAsStream("all_docs_result.json")) {
      String contentType = "application/text";

      AttachmentInputStream ais = new AttachmentInputStream("1", is, contentType);
      String revision = connector.createAttachment("1", ais);
      assertNotNull(revision);

      Options options = new Options();
      options.includeConflicts();
      options.includeRevisions();
      options.revision(revision);
      CouchDbDocument document = connector.get(CouchDbDocument.class, "1", options);
      assertNotNull(document);
      assertFalse(document.getAttachments().isEmpty());

      AttachmentInputStream rs = connector.getAttachment("1",
          document.getAttachments().keySet().iterator().next(), revision);
      assertNotNull(rs);

      String result;
      try (Scanner scan = new Scanner(rs, StandardCharsets.UTF_8.name())) {
        result = scan.useDelimiter("\\A").next();
      }

      assertTrue(result.contains("1-2842770487"));
    }

  }
}
