package com.expr.bool;

class Operand implements Token, Comparable<Operand> {
    private String symbol;

    private Operand(String symbol) {
        this.symbol = symbol;
    }

    static Token of(Character symbol) {
        return of(symbol.toString());
    }

    static Token of(String symbol) {
        return new Operand(symbol);
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return symbol.equals(((Operand) other).symbol);
    }

    @Override
    public int compareTo(Operand other) {
        return symbol.compareTo(other.symbol);
    }

    @Override
    public String toString() {
        return symbol();
    }
}
