package org.ektorp.impl;

import java.util.List;
import org.ektorp.DocumentOperationResult;

public interface BulkExecutor<T> {

  List<DocumentOperationResult> executeBulk(T bulk, boolean allOrNothing);
}
