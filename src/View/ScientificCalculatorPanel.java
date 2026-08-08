package View;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This ScientificCalculatorPanel is used to displaying the scientific modes including all of the
 * important buttons such as power function buttons.
 */
public class ScientificCalculatorPanel extends JPanel {

    private final Model.ScientificCalculator calculator = new Model.ScientificCalculator();

    private final Color customLightGray = new Color(210, 210, 210);
    private final Color customDarkGray = new Color(90, 90, 90);
    private final Color customBlack = new Color(28, 28, 28);
    private final Color customWhite = new Color(255, 255, 255);
    private final Color customOrange = new Color(255, 149, 0);

    private final String[] buttonValues = {
            "Deg", "AC", "+/-",
            "%", "1/x", "√",
            "sin", "x²", "÷",
            "cos", "x³", "×",
            "tan", "cot", "-",
            "π", "e", "+",
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", ".", "="
    };
    private final String[] rightSymbols = {"÷", "×", "-", "+", "="};
    private final String[] topSymbols = {"Deg", "AC", "+/-", "%"};
    private final String[] sciSymbols = {"√", "sin", "x²", "cos", "x³", "tan", "cot", "1/x", "π", "e"};
    private final String[] binaryOperators = {"÷", "×", "-", "+"};

    private final JLabel descriptionLabel = new JLabel();
    private final JLabel displayLabel = new JLabel();
    private final JPanel displayPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    private boolean isDegreeMode = true;
    private JButton degButton;
    private boolean isTypingANumber = false; //True while the user is in the middle of typing the digits of a number.
    private String currentOperandSymbol = null;

