package View;
import Model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class is used for storing the menu, it allows user to switch between modes.
 */
public class MainFrame extends JFrame {

    public static final String STANDARD = "Standard";
    public static final String SCIENTIFIC = "Scientific";
    public static final String PROGRAMMER = "Programmer";
    public static final String STORAGE = "Storage Converter";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final JLabel titleLabel = new JLabel(STANDARD, SwingConstants.CENTER);

    private final Color headerBackground = new Color(28, 28, 28);
    private final Color headerText = Color.WHITE;

    public MainFrame() {
        super("Calculator");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        cardPanel.add(new StandardCalculatorPanel(), STANDARD);
        cardPanel.add(new ScientificCalculatorPanel(), SCIENTIFIC);
        cardPanel.add(new ProgrammerCalculatorPanel(), PROGRAMMER);
        cardPanel.add(new StorageConverterPanel(), STORAGE);

        cardLayout.show(cardPanel, STANDARD);
    }

    /**
     * This is the header bar containing the hamburger menu button.
     */
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(headerBackground);
        header.setPreferredSize(new Dimension(500, 50));

        JButton menuButton = new JButton("\u2630"); // hamburger icon (☰)
        menuButton.setFont(new Font("Arial", Font.PLAIN, 24));
        menuButton.setForeground(headerText);
        menuButton.setBackground(headerBackground);
        menuButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);

        final JPopupMenu menu = buildMenu();
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.show(menuButton, 0, menuButton.getHeight());
            }
        });

        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(headerText);

        header.add(menuButton, BorderLayout.WEST);
        header.add(titleLabel, BorderLayout.CENTER);
        return header;
    }

    /**
     * this method show the buttons, and can only select one item per calculator mode.
     */
    private JPopupMenu buildMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(buildMenuItem(STANDARD));
        menu.add(buildMenuItem(SCIENTIFIC));
        menu.add(buildMenuItem(PROGRAMMER));
        menu.add(buildMenuItem(STORAGE));
        return menu;
    }

    /**
     * This method allow user to switches the CardLayout to the given calculator mode when clicked.
     */
    private JMenuItem buildMenuItem(final String mode) {
        JMenuItem item = new JMenuItem(mode);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, mode);
                titleLabel.setText(mode);
            }
        });
        return item;
    }
}
