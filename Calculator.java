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
        isError = false; // Reset error flag
        switch (operator) {
            case '+':
                result = op1 + op2; // Addition
                break;
            case '-':
                result = op1 - op2; // Subtraction
                break;
            case '*':
                result = op1 * op2; // Multiplication
                break;
            case '/':
                if (op2 == 0) {
                    isError = true; // Handle division by zero
                    result = 0;
                } else {
                    result = op1 / op2; // Division
                }
                break;
            default:
                isError = true; // Invalid operator
                result = 0;
                break;
        }
    }

    public void reset() {
        op1 = 0; // Reset operand 1
        op2 = 0; // Reset operand 2
        operator = '\0'; // Reset operator
        result = 0; // Reset result
        isError = false; // Reset error flag
    }
}
