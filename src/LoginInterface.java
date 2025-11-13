import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginInterface extends JFrame {

    public LoginInterface() {
        setTitle("GATEO Login");
        setSize(1100, 700); // Increased window size
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
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 80, 100, 80)); // bigger spacing

        // Title
        JLabel titleLabel = new JLabel("HELLO!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48)); // bigger font
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to your account");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // increased font size
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 60)));

        // Username field
        RoundedTextField usernameField = new RoundedTextField(20);
        usernameField.setMaximumSize(new Dimension(420, 60)); // larger width/height
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Password field
        RoundedPasswordField passwordField = new RoundedPasswordField(20);
        passwordField.setMaximumSize(new Dimension(420, 60));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Sign In button
        RoundedButton signInButton = new RoundedButton("Sign In");
        signInButton.setFont(new Font("Arial", Font.BOLD, 20));
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(new Color(90, 90, 220));
        signInButton.setPreferredSize(new Dimension(160, 50));
        signInButton.setMaximumSize(new Dimension(160, 50));
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(signInButton);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Create account label
        JLabel createAccountLabel = new JLabel("<html><center>Don't have an account?<b> Create</b></center></html>");
        createAccountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(createAccountLabel);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 60, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(130, 70, 130, 70)); // more padding

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
            textLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // larger text
            textLabel.setForeground(new Color(180, 180, 180));
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        return panel;
    }

    // Custom rounded text field
    class RoundedTextField extends JTextField {
        private Shape shape;

        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 18));
            setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
            g2.dispose();
        }
    }

    // Custom rounded password field
    class RoundedPasswordField extends JPasswordField {
        private Shape shape;

        public RoundedPasswordField(int size) {
            super(size);
            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 18));
            setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
            g2.dispose();
        }
    }

    // Custom rounded button
    class RoundedButton extends JButton {
        private Shape shape;

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

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();

            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginInterface frame = new LoginInterface();
            frame.setVisible(true);
        });
    }
}
