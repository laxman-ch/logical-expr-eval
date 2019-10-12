package com.expr.bool;

import static com.expr.bool.Associativity.L2R;
import static com.expr.bool.Associativity.R2L;
import static com.expr.bool.OperatorType.BINARY;
import static com.expr.bool.OperatorType.UNARY;

enum BooleanOperator implements Operator<Boolean> {
    /**
     * Logical AND
     */
    AND("&", BINARY, L2R) {
        @Override
        public Boolean eval(Boolean... operands) {
            return operands[0] && operands[1];
        }
    },
    /**
     * Logical OR
     */
    OR("|", BINARY, L2R) {
        @Override
        public Boolean eval(Boolean... operands) {
            return operands[0] || operands[1];
        }
    },

    /**
     * Logical NOT
     */
    NOT("!", UNARY, R2L) {
        @Override
        public Boolean eval(Boolean... operands) {
            return !operands[0];
        }
    };

    private String symbol;
    private OperatorType type;
    private Associativity associativity;

    BooleanOperator(String symbol, OperatorType type, Associativity associativity) {
        this.symbol = symbol;
        this.type = type;
        this.associativity = associativity;
    }

    static BooleanOperator of(char symbol) {
        switch (symbol) {
            case '&':
                return AND;
            case '|':
                return OR;
            case '!':
                return NOT;
        }
        throw new MalformedExpressionException("unknown boolean operator: " + symbol);
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

    @Override
    public String toString() {
        return symbol();
    }
}
