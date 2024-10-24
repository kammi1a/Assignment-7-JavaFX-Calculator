package org.example.javafx_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.Stack;

public class CalculatorController {
    Calculator calc = new Calculator();
    @FXML
    private TextField text;
    private boolean isCalculationComplete = false;
    private boolean isErrorDisplayed = false;

    @FXML
    private void onClick1() {
        handleDigit("1");
    }

    @FXML
    private void onClick2() {
        handleDigit("2");
    }

    @FXML
    private void onClick3() {
        handleDigit("3");
    }

    @FXML
    private void onClick4() {
        handleDigit("4");
    }

    @FXML
    private void onClick5() {
        handleDigit("5");
    }

    @FXML
    private void onClick6() {
        handleDigit("6");
    }

    @FXML
    private void onClick7() {
        handleDigit("7");
    }

    @FXML
    private void onClick8() {
        handleDigit("8");
    }

    @FXML
    private void onClick9() {
        handleDigit("9");
    }

    @FXML
    private void onClick0() {
        handleDigit("0");
    }

    private void handleDigit(String digit) {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }
        if (isCalculationComplete) {
            text.setText(""); // Clear input if a calculation is complete
            isCalculationComplete = false;
        }
        String val = text.getText();
        val += digit;
        text.setText(val);
    }

    @FXML
    private void onClickPlus() {
        handleOperator('+');
    }

    @FXML
    private void onClickMinus() {
        handleOperator('-');
    }

    @FXML
    private void onClickMultiply() {
        handleOperator('*');
    }

    @FXML
    private void onClickDivide() {
        handleOperator('/');
    }

    private void handleOperator(char operator) {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }

        String val = text.getText();

        if (isCalculationComplete) {
            isCalculationComplete = false; // Reset calculation flag if it was complete
        }

        if (!val.isEmpty() && !val.endsWith(" ")) {
            text.setText(val + " " + operator + " "); // Append operator to the input
        }
    }

    @FXML
    private void onClickEqual() {
        if (isErrorDisplayed) {
            return; // Prevent calculation if an error is displayed
        }
        String expression = text.getText();
        try {
            if (!expression.isEmpty()) {
                double result = calculateExpression(expression);
                displayResult(result); // Display the result
                isCalculationComplete = true; // Set calculation as complete
            }
        } catch (ArithmeticException e) {
            text.setText("Error"); // Show error message on exception
            isErrorDisplayed = true; // Set error flag
        }
    }

    private double calculateExpression(String expression) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ') continue; // Skip spaces

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(Double.parseDouble(sb.toString())); // Push number onto operand stack
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop())); // Apply operations based on precedence
                }
                operators.push(ch); // Push current operator onto stack
            }
        }

        while (!operators.isEmpty()) {
            operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop())); // Process remaining operations
        }

        return operands.pop(); // Return final result
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-'); // Check operator precedence
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': 
                if (b == 0) throw new ArithmeticException("Cannot divide by zero"); // Prevent division by zero
                return a / b;
        }
        return 0;
    }

    @FXML
    private void onClickClear() {
        calc.reset(); // Reset calculator state
        text.setText(""); // Clear input field
        isCalculationComplete = false; // Reset calculation state
        isErrorDisplayed = false; // Reset error state
    }

    @FXML
    private void onClickDot() {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }
        String currentText = text.getText();

        // Check if there's already a decimal point in the current number
        if (currentText.contains(".")) {
            return; // Do nothing if a decimal point is present
        }

        // If the input is empty, add "0."
        if (currentText.isEmpty()) {
            text.setText("0.");
        } else {
            text.setText(currentText + "."); // Add decimal point to the current number
        }
    }

    @FXML
    private void onClickOpenBracket() {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }

        String val = text.getText();
        if (isCalculationComplete) {
            text.setText("("); // Start a new expression with an open bracket
            isCalculationComplete = false;
        } else {
            text.setText(val + "("); // Append open bracket to the current input
        }
    }

    @FXML
    private void onClickCloseBracket() {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }

        String val = text.getText();
        if (!val.isEmpty()) {
            text.setText(val + ")"); // Append close bracket to the current input
        }
    }

    @FXML
    private void onClickBackspace() {
        if (isErrorDisplayed) {
            return; // Prevent input when an error is displayed
        }
        String currentText = text.getText();

        if (!currentText.isEmpty()) {
            text.setText(currentText.substring(0, currentText.length() - 1)); // Remove last character
        }
    }

    @FXML
    protected void onExitMenuClick() {
        System.exit(0); // Exit application
    }

    @FXML
    protected void onHelpMenuClick() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("How to Use the Calculator");
        helpAlert.setContentText("This program allows you to perform basic arithmetic operations.\n\n" +
                "1. Enter the numbers you want to use in calculations.\n" +
                "2. Choose the desired arithmetic operation (addition, subtraction, multiplication, division).\n" +
                "3. Click 'Equals' to get the result.\n" +
                "4. Use 'Clear' to reset inputs or 'Backspace' to delete the last character.\n" +
                "5. Select 'Exit' to close the application.");
        helpAlert.showAndWait(); // Show help dialog
    }

    private void displayResult(double result) {
        if (result == (int) result) {
            text.setText(String.valueOf((int) result)); // Display as integer if whole number
        } else {
            text.setText(String.valueOf(result)); // Display as floating-point number
        }
    }
}
