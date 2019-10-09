package org.ektorp.mongoquery;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class Operator {

    private String fieldName;
    private Expression expression;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @JsonValue
    public Map<String, Object> toJson() {
        Map<String, Object> outerMap = new HashMap<>();
        outerMap.put(this.fieldName, this.expression);
        return outerMap;
    }

    public static class SimpleCompareOperator extends Operator {

        private String comparator;
        private Object value;

        public String getComparator() {
            return comparator;
        }

        public void setComparator(String comparator) {
            this.comparator = comparator;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public SimpleCompareOperator(String fieldName, String comparator, Object value) {
            this.setFieldName(fieldName);
            Expression exp = new Expression();
            exp.setLeft(comparator);
            exp.setRight(value);
            this.setExpression(exp);
        }
    }

    public static class EqualOperator extends SimpleCompareOperator {

        private static final String COMPARATOR = "$eq";

        public EqualOperator(String fieldName, Object value) {
            super(fieldName, COMPARATOR, value);
        }
    }

    public static class ExistsOperator extends SimpleCompareOperator {

        private static final String COMPARATOR = "$in";

        public ExistsOperator(String fieldName, Boolean value) {
            super(fieldName, COMPARATOR, value);
        }
    }


    public String getFieldName() {
        return fieldName;
    }

    public Expression getExpression() {
        return expression;
    }
}