    public ScientificCalculatorPanel() {
        setPreferredSize(new Dimension(500, 550));
        setLayout(new BorderLayout());

        descriptionLabel.setForeground(new Color(120, 120, 120));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionLabel.setHorizontalAlignment(JLabel.RIGHT);
        descriptionLabel.setText("");
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(customWhite);

        displayLabel.setBackground(customWhite);
        displayLabel.setForeground(customBlack);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 45));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(descriptionLabel, BorderLayout.NORTH);
        displayPanel.add(displayLabel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(10, 3));
        buttonPanel.setBackground(customWhite);
        add(buttonPanel, BorderLayout.CENTER);

        for (int i = 0; i < buttonValues.length; i++) {
            String buttonValue = buttonValues[i];

            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, 22));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);

            if (buttonValue.equals("Deg")) {
                degButton = button;
            }

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(Color.black);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.black);
            } else if (Arrays.asList(sciSymbols).contains(buttonValue)) {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customWhite);
                button.setForeground(Color.black);
            }

            buttonPanel.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clicked = (JButton) e.getSource();
                    handleButtonClick(clicked.getText());
                }
            });
        }
    }

    /**
     * Handles a click on any scientific calculator button, dispatching to the appropriate helper.
     */
    private void handleButtonClick(String buttonValue) {
        if (buttonValue.equals("=")) {
            handleEquals();
        } else if (Arrays.asList(binaryOperators).contains(buttonValue)) {
            handleOperator(buttonValue);
        } else if (buttonValue.equals("Deg") || buttonValue.equals("Rad")) {
            isDegreeMode = !isDegreeMode;
            degButton.setText(isDegreeMode ? "Deg" : "Rad");
        } else if (buttonValue.equals("AC")) {
            handleClear();
        } else if (buttonValue.equals("+/-")) {
            double numDisplay = Double.parseDouble(displayLabel.getText());
            displayLabel.setText(removeZeroDecimal(numDisplay * -1));
            currentOperandSymbol = null;
        } else if (buttonValue.equals("%")) {
            double numDisplay = Double.parseDouble(displayLabel.getText());
            displayLabel.setText(removeZeroDecimal(numDisplay / 100));
            currentOperandSymbol = null;
        } else if (buttonValue.equals("√")) {
            handleUnary("√", "√(" + currentOperandText() + ")");
        } else if (buttonValue.equals("x²")) {
            handleUnary("x²", "(" + currentOperandText() + ")²");
        } else if (buttonValue.equals("x³")) {
            handleUnary("x³", "(" + currentOperandText() + ")³");
        } else if (buttonValue.equals("1/x")) {
            handleUnary("1/x", "1/(" + currentOperandText() + ")");
        } else if (buttonValue.equals("sin")) {
            handleUnary("sin", "sin(" + currentOperandText() + ")");
        } else if (buttonValue.equals("cos")) {
            handleUnary("cos", "cos(" + currentOperandText() + ")");
        } else if (buttonValue.equals("tan")) {
            handleUnary("tan", "tan(" + currentOperandText() + ")");
        } else if (buttonValue.equals("cot")) {
            handleUnary("cot", "cot(" + currentOperandText() + ")");
        } else if (buttonValue.equals("π")) {
            handleConstant("π", Math.PI);
        } else if (buttonValue.equals("e")) {
            handleConstant("e", Math.E);
        } else if (buttonValue.equals(".")) {
            handleDigitOrPoint(".");
        } else if ("0123456789".contains(buttonValue)) {
            handleDigitOrPoint(buttonValue);
        }
    }

    /**
     * Returns the text that should represent the current display value in the description:
     */
    private String currentOperandText() {
        return currentOperandSymbol != null ? currentOperandSymbol : displayLabel.getText();
    }

    /**
     * Handles a digit or "." press, replacing the display when starting a brand new number.
     */
    private void handleDigitOrPoint(String value) {
        if (!isTypingANumber) {
            if (!calculator.isPartial()) {
                calculator.startFreshEntry();
            }
            displayLabel.setText(value.equals(".") ? "0." : value);
            isTypingANumber = true;
            currentOperandSymbol = null;
        } else if (value.equals(".")) {
            if (!displayLabel.getText().contains(".")) {
                displayLabel.setText(displayLabel.getText() + ".");
            }
        } else {
            displayLabel.setText(displayLabel.getText() + value);
        }
    }

    /**
     * Handles a press of a named mathematical constant button, and inserts its numeric value onto the display
     */
    private void handleConstant(String symbol, double value) {
        if (!isTypingANumber && !calculator.isPartial()) {
            calculator.startFreshEntry();
        }
        displayLabel.setText(removeZeroDecimal(value));
        isTypingANumber = true;
        currentOperandSymbol = symbol;
    }

    /**
     * Handles a binary operator button
     */
    private void handleOperator(String operatorSymbol) {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        String operandText = currentOperandText();
        try {
            double newDisplay = calculator.applyBinaryOperator(operatorSymbol, operandText, operandValue);
            displayLabel.setText(removeZeroDecimal(newDisplay));
            currentOperandSymbol = null;
            updateDescriptionLabel();
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
            handleClear();
        }
    }

    /**
     * Handles the "=" button, finalising whatever calculation is pending.
     */
    private void handleEquals() {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        String operandText = currentOperandText();
        try {
            double result = calculator.applyEquals(operandText, operandValue);
            displayLabel.setText(removeZeroDecimal(result));
            currentOperandSymbol = null;
            updateDescriptionLabel();
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
            handleClear();
        }
    }

    /**
     * Handles a unary scientific function such as cos, tan.
     */
    private void handleUnary(String funcLabel, String wrappedText) {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        try {
            double result;
            switch (funcLabel) {
                case "√":
                    result = calculator.squareRoot(operandValue);
                    break;
                case "x²":
                    result = calculator.square(operandValue);
                    break;
                case "x³":
                    result = calculator.cube(operandValue);
                    break;
                case "1/x":
                    result = calculator.reciprocal(operandValue);
                    break;
                case "sin":
                    result = isDegreeMode ? calculator.sinDegrees(operandValue) : calculator.sinRadians(operandValue);
                    break;
                case "cos":
                    result = isDegreeMode ? calculator.cosDegrees(operandValue) : calculator.cosRadians(operandValue);
                    break;
                case "tan":
                    result = isDegreeMode ? calculator.tanDegrees(operandValue) : calculator.tanRadians(operandValue);
                    break;
                case "cot":
                    result = isDegreeMode ? calculator.cotDegrees(operandValue) : calculator.cotRadians(operandValue);
                    break;
                default:
                    result = operandValue;
            }
            calculator.applyUnary(funcLabel, wrappedText, result);
            displayLabel.setText(removeZeroDecimal(result));
            currentOperandSymbol = null;
            updateDescriptionLabel();
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
            handleClear();
        }
    }

    /**
     * Clear all the number when user calls "AC"
     */
    private void handleClear() {
        calculator.clearAll();
        displayLabel.setText("0");
        descriptionLabel.setText("");
        isTypingANumber = false;
        currentOperandSymbol = null;
    }

    /**
     * Refreshes the description label from the model
     */
    private void updateDescriptionLabel() {
        String description = calculator.getDescription();
        if (description.isEmpty()) {
            descriptionLabel.setText("");
        } else if (calculator.isPartial()) {
            descriptionLabel.setText(description + " …");
        } else {
            descriptionLabel.setText(description + " =");
        }
    }

    /**
     * Formats a double for display.
     */
    private String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0 && Math.abs(numDisplay) < 1e15) {
            return Long.toString((long) numDisplay);
        }
        String formatted = String.format("%.8f", numDisplay);
        while (formatted.endsWith("0")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        if (formatted.endsWith(".")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }
        return formatted;
    }
}