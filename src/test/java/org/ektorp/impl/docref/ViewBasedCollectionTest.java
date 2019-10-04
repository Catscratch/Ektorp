package org.ektorp.impl.docref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.util.ClassUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.docref.CascadeType;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.util.Exceptions;
import org.junit.Before;
import org.junit.Test;


public class ViewBasedCollectionTest {

  ViewBasedCollection collectionHandler;
  CouchDbConnector db = mock(CouchDbConnector.class);
  ConstructibleAnnotatedCollection cac = mock(ConstructibleAnnotatedCollection.class);

  TestChildType child1 = new TestChildType("child1", "rev");
  TestChildType child2 = new TestChildType("child2", "rev");

  Set<TestChildType> proxy;

  @Test
  @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
  public void given_cascadeType_NONE_then_removed_element_should_not_be_added_to_pending_removal_list() {
    setupHandlerAndProxy(getRefsWithCascadeNone());
    proxy.size();
    proxy.remove(child1);
    assertTrue(collectionHandler.getPendingRemoval().isEmpty());
  }

  @Test
  @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
  public void given_cascadeType_ALL_then_removed_element_should_be_added_to_pending_removal_list() {
    setupHandlerAndProxy(getRefsWithCascadeAll());
    proxy.size();
    proxy.remove(child1);
    assertEquals(1, collectionHandler.getPendingRemoval().size());
  }

  @Before
  public void setUp() throws Exception {

    Constructor<Collection<Object>> ctor = findCtor(LinkedHashSet.class);
    when(cac.getConstructor()).thenReturn(ctor);

    setupViewResponse();
  }

  @SuppressFBWarnings("unchecked")
  private void setupHandlerAndProxy(DocumentReferences cascadeNone) {
    try {
      collectionHandler = new ViewBasedCollection("test", db, TestType.class, cascadeNone, cac);
    } catch (Exception e) {
      throw Exceptions.propagate(e);
    }
    Object o = Proxy.newProxyInstance(getClass().getClassLoader(),
        new Class[]{Set.class}, collectionHandler);
    proxy = (Set<TestChildType>) o;
  }

  @SuppressFBWarnings("unchecked")
  private void setupViewResponse() {
    List<TestChildType> result = Arrays.asList(child1, child2);
    when(db.queryView(any(ViewQuery.class), any(Class.class))).thenReturn(result);
  }

  private DocumentReferences getRefsWithCascadeNone() {
    try {
      return TestType.class.getField("cascadeNone").getAnnotation(DocumentReferences.class);
    } catch (Exception e) {
      throw Exceptions.propagate(e);
    }
  }

  private DocumentReferences getRefsWithCascadeAll() {
    try {
      return TestType.class.getField("cascadeAll").getAnnotation(DocumentReferences.class);
    } catch (Exception e) {
      throw Exceptions.propagate(e);
    }
  }

  @SuppressFBWarnings("unchecked")
  private Constructor<Collection<Object>> findCtor(Class<?> clazz) {
    return ClassUtil.findConstructor(
        (Class<Collection<Object>>) clazz, true);
  }

  @SuppressFBWarnings("serial")
  static class TestType extends CouchDbDocument {

    @DocumentReferences(backReference = "parentId", cascade = CascadeType.NONE)
    public Set<TestChildType> cascadeNone;

    @DocumentReferences(backReference = "parentId", cascade = CascadeType.ALL)
    public Set<TestChildType> cascadeAll;
  }

  @SuppressFBWarnings("serial")
  static class TestChildType extends CouchDbDocument {

    String parentId;

    public TestChildType(String id, String rev) {
      setId(id);
      setRevision(rev);
    }
  }

}
