package com.example.calculator;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

enum Operator
{
    CLOSE((byte)4, ')'), POW((byte)3, '^'), MUL((byte)2, '*'), DIV((byte)2, '/'), MOD((byte)2, '%'), ADD((byte)1, '+'), SUB((byte)1, '-'), OPEN((byte)0, '(');

    private byte precedence;
    private char str_repr;

    Operator(byte precedence, char repr)
    {
        this.precedence = precedence;
        str_repr = repr;
    }

    byte getPrecedence()
    {
        return precedence;
    }

    static Operator parse(char ch)
    {
        if(ch == Operator.CLOSE.str_repr)
            return Operator.CLOSE;
        if(ch == Operator.MUL.str_repr)
            return Operator.MUL;
        if(ch == Operator.DIV.str_repr)
            return Operator.DIV;
        if(ch == Operator.MOD.str_repr)
            return Operator.MOD;
        if(ch == Operator.ADD.str_repr)
            return Operator.ADD;
        if(ch == Operator.SUB.str_repr)
            return Operator.SUB;
        if(ch == Operator.POW.str_repr)
            return Operator.POW;

        return Operator.OPEN;
    }

    String strRepr()
    {
        return Character.toString(str_repr);
    }

}

public class Calculator
{
    Calculator()
    {}

    boolean checkExpression(String expression)
    {
        return checkParenthesis(expression);
    }

    private boolean checkParenthesis(String expression)
    {
        int unbalancedParenthesis = 0;
        char chars[] = expression.toCharArray();
        for(char e : chars)
        {
            if(e == '(') ++unbalancedParenthesis;
            else if(e == ')') --unbalancedParenthesis;
        }

        return (unbalancedParenthesis == 0);
    }

    double evaluate(String expression) throws InvalidExpressionException
    {
        ArrayList<String> postfix = toPostfix(expression);
        return evalPostfix(postfix);
    }

    private ArrayList<String> toPostfix(String infix)
    {
        ArrayList<String> postfix = new ArrayList<String>();
        Stack<Operator> operatorStack = new Stack<Operator>();

        for(int a = 0 ; a < infix.length(); ++a)
        {
            if(isDigit(infix.charAt(a)))
            {
                KeyValuePair<String, Integer> ope = parseOperand(infix, a);
                postfix.add(ope.key);
                a = ope.value - 1;
                continue;
            }
            else
            {
                Operator op = Operator.parse(infix.charAt(a));
                if(op.getPrecedence() == Operator.CLOSE.getPrecedence())
                {
                    while(operatorStack.peek() != Operator.OPEN)
                    {
                        postfix.add(operatorStack.pop().strRepr());
                    }
                    operatorStack.pop();
                }
                else if(op.getPrecedence() == 0 || operatorStack.empty() || operatorStack.peek().getPrecedence() < op.getPrecedence()
                        || (operatorStack.peek().getPrecedence() == op.getPrecedence() && op == Operator.POW))
                    operatorStack.add(op);
                else
                {
                    while(!operatorStack.empty() && operatorStack.peek().getPrecedence() >= op.getPrecedence())
                    {
                        postfix.add(operatorStack.pop().strRepr());
                    }
                    operatorStack.add(op);
                }

            }

        }
        while(!operatorStack.isEmpty())
        {
            postfix.add(operatorStack.pop().strRepr());
        }

        return postfix;
    }

    private boolean isDigit(char c)
    {
        //Tells whether the character is a numerical digit

        try
        {
            byte digit = Byte.parseByte(Character.toString(c));

            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }

    }

    private KeyValuePair<String, Integer> parseOperand(String ex, int start)
    {
        //Parses the operand and adds it to the list of operands. Returns the end of the operand in the expression

        String operand = ""; //The parsed operand

        int a = start;
        for(; a < ex.length(); ++a)
        {
            if(isDigit(ex.charAt(a)) || ex.charAt(a) == '.')
                operand += ex.charAt(a);
            else
                break;
        }

        return new KeyValuePair<String, Integer>(operand, a);
    }

    private double evalPostfix(ArrayList<String> postfix) throws InvalidExpressionException
    {
        Stack<Double> output = new Stack<Double>();

        for(int a = 0; a < postfix.size(); ++a)
        {
            try
            {
                double operand = Double.parseDouble(postfix.get(a));
                output.add(operand);
            }
            catch (NumberFormatException e)
            {
                double rightOperand = 0.0;
                double leftOperand = 0.0;
                try {
                    rightOperand = output.pop();
                    leftOperand = output.pop();
                }
                catch(EmptyStackException e2)
                {
                    throw new InvalidExpressionException();
                }

                double res = 0.0;
                switch(Operator.parse(postfix.get(a).toCharArray()[0]))
                {
                    case POW : res = Math.pow(leftOperand, rightOperand);break;
                    case MUL : res = leftOperand * rightOperand; break;
                    case DIV : res = leftOperand / rightOperand; break;
                    case MOD : res = leftOperand % rightOperand; break;
                    case ADD : res = leftOperand + rightOperand; break;
                    case SUB : res = leftOperand - rightOperand; break;
                }
                output.add(res);
            }
        }

        return output.pop();
    }

}
