package View;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This class is for creating the design such as colouring. and it is allowed to switch between the modes such as binary, octal, and hexadecimal, and many others.
 */
public class ProgrammerCalculatorPanel extends JPanel {

    private final Model.ProgrammerCalculator calculator = new Model.ProgrammerCalculator();

    private final Color customLightGray = new Color(210, 210, 210);
    private final Color customDarkGray = new Color(90, 90, 90);
    private final Color customBlack = new Color(28, 28, 28);
    private final Color customWhite = new Color(255, 255, 255);
    private final Color customOrange = new Color(255, 149, 0);

    private final String[] buttonValues = {
        "A", "B", "C", "AND", "AC",
        "D", "E", "F", "OR", "%",
        "7", "8", "9", "XOR", "÷",
        "4", "5", "6", "NOT", "×",
        "1", "2", "3", "<<", "-",
        "0", "+/-", ">>", "=", "+"
    };
    private final String[] rightSymbols = {"÷", "×", "-", "+", "="};
    private final String[] topSymbols = {"AC", "+/-", "%", "AND", "OR", "XOR", "NOT", "<<", ">>"};
    private final String[] hexLetters = {"A", "B", "C", "D", "E", "F"};
    private final String[] binaryOperators = {"÷", "×", "-", "+", "%", "AND", "OR", "XOR", "<<", ">>"};

    private final JLabel displayLabel = new JLabel();
    private final JPanel displayPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel baseRow = new JPanel();
    private final Map<String, JButton> buttonsByLabel = new HashMap<>();

    private int currentBase = 10;
    private String A = "0";
    private String operator = null;
    private String B = null;

    public ProgrammerCalculatorPanel() {
        setPreferredSize(new Dimension(500, 600));
        setLayout(new BorderLayout());

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.add(buildBaseRow(), BorderLayout.NORTH);

        displayLabel.setBackground(customWhite);
        displayLabel.setForeground(customBlack);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 42));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        topArea.add(displayPanel, BorderLayout.CENTER);

        add(topArea, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(6, 5));
        buttonPanel.setBackground(customWhite);
        add(buttonPanel, BorderLayout.CENTER);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 20));
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
            } else if (Arrays.asList(hexLetters).contains(buttonValue)) {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customWhite);
                button.setForeground(Color.black);
            }

            buttonsByLabel.put(buttonValue, button);
            buttonPanel.add(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clicked = (JButton) e.getSource();
                    handleButtonClick(clicked.getText());
                }
            });
        }

        updateButtonAvailability();
    }

    /**
     * Displaying the Top row, showing the Hex, Dec, OCT, and binary.
     */
    private JPanel buildBaseRow() {
        baseRow.setLayout(new GridLayout(1, 4));
        String[] bases = {"HEX", "DEC", "OCT", "BIN"};
        for (int i = 0; i < bases.length; i++) {
            final String base = bases[i];
            JButton baseButton = new JButton(base);
            baseButton.setFocusable(false);
            baseButton.setFont(new Font("Arial", Font.BOLD, 16));
            baseButton.setBackground(customBlack);
            baseButton.setForeground(customBlack);
            baseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switchBase(base);
                }
            });
            baseRow.add(baseButton);
        }
        return baseRow;
    }

    /**
     * Switches the active number base, reformatting the current display
     */
    private void switchBase(String base) {
        long currentValue = parseCurrentDisplay();

        if (base.equals("HEX")) {
            currentBase = 16;
        } else if (base.equals("OCT")) {
            currentBase = 8;
        } else if (base.equals("BIN")) {
            currentBase = 2;
        } else {
            currentBase = 10;
        }

        displayLabel.setText(Long.toString(currentValue, currentBase).toUpperCase());
        updateButtonAvailability();
    }

    /**
     * Enables or disables the digit buttons depending on the current base, so the user cannot enter digits that are invalid in that base.
     */
    private void updateButtonAvailability() {
        for (int digit = 0; digit <= 9; digit++) {
            JButton button = buttonsByLabel.get(String.valueOf(digit));
            if (button != null) {
                button.setEnabled(digit < currentBase);
            }
        }
        for (String letter : hexLetters) {
            JButton button = buttonsByLabel.get(letter);
            if (button != null) {
                button.setEnabled(currentBase == 16);
            }
        }
    }

    /**
     * Handles a click on any programmer calculator button.
     */
    private void handleButtonClick(String buttonValue) {
        if (buttonValue.equals("=")) {
            evaluate();
        } else if (Arrays.asList(binaryOperators).contains(buttonValue)) {
            if (operator == null) {
                A = displayLabel.getText();
                displayLabel.setText("0");
                B = "0";
            }
            operator = buttonValue;
        } else if (buttonValue.equals("AC")) {
            clearAll();
            displayLabel.setText("0");
        } else if (buttonValue.equals("+/-")) {
            long value = parseCurrentDisplay();
            displayLabel.setText(Long.toString(-value, currentBase).toUpperCase());
        } else if (buttonValue.equals("NOT")) {
            int value = (int) parseCurrentDisplay();
            int result = calculator.bitwiseInversion(value);
            displayLabel.setText(Integer.toString(result, currentBase).toUpperCase());
        } else {
            // Digits 0-9 and hex letters A-F
            if (displayLabel.getText().equals("0")) {
                displayLabel.setText(buttonValue);
            } else {
                displayLabel.setText(displayLabel.getText() + buttonValue);
            }
        }
    }

    /**
     * This method performs the pending calculation using the ProgrammerCalculator model.
     */
    private void evaluate() {
        if (A == null || operator == null) {
            return;
        }
        B = displayLabel.getText();
        long numA = Long.parseLong(A, currentBase);
        long numB = Long.parseLong(B, currentBase);
        try {
            long result;
            switch (operator) {
                case "+": result = (long) calculator.add(numA, numB); break;
                case "-": result = (long) calculator.subtract(numA, numB); break;
                case "×": result = (long) calculator.multiply(numA, numB); break;
                case "÷": result = (long) calculator.divide(numA, numB); break;
                case "%": result = (long) calculator.modulo(numA, numB); break;
                case "AND": result = calculator.logicalAND((int) numA, (int) numB); break;
                case "OR": result = calculator.logicalOR((int) numA, (int) numB); break;
                case "XOR": result = calculator.XOR((int) numA, (int) numB); break;
                case "<<": result = calculator.leftShift((int) numA, (int) numB); break;
                case ">>": result = calculator.rightShift((int) numA, (int) numB); break;
                default: result = numB;
            }
            displayLabel.setText(Long.toString(result, currentBase).toUpperCase());
        } catch (ArithmeticException ex) {
            displayLabel.setText("Error");
        }
        clearAll();
    }

    /**
     * Parses the value currently shown on the display using the active number base.
     */
    private long parseCurrentDisplay() {
        String text = displayLabel.getText();
        if (text.isEmpty() || text.equals("-") || text.equals("Error")) {
            return 0;
        }
        return Long.parseLong(text, currentBase);
    }

    private void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
}
