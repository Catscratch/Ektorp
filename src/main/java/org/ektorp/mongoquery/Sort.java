package org.ektorp.mongoquery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Sort implements Serializable {

  public enum SortOrder {
    DESC("desc"),
    ASC("asc");

    private String jsonName;

    SortOrder(String jsonName) {
      this.jsonName = jsonName;
    }

    public String getJsonName() {
      return this.jsonName;
    }
  }

  private String fieldName;

  private SortOrder sortOrder;

  public Sort(String fieldName, SortOrder sortOrder) {
    this.fieldName = fieldName;
    this.sortOrder = sortOrder;
  }

  public Sort(String fieldName) {
    this(fieldName, SortOrder.ASC);
  }

  public Map<String, String> toJson() {
    Map<String, String> result = new HashMap<>();
    result.put(fieldName, sortOrder.getJsonName());
    return result;
  }

}
