package org.ektorp.mongoquery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeserializeTest {

    @Test
    public void testFormat() throws Exception {
        ObjectMapper om = new ObjectMapper();

        Expression op = new Expression();
        op.setLeft("$eq");

        op.setRight("value");
        String result = om.writeValueAsString(op);
        assertEquals("{\"$eq\":\"value\"}", result);

        op.setRight(4711);
        result = om.writeValueAsString(op);
        assertEquals("{\"$eq\":4711}", result);

        op.setLeft("director");
        Expression opIn = new Expression();
        opIn.setLeft("$eq");
        opIn.setRight("Lars von Trier");
        op.setRight(opIn);
        result = om.writeValueAsString(op);
        assertEquals("{\"director\":{\"$eq\":\"Lars von Trier\"}}", result);

        op.setLeft("genre");
        opIn.setLeft("$elemMatch");
        Expression opInIn = new Expression();
        opInIn.setLeft("$eq");
        opInIn.setRight("Horror");
        opIn.setRight(opInIn);
        op.setRight(opIn);
        result = om.writeValueAsString(op);
        assertEquals("{\"genre\":{\"$elemMatch\":{\"$eq\":\"Horror\"}}}", result);
    }

    @Test
    public void testMangoQueryBuilder() throws Exception {
        ObjectMapper om = new ObjectMapper();
        MangoQueryBuilder mb = new MangoQueryBuilder();

        Operator op1 = new Operator.EqualOperator("title", "Der Herr der Ringe");
        Operator op2 = new Operator.ExistsOperator("genre", Boolean.TRUE);

        mb.and(op1);
        String result = om.writeValueAsString(mb);
        assertEquals("{\"title\":{\"$eq\":\"Der Herr der Ringe\"}}", result);

        mb.and(op1, op2);
        result = om.writeValueAsString(mb);
        assertEquals("{\"$and\":[{\"title\":{\"$eq\":\"Der Herr der Ringe\"}},{\"genre\":{\"$in\":true}}]}", result);
    }
}
