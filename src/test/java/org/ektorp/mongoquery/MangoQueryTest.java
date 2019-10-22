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
  }

  @Test
  public void testMangoQueryBuilder() throws Exception {
    ObjectMapper om = new ObjectMapper();
    MangoQueryBuilder mb = new MangoQueryBuilder();

    Operator op1 = new Operator.EqualOperator("title", "Der Herr der Ringe");
    Operator op2 = new Operator.ExistsOperator("genre", Boolean.TRUE);

    Expression exp = mb.and(op1);
    String result = om.writeValueAsString(exp);
    assertEquals("{\"title\":{\"$eq\":\"Der Herr der Ringe\"}}", result);

    exp = mb.and(op1, op2);
    result = om.writeValueAsString(exp);
    assertEquals("{\"$and\":[{\"title\":{\"$eq\":\"Der Herr der Ringe\"}},{\"genre\":{\"$in\":true}}]}", result);
  }


}
