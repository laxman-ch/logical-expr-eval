package com.expr.bool;

/**
 * Base operator class
 *
 * @param <T> type to support various types of expressions - Boolean expressions, Arithmetic expression
 */
interface Operator<T> extends Token {
    default boolean isOperator() {
        return true;
    }

    /**
     * @return expected number of operands for this operator to evaluate
     */
    default int expectedOpereandCount() {
        return type().getNumOperands();
    }

    /**
     * @return Type of the operator - unary, binary
     */
    OperatorType type();

    /**
     * @return Associativity of the operator
     */
    Associativity associativity();

    /**
     * Evaluate the expression based on
     *
     * @param operands
     * @return
     */
    T eval(T... operands);

    default int precedence() {
        return OperatorRegistry.precedence(this);
    }
}
