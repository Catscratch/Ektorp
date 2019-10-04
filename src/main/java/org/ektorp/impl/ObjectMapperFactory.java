package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ektorp.CouchDbConnector;

public interface ObjectMapperFactory {

  ObjectMapper createObjectMapper();

  ObjectMapper createObjectMapper(CouchDbConnector connector);

}
