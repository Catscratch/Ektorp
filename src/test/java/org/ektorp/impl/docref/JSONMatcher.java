package org.ektorp.impl.docref;

import org.ektorp.util.JSONComparator;
import org.junit.Assert;
import org.mockito.ArgumentMatcher;

public class JSONMatcher extends ArgumentMatcher<String> {

  private final String expectedJSON;

  public JSONMatcher(String expectedJSON) {
    super();
    this.expectedJSON = expectedJSON;
  }

  @Override
  public boolean matches(Object argument) {
    String actualJSON = (String) argument;
    if (!JSONComparator.areEqual(expectedJSON, actualJSON)) {
      Assert.assertEquals(expectedJSON, actualJSON);
      return false;
    }
    return true;

  }

}
