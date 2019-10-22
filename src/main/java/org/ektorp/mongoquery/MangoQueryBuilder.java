package org.ektorp.mongoquery;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MangoQueryBuilder {


    public Expression or(Operator... operators) {
        return combinationOperators("$or", operators);
    }

    public Expression and(Operator... operators) {
        return combinationOperators("$and", operators);
    }

    private Expression combinationOperators(String oper, Operator... operators) {
        Expression expression = new Expression();
        expression.setLeft(oper);

        if (operators.length > 1) {
            List<Expression> expressions = new LinkedList<>();
            for (Operator operator : operators) {
                Expression e = new Expression();
                e.setLeft(operator.getFieldName());
                e.setRight(operator.getExpression());

                expressions.add(e);
            }
            expression.setRight((Serializable) expressions);
        } else if (operators.length == 1){
            expression = new Expression();
            expression.setLeft(operators[0].getFieldName());
            expression.setRight(operators[0].getExpression());
        }

        return expression;
    }


}
