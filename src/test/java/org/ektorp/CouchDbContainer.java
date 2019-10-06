package org.ektorp;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class CouchDbContainer extends GenericContainer<CouchDbContainer> {

  private static final int COUCH_DB_PORT = 5984;
  private static final String COUCH_DB_DEFAULT_IMAGE = "couchdb:2.3";

  public CouchDbContainer(String dockerImageName) {
    super(dockerImageName);
  }

  public CouchDbContainer() {
    this(COUCH_DB_DEFAULT_IMAGE);
  }

  @Override
  protected void configure() {
    addExposedPort(COUCH_DB_PORT);
    waitingFor(Wait.forHttp("/_up").forStatusCode(200));
  }

  public String getCouchDbUrl() {
    return String.format("http://%s:%d",
        this.getContainerIpAddress(),
        this.getMappedPort(COUCH_DB_PORT));
  }

}
