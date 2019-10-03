package org.ektorp.impl;

import org.ektorp.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperFactory {

	ObjectMapper createObjectMapper();

	ObjectMapper createObjectMapper(CouchDbConnector connector);

}
