package View;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This class is used to displaying the standard calculator with basic calculator functions.
 */
public class StandardCalculatorPanel extends JPanel {

    private final Model.Calculator calculator = new Model.Calculator();

    private final Color customLightGray = new Color(210, 210, 210);
    private final Color customBlack = new Color(28, 28, 28);
    private final Color customWhite = new Color(255, 255, 255);
    private final Color customOrange = new Color(255, 149, 0);

    private final String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };
    private final String[] rightSymbols = {"÷", "×", "-", "+", "="};
    private final String[] topSymbols = {"AC", "+/-", "%"};
    private final String[] binaryOperators = {"÷", "×", "-", "+"};

    private final JLabel descriptionLabel = new JLabel();
    private final JLabel displayLabel = new JLabel();
    private final JPanel displayPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    /** True while the user is in the middle of typing the digits of a number. */
    private boolean isTypingANumber = false;

    public StandardCalculatorPanel() {
        setPreferredSize(new Dimension(500, 550));
        setLayout(new BorderLayout());

        descriptionLabel.setForeground(new Color(120, 120, 120));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setHorizontalAlignment(JLabel.RIGHT);
        descriptionLabel.setText("");
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(customWhite);

        displayLabel.setBackground(customWhite);
        displayLabel.setForeground(customBlack);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(descriptionLabel, BorderLayout.NORTH);
        displayPanel.add(displayLabel, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setBackground(customWhite);
        add(buttonPanel, BorderLayout.CENTER);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(Color.black);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.black);
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
     * This method is for logic of the calculation such as double, subtract, percentages.
     */
    private void handleButtonClick(String buttonValue) {
        if (buttonValue.equals("=")) {
            handleEquals();
        } else if (Arrays.asList(binaryOperators).contains(buttonValue)) {
            handleOperator(buttonValue);
        } else if (buttonValue.equals("AC")) {
            handleClear();
        } else if (buttonValue.equals("+/-")) {
            double numDisplay = Double.parseDouble(displayLabel.getText());
            numDisplay *= -1;
            displayLabel.setText(removeZeroDecimal(numDisplay));
        } else if (buttonValue.equals("%")) {
            double numDisplay = Double.parseDouble(displayLabel.getText());
            numDisplay /= 100;
            displayLabel.setText(removeZeroDecimal(numDisplay));
        } else if (buttonValue.equals("√")) {
            handleUnary("√", "√(" + displayLabel.getText() + ")");
        } else if (buttonValue.equals(".")) {
            handleDigitOrPoint(".");
        } else if ("0123456789".contains(buttonValue)) {
            handleDigitOrPoint(buttonValue);
        }
    }

    private void handleDigitOrPoint(String value) {
        if (!isTypingANumber) {
            if (!calculator.isPartial()) {
                calculator.startFreshEntry();
            }
            displayLabel.setText(value.equals(".") ? "0." : value);
            isTypingANumber = true;
        } else if (value.equals(".")) {
            if (!displayLabel.getText().contains(".")) {
                displayLabel.setText(displayLabel.getText() + ".");
            }
        } else {
            displayLabel.setText(displayLabel.getText() + value);
        }
    }

    private void handleOperator(String operatorSymbol) {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        String operandText = displayLabel.getText();
        try {
            double newDisplay = calculator.applyBinaryOperator(operatorSymbol, operandText, operandValue);
            displayLabel.setText(removeZeroDecimal(newDisplay));
            updateDescriptionLabel();
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
            handleClear();
        }
    }

    private void handleEquals() {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        String operandText = displayLabel.getText();
        try {
            double result = calculator.applyEquals(operandText, operandValue);
            displayLabel.setText(removeZeroDecimal(result));
            updateDescriptionLabel();
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
            handleClear();
        }
    }

    private void handleUnary(String funcLabel, String wrappedText) {
        isTypingANumber = false;
        double operandValue = Double.parseDouble(displayLabel.getText());
        if (operandValue < 0) {
            displayLabel.setText("Error");
            handleClear();
            return;
        }
        double result = Math.sqrt(operandValue);
        calculator.applyUnary(funcLabel, wrappedText, result);
        displayLabel.setText(removeZeroDecimal(result));
        updateDescriptionLabel();
    }

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

    private void handleClear() {
        calculator.clearAll();
        displayLabel.setText("0");
        descriptionLabel.setText("");
        isTypingANumber = false;
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