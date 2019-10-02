package org.ektorp.support;

import java.util.List;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;

public interface GenericRepository<T> {

  /**
   * @throws UpdateConflictException if there was an update conflict.
   */
  void add(T entity);

  /**
   * @throws UpdateConflictException if there was an update conflict.
   */
  void update(T entity);

  /**
   * @throws UpdateConflictException if there was an update conflict.
   */
  void remove(T entity);

  /**
   * @throws DocumentNotFoundException if the document was not found.
   */
  T get(String id);

  /**
   * @throws DocumentNotFoundException if the document was not found.
   */
  T get(String id, String rev);

  /**
   * @return empty list if nothing was found.
   */
  List<T> getAll();

  /**
   * @return true if a document with the specified id exists in the database.
   */
  boolean contains(String docId);
}
