package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GateOSplash extends JWindow {
    private static final Color BG = new Color(5, 10, 20);
    private static final Color ACCENT = new Color(120, 100, 255);
    private static final Color ACCENT_GLOW = new Color(150, 130, 255);
    private static final Color ACCENT_DIM = new Color(140, 180, 255);
    private static final Color PARTICLE_COLOR = new Color(100, 200, 255);

    private final String title = "GATE O";
    private final String subtitle = "BUILDING THE CIRCUIT LOGIC FLOW";

    private int revealTitle = 0;
    private int revealSub = 0;
    private float alpha = 1f;
    private float logoRotation = 0f;
    private float pulseScale = 1f;
    private boolean pulsing = true;
    private float[] particleX = new float[30];
    private float[] particleY = new float[30];
    private float[] particleSpeed = new float[30];
    private float[] particleAlpha = new float[30];

    public GateOSplash() {
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        
        initParticles();

        Timer animation = new Timer(30, e -> {
            logoRotation += 0.01f;
            if (pulsing) {
                pulseScale = 1f + 0.08f * (float)Math.sin(logoRotation * 3);
            }
            
            for (int i = 0; i < particleX.length; i++) {
                particleY[i] -= particleSpeed[i];
                particleAlpha[i] -= 0.008f;
                if (particleY[i] < -20 || particleAlpha[i] <= 0) {
                    resetParticle(i);
                }
            }
            repaint();
        });
        animation.start();

        Timer typing = new Timer(90, null);
        typing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (revealTitle < title.length()) {
                    revealTitle++;
                } else if (revealSub < subtitle.length()) {
                    revealSub++;
                } else {
                    ((Timer)e.getSource()).stop();
                    pulsing = false;
                    Timer visible = new Timer(3000, ev -> startFade());
                    visible.setRepeats(false);
                    visible.start();
                }
                repaint();
            }
        });
        typing.setInitialDelay(100);
        typing.start();
    }
    
    private void initParticles() {
        for (int i = 0; i < particleX.length; i++) {
            resetParticle(i);
        }
    }
    
    private void resetParticle(int i) {
        particleX[i] = 50 + (float)(Math.random() * 350);
        particleY[i] = 400 + (float)(Math.random() * 150);
        particleSpeed[i] = 0.5f + (float)(Math.random() * 1.5f);
        particleAlpha[i] = 0.3f + (float)(Math.random() * 0.5f);
    }

    private void startFade() {
        Timer fade = new Timer(40, null);
        fade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.035f;
                if (alpha <= 0f) {
                    alpha = 0f;
                    ((Timer)e.getSource()).stop();
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        openNextscreen();
                    });
                }
                repaint();
            }
        });
        fade.start();
    }

    public void openNextscreen(){
        try {
            LoginInterface login = new LoginInterface();
            login.setVisible(true);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error opening the login screen: " + ex.getMessage());
            
            // Fallback: Create a simple frame
            JFrame fallback = new JFrame("Gate O");
            fallback.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fallback.setSize(800, 600);
            fallback.setLocationRelativeTo(null);
            fallback.setVisible(true);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int w = getWidth();
            int h = getHeight();

            // Gradient background
            GradientPaint bgGradient = new GradientPaint(0, 0, BG, w, h, new Color(15, 25, 45));
            g2.setPaint(bgGradient);
            g2.fillRect(0, 0, w, h);
            
            // Draw animated particles
            drawParticles(g2);
            
            // Subtle grid overlay
            g2.setColor(new Color(255, 255, 255, 8));
            g2.setStroke(new BasicStroke(1f));
            for (int i = 0; i < w; i += 40) {
                g2.drawLine(i, 0, i, h);
            }
            for (int i = 0; i < h; i += 40) {
                g2.drawLine(0, i, w, i);
            }

            int logoCenterX = 250;
            int logoCenterY = h/2 - 10;
            int outerR = 130;

            // Save transform for logo rotation
            AffineTransform oldTransform = g2.getTransform();
            
            // Apply rotation and pulse
            g2.translate(logoCenterX, logoCenterY);
            g2.rotate(logoRotation);
            g2.scale(pulseScale, pulseScale);
            g2.translate(-logoCenterX, -logoCenterY);

            // Draw glowing outer ring (multiple layers for glow effect)
            for (int i = 5; i > 0; i--) {
                g2.setStroke(new BasicStroke(24f + i * 3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 15 * i));
                Ellipse2D glow = new Ellipse2D.Double(logoCenterX - outerR, logoCenterY - outerR, outerR*2, outerR*2);
                g2.draw(glow);
            }

            // Main outer ring
            g2.setStroke(new BasicStroke(24f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(ACCENT);
            Ellipse2D outer = new Ellipse2D.Double(logoCenterX - outerR, logoCenterY - outerR, outerR*2, outerR*2);
            g2.draw(outer);

            // Gap for G shape
            g2.setColor(BG);
            Arc2D gap = new Arc2D.Double(logoCenterX - outerR - 12, logoCenterY - outerR - 12, (outerR+12)*2, (outerR+12)*2, -45, 95, Arc2D.PIE);
            g2.fill(gap);

            // Inner horizontal bar with glow
            g2.setStroke(new BasicStroke(28f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(ACCENT_GLOW.getRed(), ACCENT_GLOW.getGreen(), ACCENT_GLOW.getBlue(), 100));
            Line2D barGlow = new Line2D.Double(logoCenterX - 15, logoCenterY + 22, logoCenterX + 70, logoCenterY + 22);
            g2.draw(barGlow);
            
            g2.setStroke(new BasicStroke(20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(ACCENT);
            Line2D bar = new Line2D.Double(logoCenterX - 15, logoCenterY + 22, logoCenterX + 70, logoCenterY + 22);
            g2.draw(bar);

            // Small circle with glow
            g2.setColor(new Color(ACCENT_GLOW.getRed(), ACCENT_GLOW.getGreen(), ACCENT_GLOW.getBlue(), 150));
            g2.fill(new Ellipse2D.Double(logoCenterX + outerR - 44, logoCenterY - outerR + 18, 36, 36));
            g2.setColor(ACCENT);
            g2.fill(new Ellipse2D.Double(logoCenterX + outerR - 40, logoCenterY - outerR + 22, 30, 30));

            // Restore transform
            g2.setTransform(oldTransform);

            // Draw title text with shadow and glow
            Font titleFont = new Font("SansSerif", Font.BOLD, 84);
            g2.setFont(titleFont);

            String shownTitle = title.substring(0, Math.min(revealTitle, title.length()));
            int titleX = logoCenterX + outerR + 50;
            int titleY = logoCenterY;
            
            // Text shadow
            g2.setColor(new Color(0, 0, 0, 120));
            g2.drawString(shownTitle, titleX + 3, titleY + 3);
            
            // Text glow
            g2.setColor(new Color(ACCENT_GLOW.getRed(), ACCENT_GLOW.getGreen(), ACCENT_GLOW.getBlue(), 150));
            g2.drawString(shownTitle, titleX - 1, titleY - 1);
            g2.drawString(shownTitle, titleX + 1, titleY + 1);
            
            // Main text
            g2.setColor(ACCENT);
            g2.drawString(shownTitle, titleX, titleY);

            // Subtitle with enhanced styling
            Font subFont = new Font("SansSerif", Font.ITALIC | Font.BOLD, 22);
            g2.setFont(subFont);
            
            String shownSub = subtitle.substring(0, Math.min(revealSub, subtitle.length()));
            int subX = titleX + 8;
            int subY = titleY + 44;
            
            // Subtitle shadow
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString(shownSub, subX + 2, subY + 2);
            
            // Subtitle glow
            g2.setColor(new Color(ACCENT_DIM.getRed(), ACCENT_DIM.getGreen(), ACCENT_DIM.getBlue(), 180));
            g2.drawString(shownSub, subX - 1, subY);
            
            // Main subtitle
            g2.setColor(ACCENT_DIM);
            g2.drawString(shownSub, subX, subY);
            
            // Draw cursor blink effect
            if (revealTitle < title.length() || revealSub < subtitle.length()) {
                if ((System.currentTimeMillis() / 300) % 2 == 0) {
                    g2.setColor(ACCENT_GLOW);
                    if (revealTitle < title.length()) {
                        FontMetrics fm = g2.getFontMetrics(titleFont);
                        int cursorX = titleX + fm.stringWidth(shownTitle);
                        g2.fillRect(cursorX + 5, titleY - 60, 4, 70);
                    } else if (revealSub < subtitle.length()) {
                        FontMetrics fm = g2.getFontMetrics(subFont);
                        int cursorX = subX + fm.stringWidth(shownSub);
                        g2.fillRect(cursorX + 3, subY - 18, 3, 25);
                    }
                }
            }
            
            // Version/footer text
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            g2.setColor(new Color(100, 140, 200, 150));
            String version = "v1.0 | Digital Logic Simulator";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(version, w - fm.stringWidth(version) - 30, h - 30);

        } finally {
            g2.dispose();
        }
    }
    
    private void drawParticles(Graphics2D g2) {
        for (int i = 0; i < particleX.length; i++) {
            int size = 2 + (i % 3);
            g2.setColor(new Color(
                PARTICLE_COLOR.getRed(), 
                PARTICLE_COLOR.getGreen(), 
                PARTICLE_COLOR.getBlue(), 
                (int)(particleAlpha[i] * 255)
            ));
            g2.fillOval((int)particleX[i], (int)particleY[i], size, size);
            
            // Add glow
            g2.setColor(new Color(
                PARTICLE_COLOR.getRed(), 
                PARTICLE_COLOR.getGreen(), 
                PARTICLE_COLOR.getBlue(), 
                (int)(particleAlpha[i] * 100)
            ));
            g2.fillOval((int)particleX[i] - 1, (int)particleY[i] - 1, size + 2, size + 2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GateOSplash s = new GateOSplash();
            s.setVisible(true);
        });
    }
}