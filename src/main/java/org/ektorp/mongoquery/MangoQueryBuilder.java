package org.ektorp.mongoquery;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MangoQueryBuilder {

    private Expression expression;

    public MangoQueryBuilder or(Operator... operators) {
        return combinationOperators("$or", operators);
    }

    public MangoQueryBuilder and(Operator... operators) {
        return combinationOperators("$and", operators);
    }

    private MangoQueryBuilder combinationOperators(String oper, Operator... operators) {
        expression = new Expression();
        expression.setLeft(oper);

        if (operators.length > 1) {
            List<Expression> expressions = new LinkedList<>();
            for (Operator operator : operators) {
                Expression e = new Expression();
                e.setLeft(operator.getFieldName());
                e.setRight(operator.getExpression());

                expressions.add(e);
            }
            expression.setRight(expressions);
        } else if (operators.length == 1){
            Expression e = new Expression();
            e.setLeft(operators[0].getFieldName());
            e.setRight(operators[0].getExpression());

            expression = e;
        }

        return this;
    }

    @JsonValue
    public Map<String, Object> toJson() {
        return this.expression.toJson();
    }


}
