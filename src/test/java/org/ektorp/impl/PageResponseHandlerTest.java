package org.ektorp.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.ektorp.Page;
import org.ektorp.PageRequest;
import org.ektorp.support.CouchDbDocument;
import org.junit.Test;

public class PageResponseHandlerTest {

  PageResponseHandler<TestDoc> handler;

  @Test
  public void given_view_result_size_is_smaller_than_page_size_plus_one_then_next_link_should_not_exist()
      throws Exception {
    handler = new PageResponseHandler<TestDoc>(PageRequest.firstPage(5), TestDoc.class,
        new ObjectMapper());
    Page<TestDoc> page = handler
        .success(ResponseOnFileStub.newInstance(200, "offset_view_result.json"));
    assertEquals(5, page.size());
    assertFalse(page.isHasNext());
  }

  @Test
  public void given_view_result_size_is_equal_to_page_size_plus_one_then_next_link_should_exist()
      throws Exception {
    handler = new PageResponseHandler<TestDoc>(PageRequest.firstPage(4), TestDoc.class,
        new ObjectMapper());
    Page<TestDoc> page = handler
        .success(ResponseOnFileStub.newInstance(200, "offset_view_result.json"));
    assertEquals(4, page.size());
    assertTrue(page.isHasNext());
    assertEquals("dcdaf08242a4be7da1a36e25f4f0b022", page.getNextPageRequest().getStartKeyDocId());
  }

  @Test
  public void given_previous_page_request_has_been_set_then_hasPrevious_should_be_true()
      throws Exception {
    PageRequest pr = PageRequest.firstPage(5).nextRequest("key", "docId").page(1).build();
    handler = new PageResponseHandler<TestDoc>(pr, TestDoc.class, new ObjectMapper());

    Page<TestDoc> page = handler
        .success(ResponseOnFileStub.newInstance(200, "offset_view_result.json"));
    assertTrue(page.isHasPrevious());
  }

  @SuppressFBWarnings("serial")
  public static class TestDoc extends CouchDbDocument {

    private String name;
    private int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public int getAge() {
      return age;
    }
  }

}
