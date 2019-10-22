package org.ektorp.mongoquery;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ektorp.mongoquery.Operator.EqualOperator;
import org.junit.Test;

public class MangoQueryTest {

  @Test
  public void testAndQuery() throws Exception {
    ObjectMapper om = new ObjectMapper();

    Operator operator1 = new EqualOperator("fieldName", "value");
    Operator operator2 = new EqualOperator("fieldName2", "value2");

    assertEquals("{\"fieldName\":{\"$eq\":\"value\"}}", om.writeValueAsString(operator1));
    assertEquals("{\"fieldName2\":{\"$eq\":\"value2\"}}", om.writeValueAsString(operator2));

    MangoQueryBuilder builder = new MangoQueryBuilder();

    Expression expression = builder.and(operator1, operator2);

    assertEquals("{\"$and\":[{\"fieldName\":{\"$eq\":\"value\"}},{\"fieldName2\":{\"$eq\":\"value2\"}}]}", om.writeValueAsString(expression));

//    expression = builder.or(expression, operator1);
  }

}
