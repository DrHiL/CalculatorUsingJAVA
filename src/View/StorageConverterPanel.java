package View;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This storage converter class is for designing and check whether this user enter a valid value. and logic about the units convert.
 */
public class StorageConverterPanel extends JPanel {

    private final Model.StorageConverter converter = new Model.StorageConverter();

    private final String[] units = {"Byte", "Kilobyte", "Megabyte", "Gigabyte", "Terabyte", "Petabyte"};

    private final JTextField inputField = new JTextField("0");
    private final JComboBox<String> fromUnitBox = new JComboBox<String>(units);
    private final JComboBox<String> toUnitBox = new JComboBox<String>(units);
    private final JLabel resultLabel = new JLabel("Result: 0");
    private final JButton convertButton = new JButton("Convert");

    private final Color customBlack = new Color(28, 28, 28);
    private final Color customWhite = new Color(255, 255, 255);
    private final Color customOrange = new Color(255, 149, 0);

    public StorageConverterPanel() {
        setPreferredSize(new Dimension(500, 550));
        setLayout(new GridBagLayout());
        setBackground(customWhite);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 25, 15, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Storage Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        inputField.setFont(new Font("Arial", Font.PLAIN, 26));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        gbc.gridy = 1;
        add(inputField, gbc);

        gbc.gridwidth = 1;
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(fromLabel, gbc);

        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(toLabel, gbc);

        fromUnitBox.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(fromUnitBox, gbc);

        toUnitBox.setFont(new Font("Arial", Font.PLAIN, 18));
        toUnitBox.setSelectedIndex(1);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(toUnitBox, gbc);

        convertButton.setFont(new Font("Arial", Font.BOLD, 20));
        convertButton.setBackground(customOrange);
        convertButton.setForeground(Color.black);
        convertButton.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(convertButton, gbc);

        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 5;
        add(resultLabel, gbc);

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    /**
     * Reads the input value and selected units, performs the conversion using the StorageConverter model, and displays the result.
     */
    private void performConversion() {
        try {
            double value = Double.parseDouble(inputField.getText());
            String from = (String) fromUnitBox.getSelectedItem();
            String to = (String) toUnitBox.getSelectedItem();
            double result = convert(value, from, to);
            resultLabel.setText("Result: " + result + " " + to + "(s)");
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number.");
        } catch (IllegalArgumentException ex) {
            resultLabel.setText(ex.getMessage());
        }
    }

    /**
     * Routes a value to the correct StorageConverter method based on the chosen "from" and "to" units.
     */
    private double convert(double value, String from, String to) {
        if (from.equals(to)) {
            return value;
        }
        if (from.equals("Byte")) {
            if (to.equals("Kilobyte")) return converter.bytesToKilobytes(value);
            if (to.equals("Megabyte")) return converter.bytesToMegabytes(value);
            if (to.equals("Gigabyte")) return converter.bytesToGigabytes(value);
            if (to.equals("Terabyte")) return converter.bytesToTerabytes(value);
            if (to.equals("Petabyte")) return converter.bytesToPetabytes(value);
        } else if (from.equals("Kilobyte")) {
            if (to.equals("Byte")) return converter.kilobytesToBytes(value);
            if (to.equals("Megabyte")) return converter.kilobytesToMegabytes(value);
            if (to.equals("Gigabyte")) return converter.kilobytesToGigabytes(value);
            if (to.equals("Terabyte")) return converter.kilobytesToTerabytes(value);
            if (to.equals("Petabyte")) return converter.kilobytesToPetabytes(value);
        } else if (from.equals("Megabyte")) {
            if (to.equals("Byte")) return converter.megabytesToBytes(value);
            if (to.equals("Kilobyte")) return converter.megabytesToKilobytes(value);
            if (to.equals("Gigabyte")) return converter.megabytesToGigabytes(value);
            if (to.equals("Terabyte")) return converter.megabytesToTerabytes(value);
            if (to.equals("Petabyte")) return converter.megabytesToPetabytes(value);
        } else if (from.equals("Gigabyte")) {
            if (to.equals("Byte")) return converter.gigabytesToBytes(value);
            if (to.equals("Kilobyte")) return converter.gigabytesToKilobytes(value);
            if (to.equals("Megabyte")) return converter.gigabytesToMegabytes(value);
            if (to.equals("Terabyte")) return converter.gigabytesToTerabytes(value);
            if (to.equals("Petabyte")) return converter.gigabytesToPetabytes(value);
        } else if (from.equals("Terabyte")) {
            if (to.equals("Byte")) return converter.terabytesToBytes(value);
            if (to.equals("Kilobyte")) return converter.terabytesToKilobytes(value);
            if (to.equals("Megabyte")) return converter.terabytesToMegabytes(value);
            if (to.equals("Gigabyte")) return converter.terabytesToGigabytes(value);
            if (to.equals("Petabyte")) return converter.terabytesToPetabytes(value);
        } else if (from.equals("Petabyte")) {
            if (to.equals("Byte")) return converter.petabytesToBytes(value);
            if (to.equals("Kilobyte")) return converter.petabytesToKilobytes(value);
            if (to.equals("Megabyte")) return converter.petabytesToMegabytes(value);
            if (to.equals("Gigabyte")) return converter.petabytesToGigabytes(value);
            if (to.equals("Terabyte")) return converter.petabytesToTerabytes(value);
        }
        throw new IllegalArgumentException("Unsupported conversion.");
    }
}
