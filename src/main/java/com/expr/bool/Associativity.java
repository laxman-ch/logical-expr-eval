package com.expr.bool;

/**
 * Defines associativity types of operators
 */
enum Associativity {
    /**
     * Left to right associativity
     * Example operators: Logical AND, Logical OR
     */
    L2R,
    /**
     * Right to left associativity
     * Example operators: Logical NOT
     */
    R2L
}
