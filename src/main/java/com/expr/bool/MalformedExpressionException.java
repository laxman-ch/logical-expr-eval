package com.expr.bool;

class MalformedExpressionException extends RuntimeException {
    public MalformedExpressionException(String errorMessage) {
        super(errorMessage);
    }
}
