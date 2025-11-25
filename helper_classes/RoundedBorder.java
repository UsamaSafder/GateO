package helper_classes;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder implements Border {
    private Color color;
    private int radius;
    private int thickness;
    
    public RoundedBorder(Color c, int r, int t) {
        color = c;
        radius = r;
        thickness = t;
    }
    
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }
    
    public boolean isBorderOpaque() {
        return false;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x + thickness/2, y + thickness/2, 
                       width - thickness, height - thickness, radius, radius);
    }
}