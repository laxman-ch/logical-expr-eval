package com.expr.bool;

import java.util.HashMap;
import java.util.Map;

import static com.expr.bool.BooleanOperator.AND;
import static com.expr.bool.BooleanOperator.NOT;
import static com.expr.bool.BooleanOperator.OR;
import static com.expr.bool.GroupingOperator.LEFT_PARENTHESES;
import static com.expr.bool.GroupingOperator.RIGHT_PARAETHESES;

public interface OperatorRegistry {
    Map<Operator, Integer> PRECEDENCE_MAP = new HashMap<Operator, Integer>() {
        {
            // update the precedence appropriately here as we support new operators
            put(OR, 1);
            put(AND, 2);
            put(NOT, 3);
            put(LEFT_PARENTHESES, 4);
            put(RIGHT_PARAETHESES, 4);
        }
    };

    static int precedence(Operator operator) {
        if (!PRECEDENCE_MAP.containsKey(operator)) {
            throw new MalformedExpressionException(
                    "unknown operator: " + operator + ", precedence map: " + PRECEDENCE_MAP);
        }
        return PRECEDENCE_MAP.get(operator);
    }

}
