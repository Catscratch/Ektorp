package org.ektorp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;

public class ReflectionUtilsTest {

  TestDocument testDoc_1 = new TestDocument();

  @Before
  public void setup() {
    testDoc_1.setId("id_1");
    testDoc_1.setRevision("rev_1");
  }

  @Test
  public void testFindMethod() {
    Method m = ReflectionUtils.findMethod(TestDocument.class, "getName");
    assertNotNull(m);
    assertEquals("getName", m.getName());
  }

  public static class TestDocument {

    private String id;
    private String revision;
    private String name;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getRevision() {
      return revision;
    }

    public void setRevision(String revision) {
      this.revision = revision;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
