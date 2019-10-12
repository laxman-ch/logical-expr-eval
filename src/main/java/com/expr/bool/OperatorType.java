package com.expr.bool;

enum OperatorType {
    /**
     * Unary operators which expects and operates exactly on 1 operand
     */
    UNARY(1),
    /**
     * Binary operators which expects and operates exactly on 2 operands
     */
    BINARY(2),

    /**
     * Grouping operators are special and they are not expected to appear in a postfix expression.
     * Hence, zero operands
     */
    GROUPING(0);

    int numOperands;

    OperatorType(int numOperands) {
        this.numOperands = numOperands;
    }

    public int getNumOperands() {
        return numOperands;
    }
}
