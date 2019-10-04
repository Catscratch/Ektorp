package org.ektorp.impl;

import java.util.Collection;

public interface JsonSerializer {

  BulkOperation createBulkOperation(final Collection<?> objects,
      final boolean allOrNothing);

  String toJson(Object o);

}