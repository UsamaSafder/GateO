package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import helper_classes.DatabaseHelper;

public class LoginInterface extends JFrame {

    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private RoundedButton signInButton;
    private JLabel createAccountLabel;

    public LoginInterface() {
        setTitle("GATEO Login");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with split layout
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // Left panel - Login form
        JPanel leftPanel = createLeftPanel();

        // Right panel - Welcome message
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
        
        // Add action listeners
        setupEventListeners();
    }

    private void setupEventListeners() {
        // Sign In button action
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Enter key support for both fields
        emailField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Create account link
        createAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openSignupPage();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                createAccountLabel.setForeground(new Color(99, 102, 241));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                createAccountLabel.setForeground(new Color(129, 140, 248));
            }
        });
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validation
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both email and password!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check credentials against database
        if (DatabaseHelper.validateUser(email, password)) {
            // Successful login
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Close login window and open next screen
            dispose();
            openNextScreen();
        } else {
            // Failed login
            JOptionPane.showMessageDialog(this, 
                "Invalid email or password!\n\nPlease check your credentials or create a new account.", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
            
            // Clear password field
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    private void openSignupPage() {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Open Signup Interface
                    SignupInterface signup = new SignupInterface();
                    signup.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error opening signup page: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void openNextScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Open LogicGateSimulator (your main application)
                    LogicGateSimulator mainApp = new LogicGateSimulator();
                    mainApp.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error opening main application: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    
                    // Fallback: Create a simple success window
                    JFrame successFrame = new JFrame("Gate O - Main Application");
                    successFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    successFrame.setSize(800, 600);
                    successFrame.setLocationRelativeTo(null);
                    
                    JPanel panel = new JPanel(new BorderLayout());
                    JLabel successLabel = new JLabel("Welcome to Gate O Main Application!", JLabel.CENTER);
                    successLabel.setFont(new Font("Arial", Font.BOLD, 24));
                    successLabel.setForeground(new Color(0, 100, 0));
                    
                    panel.add(successLabel, BorderLayout.CENTER);
                    successFrame.add(panel);
                    successFrame.setVisible(true);
                }
            }
        });
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(100, 80, 100, 80));

        // Title with modern gradient effect (simulated with color)
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(new Color(30, 30, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to continue to GATEO");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(107, 114, 128));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Email field
        JLabel emailTextLabel = new JLabel("Email Address");
        emailTextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTextLabel.setForeground(new Color(55, 65, 81));
        
        emailField = new RoundedTextField(20);
        emailField.setMaximumSize(new Dimension(420, 52));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            emailField.getBorder(), 
            BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));

        panel.add(emailTextLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 28)));

        // Password field
        JLabel passwordTextLabel = new JLabel("Password");
        passwordTextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextLabel.setForeground(new Color(55, 65, 81));
        
        passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(420, 52));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            passwordField.getBorder(), 
            BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));

        panel.add(passwordTextLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 45)));

        // Sign In button
        signInButton = new RoundedButton("Sign In");
        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(new Color(79, 70, 229));
        signInButton.setPreferredSize(new Dimension(420, 80));
        signInButton.setMaximumSize(new Dimension(420, 80));
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(signInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 32)));

        // Create account label - now clickable
        createAccountLabel = new JLabel("<html><center>Don't have an account? <b>Create one now</b></center></html>");
        createAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountLabel.setForeground(new Color(129, 140, 248));

        panel.add(createAccountLabel);

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
                    0, 0, new Color(79, 70, 229),
                    0, getHeight(), new Color(99, 102, 241)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(130, 70, 130, 70));

        // Welcome title
        JLabel welcomeLabel = new JLabel("GATEO");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 56));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tagline
        JLabel taglineLabel = new JLabel("Digital Logic Reimagined");
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        taglineLabel.setForeground(new Color(224, 231, 255));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(taglineLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Description text with modern spacing
        String[] lines = {
            "Build the foundation of digital circuits",
            "Design and simulate logic gates",
            "Experience the power of binary processing",
            "Create complex digital systems with ease"
        };

        for (String line : lines) {
            JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            linePanel.setOpaque(false);
            linePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Modern bullet point
            JLabel bullet = new JLabel("●  ");
            bullet.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            bullet.setForeground(new Color(167, 139, 250));
            
            JLabel textLabel = new JLabel(line);
            textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textLabel.setForeground(new Color(224, 231, 255));
            
            linePanel.add(bullet);
            linePanel.add(textLabel);
            
            panel.add(linePanel);
            panel.add(Box.createRigidArea(new Dimension(0, 14)));
        }

        // Add decorative element
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JLabel demoLabel = new JLabel("Try with any registered account");
        demoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        demoLabel.setForeground(new Color(199, 210, 254));
        demoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(demoLabel);

        return panel;
    }

    // Custom rounded text field with modern styling
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
            
            // Modern shadow effect
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
                g2.setColor(new Color(79, 70, 229));
                g2.setStroke(new BasicStroke(2.0f));
            } else {
                g2.setColor(new Color(229, 231, 235));
                g2.setStroke(new BasicStroke(1.5f));
            }
            
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
            g2.dispose();
        }
    }

    // Custom rounded password field with modern styling
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
            
            // Modern shadow effect
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
                g2.setColor(new Color(79, 70, 229));
                g2.setStroke(new BasicStroke(2.0f));
            } else {
                g2.setColor(new Color(229, 231, 235));
                g2.setStroke(new BasicStroke(1.5f));
            }
            
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
            g2.dispose();
        }
    }

    // Custom rounded button with modern hover effects
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
            g2.setColor(new Color(79, 70, 229, 40));
            g2.fillRoundRect(0, 4, getWidth(), getHeight() - 4, 12, 12);

            if (getModel().isPressed()) {
                g2.setColor(new Color(67, 56, 202));
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(99, 102, 241));
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
        // Initialize database
        DatabaseHelper.initializeDatabase();
        
        SwingUtilities.invokeLater(() -> {
            LoginInterface frame = new LoginInterface();
            frame.setVisible(true);
        });
    }
}