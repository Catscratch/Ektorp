package org.ektorp.util;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class JoinerTest {

  @Test
  public void should_join_string_collection() {
    List<String> src = Arrays.asList("foo", "bar", "baz");
    assertEquals("foo,bar,baz", Joiner.join(src, ","));
  }

  @Test
  public void single_value() {
    List<String> src = Arrays.asList("foo");
    assertEquals("foo", Joiner.join(src, ","));
  }

  @Test
  public void long_separator() {
    List<String> src = Arrays.asList("foo", "bar", "baz");
    assertEquals("foo && bar && baz", Joiner.join(src, " && "));
  }

}
