package com.expr.bool;


import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import static com.expr.bool.Associativity.L2R;
import static com.expr.bool.GroupingOperator.LEFT_PARENTHESES;

public class ExpEvaluator {
    private static final Logger LOG = LoggerFactory.getLogger(ExpEvaluator.class);

    public boolean checkTotality(String infix) {
        // convert to postfix
        Deque<Token> postfix = convertToPostfix(infix);

        LOG.debug("built posfix expression. infix: {}, postfix: {}", infix, postfix);

        // collect all unique variables set
        TreeSet<Token> variablesSet = new TreeSet<>();
        postfix.stream().sequential().filter(token -> !token.isOperator()).forEach(variablesSet::add);
        ArrayList<Token> variables = new ArrayList<Token>() {{
            addAll(variablesSet);
        }};

        LOG.debug("unique operands in the expression: {}, max verifiable combinations: {}", variables,
                Math.pow(variables.size(), 2));
        // generate truth table maps for the variables
        String format = "%" + variables.size() + "s";

        // truth table for n variables will have 2^n -1 combinations
        // generate binary representation and treat 0 as false and 1 as true
        for (Integer val = 0; val < Math.pow(2, variables.size()); val++) {
            Map<Operand, Boolean> context = Maps.newHashMap();
            String binaryValueStr = String.format(format, val.toBinaryString(val)).replace(' ', '0');

            for (int index = 0; index < variables.size(); index++) {
                context.put((Operand) variables.get(index), binaryValueStr.charAt(index) == '1');
            }

            boolean result = eval(infix, postfix, context);
            LOG.debug("infix: {}, postfix: {}, totality check result: {}, context: {}", infix, postfix, result,
                    context);

            if (result == false) {
                LOG.info("totality check failed. expression: {}, postfix: {}, variable values are: {}", infix, postfix,
                        context);
                return false;
            }
        }
        return true;
    }

    public boolean eval(String infix, Deque<Token> postfix, Map<Operand, Boolean> context) {
        Stack<Boolean> operandsStack = new Stack<>();

        postfix.stream().sequential().forEach(token -> {
            // operand - push on to the stack
            if (!token.isOperator()) {
                operandsStack.push(context.get(token));
            } else {
                Operator operator = (Operator) token;
                Boolean[] operands = new Boolean[operator.expectedOpereandCount()];
                for (int index = operator.expectedOpereandCount() - 1; index >= 0; index--) {
                    operands[index] = operandsStack.pop();
                }
                operandsStack.push((Boolean) operator.eval(operands));
            }
        });

        if (operandsStack.size() != 1) {
            throw new MalformedExpressionException("error occurred while evaluating the expression: " + infix);
        }

        return operandsStack.pop();
    }

    /**
     * convert a given infix expression to postfix format
     *
     * @param infix
     * @return postfix form of input infix expression
     * @throws
     */
    private Deque<Token> convertToPostfix(String infix) {
        Deque<Token> postfix = new ArrayDeque<>();
        Stack<Operator> operators = new Stack<>();

        for (int index = 0; index < infix.length(); index++) {
            Character token = infix.charAt(index);
            LOG.debug(
                    "postfix conversion. infix: {}, processing index: {}, processing token: {}, postfix so far: {}, op-stack: {}",
                    infix, index, token, postfix, operators);
            if (token.equals(' ')) {
                // discard spaces
                continue;
            } else if (isVariable(token)) {
                // variables - add them to the end of postfix expression queue
                postfix.addLast(Operand.of(token));
            } else if (isOperator(token)) {
                if (token == '(') {
                    // left parentheses - push it to operator stack
                    operators.push(LEFT_PARENTHESES);
                } else if (token == ')') {
                    Operator top = null;
                    while (!operators.isEmpty() && (top = operators.pop()) != LEFT_PARENTHESES) {
                        postfix.addLast(top);
                    }
                    if (top != LEFT_PARENTHESES) {
                        throw new MalformedExpressionException("parentheses mismatch. failed at index: " + index);
                    }
                } else {
                    BooleanOperator current = BooleanOperator.of(token);
                    while (!operators.isEmpty() &&
                           operators.peek() != LEFT_PARENTHESES &&
                           (
                                   operators.peek().precedence() > current.precedence() ||
                                   (
                                           operators.peek().precedence() == current.precedence() &&
                                           operators.peek().associativity() == L2R
                                   )
                           )) {
                        postfix.addLast(operators.pop());
                    }
                    operators.push(current);
                }
            } else {
                throw new MalformedExpressionException(
                        "unexpected token '" + token + "' encountered at index: " + index);
            }
            LOG.debug(
                    "postfix conversion. infix: {}, processed index: {}, processed token: {}, postfix so far: {}, op-stack: {}",
                    infix, index, token, postfix, operators);
        }

        // pop from operator stack to the post fix expression
        while (!operators.isEmpty()) {
            postfix.addLast(operators.pop());
        }

        return postfix;
    }

    private boolean isVariable(char token) {
        return Character.isLowerCase(token) && token <= 'j';
    }

    private boolean isOperator(char token) {
        return token == '&' || token == '|' || token == '!' || token == '(' || token == ')';
    }
}
