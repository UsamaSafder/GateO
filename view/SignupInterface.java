package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import helper_classes.DatabaseHelper;

public class SignupInterface extends JFrame {
    private RoundedTextField usernameField;
    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private RoundedPasswordField confirmPasswordField;
    private RoundedButton signupButton;
    private JLabel loginLink;

    public SignupInterface() {
        setTitle("GATEO - Sign Up");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
        setupEventListeners();
    }

    private void setupEventListeners() {
        signupButton.addActionListener(e -> performSignup());
        
        // Enter key support
        usernameField.addActionListener(e -> performSignup());
        emailField.addActionListener(e -> performSignup());
        passwordField.addActionListener(e -> performSignup());
        confirmPasswordField.addActionListener(e -> performSignup());

        // Login link
        loginLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginInterface().setVisible(true);
            }
        });
    }

    private void performSignup() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.setText("");
            confirmPasswordField.requestFocus();
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DatabaseHelper.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            usernameField.requestFocus();
            return;
        }

        if (DatabaseHelper.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered!", "Error", JOptionPane.ERROR_MESSAGE);
            emailField.setText("");
            emailField.requestFocus();
            return;
        }

        // Register user
        if (DatabaseHelper.registerUser(username, email, password)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginInterface().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed! Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));

        // Title
        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Join the GATEO community");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Username field
        usernameField = new RoundedTextField(20);
        usernameField.setMaximumSize(new Dimension(400, 50));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            usernameField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(userLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Email field
        emailField = new RoundedTextField(20);
        emailField.setMaximumSize(new Dimension(400, 50));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            emailField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(emailLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password field
        passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            passwordField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(passLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirm Password field
        confirmPasswordField = new RoundedPasswordField(20);
        confirmPasswordField.setMaximumSize(new Dimension(400, 50));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            confirmPasswordField.getBorder(), 
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(confirmPassLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(confirmPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sign Up button
        signupButton = new RoundedButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(90, 90, 220));
        signupButton.setPreferredSize(new Dimension(160, 45));
        signupButton.setMaximumSize(new Dimension(160, 45));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(signupButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Login link
        loginLink = new JLabel("<html><center>Already have an account? <b><u>Login here</u></b></center></html>");
        loginLink.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.setHorizontalAlignment(SwingConstants.CENTER);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setForeground(new Color(60, 60, 200));

        panel.add(loginLink);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 60, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(120, 70, 120, 70));

        JLabel welcomeLabel = new JLabel("WELCOME!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 44));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        String[] lines = {
            "Create your account and unlock",
            "the full potential of GATEO.",
            "Design, simulate, and analyze",
            "digital circuits with ease.",
            "Join thousands of developers",
            "in the digital logic revolution."
        };

        for (String line : lines) {
            JLabel textLabel = new JLabel(line);
            textLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            textLabel.setForeground(new Color(180, 180, 180));
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        return panel;
    }

    // Reuse your custom component classes from LoginInterface
    class RoundedTextField extends JTextField {
        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(150, 150, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);
            g2.dispose();
        }
    }

    class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(150, 150, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);
            g2.dispose();
        }
    }

    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            g2.dispose();
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            // No border for button
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignupInterface().setVisible(true);
        });
    }
}