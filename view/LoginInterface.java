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
                createAccountLabel.setForeground(new Color(30, 30, 180));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                createAccountLabel.setForeground(new Color(60, 60, 200));
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
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 80, 100, 80));

        // Title
        JLabel titleLabel = new JLabel("HELLO!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to your account");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Email field
        JLabel emailTextLabel = new JLabel("Email:");
        emailTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailTextLabel.setForeground(new Color(80, 80, 80));
        
        emailField = new RoundedTextField(20);
        emailField.setMaximumSize(new Dimension(420, 50));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            emailField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        panel.add(emailTextLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(emailField);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Password field
        JLabel passwordTextLabel = new JLabel("Password:");
        passwordTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordTextLabel.setForeground(new Color(80, 80, 80));
        
        passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(420, 50));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            passwordField.getBorder(), 
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        panel.add(passwordTextLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Sign In button
        signInButton = new RoundedButton("Sign In");
        signInButton.setFont(new Font("Arial", Font.BOLD, 18));
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(new Color(90, 90, 220));
        signInButton.setPreferredSize(new Dimension(160, 45));
        signInButton.setMaximumSize(new Dimension(160, 45));
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(signInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Create account label - now clickable
        createAccountLabel = new JLabel("<html><center>Don't have an account? <b><u>Create one now</u></b></center></html>");
        createAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountLabel.setForeground(new Color(60, 60, 200));

        panel.add(createAccountLabel);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 60, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(130, 70, 130, 70));

        // Welcome title
        JLabel welcomeLabel = new JLabel("WELCOME BACK !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 44));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Description text
        String[] lines = {
            "Welcome to the Foundation of",
            "Everything Digital GATEO.",
            "Here, you hold the power of the",
            "processor.",
            "Assemble logic gates and breathe",
            "life into the binary world."
        };

        for (String line : lines) {
            JLabel textLabel = new JLabel(line);
            textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            textLabel.setForeground(new Color(180, 180, 180));
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // Add some spacing and demo credentials info
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JLabel demoLabel = new JLabel("<html><center><i>Demo: Try with any registered account</i></center></html>");
        demoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        demoLabel.setForeground(new Color(200, 200, 100));
        demoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(demoLabel);

        return panel;
    }

    // Custom rounded text field
    class RoundedTextField extends JTextField {
        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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

    // Custom rounded password field
    class RoundedPasswordField extends JPasswordField {
        public RoundedPasswordField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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

    // Custom rounded button
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
        // Initialize database
        DatabaseHelper.initializeDatabase();
        
        SwingUtilities.invokeLater(() -> {
            LoginInterface frame = new LoginInterface();
            frame.setVisible(true);
        });
    }
}