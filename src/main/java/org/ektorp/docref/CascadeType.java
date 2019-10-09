package org.ektorp.docref;

import java.util.EnumSet;

public enum CascadeType {
  /**
   * All operations are cascaded to the child documents.
   */
  ALL,
  /**
   * Cascades the create and update operations when create(), update(), executeBulk() or
   * executeAllOrNothing() is called.
   */
  SAVE_UPDATE,
  /**
   * Cascades the remove operation to associated entities if delete(), executeBulk() or
   * executeAllOrNothing() is called.
   */
  DELETE,
  /**
   * No operation is cascaded to the child documents.
   */
  NONE;

  public static boolean intersects(CascadeType[] anyOf, EnumSet<CascadeType> types) {
    for (CascadeType ct : anyOf) {
      if (types.contains(ct)) {
        return true;
      }
    }
    return false;
  }

  public static final EnumSet<CascadeType> DELETE_TYPES = EnumSet.of(CascadeType.ALL, CascadeType.DELETE);
  public static final EnumSet<CascadeType> PERSIST_TYPES = EnumSet.of(CascadeType.ALL, CascadeType.SAVE_UPDATE);

}
