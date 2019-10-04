package org.ektorp.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.ektorp.util.JSONComparator;
import org.junit.Test;

public class OpenCouchDbDocumentTest {

  @SuppressFBWarnings("unchecked")
  @Test
  public void testAnonymous() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String orgJson = IOUtils.toString(getClass().getResourceAsStream("open_doc.json"), "UTF-8");
    FlexDoc d = mapper.readValue(orgJson, FlexDoc.class);
    assertEquals("doc_id", d.getId());
    assertEquals("unknown", d.getAnonymous().get("mysteryStringField"));
    assertEquals(12345, d.getAnonymous().get("mysteryIntegerField"));

    Map<String, Object> mysteryObject = (Map<String, Object>) d.getAnonymous()
        .get("mysteryObjectField");
    assertEquals("foo", mysteryObject.get("nestedField1"));
    assertEquals("bar", mysteryObject.get("nestedField2"));

    List<String> mysteryList = (List<String>) d.getAnonymous().get("mysteryArrayField");
    assertEquals(3, mysteryList.size());
    assertEquals("a1", mysteryList.get(0));

    String newJson = mapper.writeValueAsString(d);

    assertTrue(JSONComparator.areEqual(newJson, orgJson));
  }

  @SuppressFBWarnings("serial")
  static class FlexDoc extends OpenCouchDbDocument {

    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

  }
}
