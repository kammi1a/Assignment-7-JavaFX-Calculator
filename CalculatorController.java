package org.example.javafx_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
            return; // Если ошибка отображена, не позволяем вводить цифры
        }
        if (isCalculationComplete) {
            text.setText("");
            isCalculationComplete = false;
        }
        String val = text.getText();
        val += digit;
        text.setText(val);
    }

//    @FXML
//    private void onClick1() {
//        String val = text.getText();
//        val = val + "1";
//        text.setText(val);
//    }

//    @FXML
//    private void onClickPlus() {
//        String val = text.getText();
//        calc.setOp1(Integer.parseInt(val));
//        calc.setOperator('+');
//        text.setText("");
//    }

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
            return;
        }

        String val = text.getText();

        if (isCalculationComplete) {
            isCalculationComplete = false;
        }

        if (!val.isEmpty() && !val.endsWith(" ")) {
            text.setText(val + " " + operator + " ");
        }
    }

    @FXML
    private void onClickEqual() {
        if (isErrorDisplayed) {
            return; // Не позволяем выполнять расчет, пока отображена ошибка
        }
        String expression = text.getText();
        try {
            if (!expression.isEmpty()) {
                double result = calculateExpression(expression);

                // Проверяем, является ли результат целым числом
                if (result == (int) result) {
                    text.setText(String.valueOf((int) result)); // Выводим как целое число
                } else {
                    text.setText(String.valueOf(result)); // Выводим как число с плавающей точкой
                }

                isCalculationComplete = true;
            }
        } catch (ArithmeticException e) {
            text.setText("Error");
            isErrorDisplayed = true;
        }
    }

    private double calculateExpression(String expression) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ') continue;

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                operands.push(Double.parseDouble(sb.toString()));
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.pop();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
        }

        return operands.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    @FXML
    private void onClickClear() {
        calc.reset();
        text.setText("");
        isCalculationComplete = false;
        isErrorDisplayed = false;
    }

    @FXML
    private void onClickDot() {
        if (isErrorDisplayed) {
            return; // Не позволяем вводить точки при отображении ошибки
        }
        String currentText = text.getText();

        // Проверка на наличие точки в текущем операнде
        if (currentText.contains(".")) {
            return; // если точка уже есть, ничего не делаем
        }

        // Если строка пустая, добавляем "0."
        if (currentText.isEmpty()) {
            text.setText("0.");
        } else {
            // Иначе добавляем точку к текущему числу
            text.setText(currentText + ".");
        }
    }

    @FXML
    private void onClickSquareRoot() {
        if (isErrorDisplayed) {
            return; // Не позволяем выполнять операцию при отображении ошибки
        }
        String val = text.getText();
        if (!val.isEmpty()) {
            calc.setOp1(Double.parseDouble(val));
            calc.setOperator('√');
            calc.calculate(); // Выполняем расчет сразу
            if (calc.isError()) {
                text.setText("Error");
                isErrorDisplayed = true; // Устанавливаем флаг ошибки
            } else {
                displayResult(calc.getResult());
                isCalculationComplete = true;
            }
        }
    }

    @FXML
    private void onClickPlusMinus() {
        if (isErrorDisplayed) {
            return; // Не позволяем выполнять операцию при отображении ошибки
        }
        String val = text.getText();
        if (!val.isEmpty()) {
            calc.setOp1(Double.parseDouble(val));
            calc.setOperator('±');
            calc.calculate(); // Выполняем расчет сразу
            if (calc.isError()) {
                text.setText("Error");
                isErrorDisplayed = true; // Устанавливаем флаг ошибки
            } else {
                displayResult(calc.getResult());
                isCalculationComplete = true;
            }
        }
    }

    @FXML
    private void onClickPercentage() {
        if (isErrorDisplayed) {
            return; // Не позволяем выполнять операцию при отображении ошибки
        }
        String val = text.getText();
        if (!val.isEmpty()) {
            calc.setOp1(Double.parseDouble(val));
            calc.setOperator('%');
            calc.calculate(); // Выполняем расчет сразу
            if (calc.isError()) {
                text.setText("Error");
                isErrorDisplayed = true; // Устанавливаем флаг ошибки
            } else {
                displayResult(calc.getResult());
                isCalculationComplete = true;
            }
        }
    }

    // Вспомогательный метод для вывода результата
    private void displayResult(double result) {
        if (result == (int) result) {
            text.setText(String.valueOf((int) result));
        } else {
            text.setText(String.valueOf(result));
        }
    }

    @FXML
    private void onClickOpenBracket() {
        if (isErrorDisplayed) {
            return;
        }

        String val = text.getText();
        if (isCalculationComplete) {
            text.setText("(");
            isCalculationComplete = false;
        } else {
            text.setText(val + "(");
        }
    }

    @FXML
    private void onClickCloseBracket() {
        if (isErrorDisplayed) {
            return; // Не позволяем вводить скобки при отображении ошибки
        }

        String val = text.getText();
        if (!val.isEmpty()) {
            text.setText(val + ")");
        }
    }

    @FXML
    private void onClickBackspace() {
        if (isErrorDisplayed) {
            return;
        }
        String currentText = text.getText();

        if (!currentText.isEmpty()) {
            text.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    @FXML
    protected void onExitMenuClick() {
        System.exit(0);
    }

    @FXML
    protected void onHelpMenuClick() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("How to Use the Calculator");
        helpAlert.setContentText("This program allows you to perform basic arithmetic operations and additional functions.\n\n" +
                "1. Enter the numbers you want to use in calculations.\n" +
                "2. Choose the desired arithmetic operation (addition, subtraction, multiplication, division).\n" +
                "3. Use the following functions:\n" +
                "   - Square Root: to calculate the square root of a number.\n" +
                "   - Percentage: to calculate percentages.\n" +
                "   - Absolute Value: to get the absolute value of a number.\n" +
                "   - Parentheses: to change the order of operations.\n" +
                "4. Click 'Equals' to get the result.\n" +
                "5. Use the 'Clear' option to reset inputs or 'Backspace' to delete the last character.\n" +
                "6. Select 'Exit' to close the application.");
        helpAlert.showAndWait();
    }
}
