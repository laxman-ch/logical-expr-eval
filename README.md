# logical-expr-eval

## Problem Statement
- Check if a given logical expression is totality.

## Valid operands:
- Logical AND (&)
- Logical OR (|)
- Logical NOT (!)
- Parentheses ('(' and ')')

## Valid variables
- Single character
- Lowercase
- Max Cardinality is 10 (a to j)

## Constraints
- DON'T use any third part expression libraries including antlr
- Parsing and evaluation should be done in plain java code

## Scope
- Evaluation of other types operators apart from what has been specified is out-of-scope for the current implementation

## Approach
- Convert given infix expression to postfix expression using standard stack based approach
- Evaluate the expression for truth table for all the variables used in the expression

## Considerations
- Structured code
- Sufficient Test cases
- Extensible design considering the solution can be extended to other operators (like arithmetic, relational) and expressions

## Assumptions
- None

## Notes
- Robust way to implement this type of parsers is by reusing expression evaluation libraries like antlr.
    Using this we can define grammar, parsers and evaluation logic cleanly for each operator.
    However, we are not doing this in the current implementation due to constraint specified.
