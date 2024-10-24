package org.example.javafx_calculator;

public class Calculator {
    private double op1;
    private double op2;
    private char operator;
    private double result;
    private boolean isError;

    public double getOp1() {
        return op1;
    }

    public void setOp1(double op1) {
        this.op1 = op1;
    }

    public double getOp2() {
        return op2;
    }

    public void setOp2(double op2) {
        this.op2 = op2;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public double getResult() {
        return result;
    }

    public boolean isError() {
        return isError;
    }

    public void calculate() {
        isError = false;
        switch (operator) {
            case '+':
                result = op1 + op2;
                break;
            case '-':
                result = op1 - op2;
                break;
            case '*':
                result = op1 * op2;
                break;
            case '%':
                result = op1 * 0.01;
                break;
            case '√':
                if (op1 < 0) {
                    isError = true;
                    result = 0;
                } else {
                    result = Math.sqrt(op1);
                }
                break;
            case '±':
                result = -op1;
                break;
            case '/':
                if (op2 == 0) {
                    isError = true;
                    result = 0;
                } else {
                    result = op1 / op2;
                }
                break;
            default:
                isError = true;
                result = 0;
                break;
        }
    }

    public void reset() {
        op1 = 0;
        op2 = 0;
        operator = '\0';
        result = 0;
        isError = false;
    }
}
