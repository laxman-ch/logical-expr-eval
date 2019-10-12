package com.expr.bool;

import static com.expr.bool.Associativity.R2L;
import static com.expr.bool.OperatorType.GROUPING;

enum GroupingOperator implements Operator<Void> {
    LEFT_PARENTHESES("(", GROUPING, R2L) {
        @Override
        public Void eval(Void... operands) {
            throw new UnsupportedOperationException("grouping operator evaluation not supported");
        }
    },
    RIGHT_PARAETHESES(")", GROUPING, R2L) {
        @Override
        public Void eval(Void... operands) {
            throw new UnsupportedOperationException("grouping operator evaluation not supported");
        }
    };

    private String symbol;
    private OperatorType type;
    private Associativity associativity;

    GroupingOperator(String symbol, OperatorType type, Associativity associativity) {
        this.symbol = symbol;
        this.type = type;
        this.associativity = associativity;
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public OperatorType type() {
        return type;
    }

    @Override
    public Associativity associativity() {
        return associativity;
    }
}
