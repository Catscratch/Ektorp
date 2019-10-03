package org.ektorp.dataload;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.ektorp.CouchDbConnector;
import org.ektorp.util.Assert;
import org.ektorp.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for DataLoaders
 */
public class DefaultDataLoader {

  private final static Logger LOG = LoggerFactory.getLogger(DefaultDataLoader.class);

  private final ObjectMapper objectMapper;
  protected final CouchDbConnector db;

  public DefaultDataLoader(CouchDbConnector db) {
    this(db, new ObjectMapper());
  }

  public DefaultDataLoader(CouchDbConnector db, ObjectMapper objectMapper) {
    Assert.notNull(db, "CouchDbConnector cannot be null");
    Assert.notNull(objectMapper, "ObjectMapper cannot be null");
    this.db = db;
    this.objectMapper = objectMapper;
  }

  /**
   * Reads documents from the reader and stores them in the database.
   */
  public void load(Reader in) {
    try {
      doLoad(in);
    } catch (Exception e) {
      throw Exceptions.propagate(e);
    }
  }

  private void doLoad(Reader in) throws IOException, JsonParseException,
      JsonMappingException {
    Set<String> allIds = new HashSet<String>(db.getAllDocIds());
    JsonNode jn = objectMapper.readValue(in, JsonNode.class);

    for (Iterator<JsonNode> i = jn.elements(); i.hasNext(); ) {
      JsonNode n = i.next();
      String id = n.get("_id").textValue();
      if (!allIds.contains(id)) {
        LOG.info("adding {} to database", id);
        createDocument(n, id);
      }
    }
  }

  /**
   * Can be overidden in order to customize document creation.
   */
  protected void createDocument(JsonNode n, String id) {
    db.create(id, n);
  }
}
