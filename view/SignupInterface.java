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
        setSize(1100, 800);
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
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLink.setForeground(new Color(99, 102, 241));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLink.setForeground(new Color(129, 140, 248));
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
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 80, 40, 80));

        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titleLabel.setForeground(new Color(30, 30, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Join the GATEO community today");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(107, 114, 128));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(new Color(55, 65, 81));

        usernameField = new RoundedTextField(20);
        usernameField.setMaximumSize(new Dimension(400, 50));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            usernameField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        panel.add(userLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Email field
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(55, 65, 81));

        emailField = new RoundedTextField(20);
        emailField.setMaximumSize(new Dimension(400, 50));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            emailField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        panel.add(emailLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(new Color(55, 65, 81));

        passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            passwordField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        panel.add(passLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirm Password field
        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPassLabel.setForeground(new Color(55, 65, 81));

        confirmPasswordField = new RoundedPasswordField(20);
        confirmPasswordField.setMaximumSize(new Dimension(400, 50));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            confirmPasswordField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        panel.add(confirmPassLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(confirmPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sign Up button
        signupButton = new RoundedButton("Sign Up");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(16, 185, 129));
        signupButton.setPreferredSize(new Dimension(400, 50));
        signupButton.setMaximumSize(new Dimension(400, 50));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(signupButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Login link
        loginLink = new JLabel("<html><center>Already have an account? <b>Login here</b></center></html>");
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLink.setHorizontalAlignment(SwingConstants.CENTER);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.setForeground(new Color(129, 140, 248));

        panel.add(loginLink);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(Box.createVerticalGlue()); // Push everything up

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Modern gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(5, 150, 105),
                    0, getHeight(), new Color(16, 185, 129)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 70, 100, 70));

        JLabel welcomeLabel = new JLabel("Start Your Journey");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 44));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel taglineLabel = new JLabel("Build the Digital Future");
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        taglineLabel.setForeground(new Color(209, 250, 229));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(taglineLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        String[] lines = {
            "Design sophisticated digital circuits",
            "Simulate and test logic gates in real-time",
            "Analyze complex binary systems",
            "Join a community of innovators",
            "Access powerful circuit design tools",
            "Learn digital logic interactively"
        };

        for (String line : lines) {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            linePanel.setOpaque(false);
            linePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Modern checkmark bullet
            JLabel bullet = new JLabel("✓  ");
            bullet.setFont(new Font("Segoe UI", Font.BOLD, 18));
            bullet.setForeground(new Color(167, 243, 208));
            
            JLabel textLabel = new JLabel(line);
            textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textLabel.setForeground(new Color(209, 250, 229));
            
            linePanel.add(bullet);
            linePanel.add(textLabel);
            
            panel.add(linePanel);
            panel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        return panel;
    }

    // Modern rounded text field with focus effects
    class RoundedTextField extends JTextField {
        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            setForeground(new Color(31, 41, 55));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Subtle shadow
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fillRoundRect(2, 3, getWidth() - 4, getHeight() - 4, 12, 12);
            
            g2.setColor(new Color(249, 250, 251));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (hasFocus()) {
                g2.setColor(new Color(16, 185, 129));
                g2.setStroke(new BasicStroke(2.0f));
            } else {
                g2.setColor(new Color(229, 231, 235));
                g2.setStroke(new BasicStroke(1.5f));
            }
            
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
            g2.dispose();
        }
    }

    class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            setForeground(new Color(31, 41, 55));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Subtle shadow
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fillRoundRect(2, 3, getWidth() - 4, getHeight() - 4, 12, 12);
            
            g2.setColor(new Color(249, 250, 251));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (hasFocus()) {
                g2.setColor(new Color(16, 185, 129));
                g2.setStroke(new BasicStroke(2.0f));
            } else {
                g2.setColor(new Color(229, 231, 235));
                g2.setStroke(new BasicStroke(1.5f));
            }
            
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
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

            // Modern shadow
            g2.setColor(new Color(16, 185, 129, 40));
            g2.fillRoundRect(0, 4, getWidth(), getHeight() - 4, 12, 12);

            if (getModel().isPressed()) {
                g2.setColor(new Color(5, 150, 105));
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(20, 201, 140));
            } else {
                g2.setColor(getBackground());
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight() - 4, 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            // No border for modern flat design
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignupInterface().setVisible(true);
        });
    }
}