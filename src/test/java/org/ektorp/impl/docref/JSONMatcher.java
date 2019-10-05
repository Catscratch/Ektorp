package org.ektorp.impl.docref;

import org.ektorp.util.JSONComparator;
import org.junit.Assert;
import org.mockito.ArgumentMatcher;

public class JSONMatcher implements ArgumentMatcher<String> {

  private final String expectedJSON;

  public JSONMatcher(String expectedJSON) {
    super();
    this.expectedJSON = expectedJSON;
  }

  @Override
  public boolean matches(String argument) {
    String actualJSON = argument;
    if (!JSONComparator.areEqual(expectedJSON, actualJSON)) {
      Assert.assertEquals(expectedJSON, actualJSON);
      return false;
    }
    return true;

  }

}
