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
}
