package view;

import controller.LogicGateController;
import model.ComponentType;
import model.GateComponent;
import model.ConnectionWire;
import helper_classes.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Map;

public class LogicGateSimulator extends JFrame {
    private DrawingPanel drawingPanel;
    private JPanel outputControlPanel;
    private LogicGateController controller;
    
    public LogicGateSimulator() {
        // Initialize controller (it will create the model)
        controller = new LogicGateController(this);
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Logic Gate Simulator - GATE O");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 24));
        
        // Main scrollable panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(18, 18, 24));
        
        // Header
        JPanel header = createHeader();
        mainPanel.add(header);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Content area with controls and drawing panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.setBackground(new Color(18, 18, 24));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        
        // Left control panel
        JPanel controlPanel = createControlPanel();
        contentPanel.add(controlPanel, BorderLayout.WEST);
        
        // Drawing panel
        drawingPanel = new DrawingPanel(controller);
        contentPanel.add(drawingPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel);
        
        // Add to scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        add(scrollPane);
        setVisible(true);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(30, 35, 50),
                    getWidth(), 0, new Color(45, 50, 70)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        header.setOpaque(false);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15),
            BorderFactory.createCompoundBorder(
                new RoundedBorder(new Color(80, 120, 200, 100), 20, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
            )
        ));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel title = new JLabel("GATE O - Logic Gate Simulator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(120, 200, 255));
        header.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton clearBtn = new JButton("CLEAR CANVAS");
        JButton run_Sim = new JButton("Run Simulation");
        JButton save_proj = new JButton("Save Project");
        JButton load_proj = new JButton("Load Project");
        JButton exportbutton = new JButton("Export as PNG");
        
        styleButton(save_proj, new Color(255, 70, 100));
        styleButton(clearBtn, new Color(255, 70, 100));
        styleButton(run_Sim, new Color(100, 200, 100));
        styleButton(load_proj, new Color(255, 70, 100));
        styleButton(exportbutton, new Color(255, 70, 100));
        
        clearBtn.addActionListener(e -> controller.clearAll());
        run_Sim.addActionListener(e -> controller.runSimulation());
        
        buttonPanel.add(save_proj);
        buttonPanel.add(load_proj);
        buttonPanel.add(run_Sim);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exportbutton);
        
        header.add(buttonPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 30, 42),
                    0, getHeight(), new Color(30, 35, 50)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(70, 100, 180, 120), 25, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(300, 600));
        
        // Input Controls
        panel.add(createSectionLabel("INPUT CONTROLS"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createInputSection());
        panel.add(Box.createVerticalStrut(25));
        
        // Output Controls
        panel.add(createSectionLabel("OUTPUT CONTROLS"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createOutputSection());
        panel.add(Box.createVerticalStrut(25));
        
        // Output Display Panel
        panel.add(createSectionLabel("OUTPUT STATUS"));
        panel.add(Box.createVerticalStrut(15));
        outputControlPanel = createOutputStatusPanel();
        panel.add(outputControlPanel);
        panel.add(Box.createVerticalStrut(25));
        
        // Logic Gates
        panel.add(createSectionLabel("LOGIC GATES"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createGatesSection());
        panel.add(Box.createVerticalStrut(25));
        
        // Connection Tool
        panel.add(createSectionLabel("CONNECTION TOOL"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createConnectionTool());
        
        return panel;
    }
    
    private JPanel createOutputStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(100, 150, 200), 15, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(260, 200));
        
        // Initial message
        JLabel initialLabel = new JLabel("Run simulation to see outputs");
        initialLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        initialLabel.setForeground(new Color(180, 180, 200));
        initialLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(initialLabel);
        
        return panel;
    }
    
    public void updateOutputPanel() {
        if (outputControlPanel != null) {
            outputControlPanel.removeAll();
            
            Map<String, Boolean> outputStates = controller.getOutputStates();
            
            if (outputStates.isEmpty()) {
                JLabel noOutputsLabel = new JLabel("No outputs in circuit");
                noOutputsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                noOutputsLabel.setForeground(new Color(180, 180, 200));
                noOutputsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                outputControlPanel.add(noOutputsLabel);
            } else {
                int outputCount = 1;
                for (Map.Entry<String, Boolean> entry : outputStates.entrySet()) {
                    JPanel outputItem = new JPanel(new BorderLayout());
                    outputItem.setOpaque(false);
                    outputItem.setMaximumSize(new Dimension(240, 30));
                    
                    JLabel nameLabel = new JLabel("Output " + outputCount + ": ");
                    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    nameLabel.setForeground(new Color(200, 220, 255));
                    
                    JLabel stateLabel = new JLabel(entry.getValue() ? "HIGH (1)" : "LOW (0)");
                    stateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    stateLabel.setForeground(entry.getValue() ? 
                        new Color(80, 255, 150) : new Color(255, 100, 130));
                    
                    outputItem.add(nameLabel, BorderLayout.WEST);
                    outputItem.add(stateLabel, BorderLayout.EAST);
                    outputControlPanel.add(outputItem);
                    outputControlPanel.add(Box.createVerticalStrut(5));
                    outputCount++;
                }
            }
            
            outputControlPanel.revalidate();
            outputControlPanel.repaint();
        }
    }
    
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(new Color(150, 220, 255));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel createInputSection() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(260, 85));
        
        panel.add(createComponentButton("HIGH", "1", new Color(80, 255, 150), ComponentType.HIGH_INPUT));
        panel.add(createComponentButton("LOW", "0", new Color(255, 100, 130), ComponentType.LOW_INPUT));
        
        return panel;
    }
    
    private JPanel createOutputSection() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(260, 85));
        
        panel.add(createComponentButton("BULB","", new Color(255, 200, 80), ComponentType.BULB));
        panel.add(createComponentButton("OUTPUT", "", new Color(150, 180, 255), ComponentType.OUTPUT));
        
        return panel;
    }
    
    private JPanel createGatesSection() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 12, 12));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(260, 300));
        
        panel.add(createGateButton("NOT", ComponentType.NOT, new Color(255, 100, 150)));
        panel.add(createGateButton("AND", ComponentType.AND, new Color(100, 170, 255)));
        panel.add(createGateButton("OR", ComponentType.OR, new Color(100, 255, 200)));
        panel.add(createGateButton("NAND", ComponentType.NAND, new Color(255, 180, 100)));
        panel.add(createGateButton("NOR", ComponentType.NOR, new Color(200, 120, 255)));
        panel.add(createGateButton("XOR", ComponentType.XOR, new Color(255, 230, 100)));
        
        return panel;
    }
    
    private JPanel createConnectionTool() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 140, 50),
                    getWidth(), getHeight(), new Color(255, 100, 80)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(new Color(255, 180, 100), 20, 3),
            BorderFactory.createEmptyBorder(15, 5, 15, 5)
        ));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setMaximumSize(new Dimension(260, 85));
        
        JLabel textLabel = new JLabel("WIRE TOOL", SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        textLabel.setForeground(Color.WHITE);
        panel.add(textLabel, BorderLayout.SOUTH);
        
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                drawingPanel.setConnectionMode(!drawingPanel.isConnectionMode());
                if (drawingPanel.isConnectionMode()) {
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(new Color(255, 220, 150), 20, 4),
                        BorderFactory.createEmptyBorder(15, 5, 15, 5)
                    ));
                } else {
                    panel.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(new Color(255, 180, 100), 20, 3),
                        BorderFactory.createEmptyBorder(15, 5, 15, 5)
                    ));
                }
            }
        });
        
        return panel;
    }
    
    private JPanel createComponentButton(String label, String symbol, Color color, ComponentType type) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, color.brighter(),
                    getWidth(), getHeight(), color.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(color.brighter(), 18, 2),
            BorderFactory.createEmptyBorder(12, 5, 12, 5)
        ));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel symbolLabel = new JLabel(symbol, SwingConstants.CENTER);
        symbolLabel.setFont(new Font("Arial", Font.BOLD, 32));
        symbolLabel.setForeground(Color.WHITE);
        panel.add(symbolLabel, BorderLayout.CENTER);
        
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        textLabel.setForeground(Color.WHITE);
        panel.add(textLabel, BorderLayout.SOUTH);
        
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.addComponent(type, 100, 100);
            }
        });
        
        return panel;
    }
    
    private JPanel createGateButton(String name, ComponentType type, Color color) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, color.brighter(),
                    getWidth(), getHeight(), color.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                
                // Draw gate preview    
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f));
                
                int w = getWidth();
                int h = getHeight() - 20;
                int cx = w / 2;
                int cy = h / 2;
                
                drawGateShape(g2, type, cx, cy, 30);
            }
        };
        
        panel.setOpaque(false);
        panel.setBorder(new RoundedBorder(color.brighter(), 18, 2));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setPreferredSize(new Dimension(120, 95));
        
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.addComponent(type, 100, 100);
            }
        });
        
        return panel;
    }
    
    public void drawGateShape(Graphics2D g2, ComponentType type, int cx, int cy, int size) {
        switch (type) {
            case NOT:
                int[] xPoints = {cx - size, cx + size, cx - size};
                int[] yPoints = {cy - size/2, cy, cy + size/2};
                g2.drawPolygon(xPoints, yPoints, 3);
                g2.drawOval(cx + size, cy - 4, 8, 8);
                g2.drawLine(cx - size - 10, cy, cx - size, cy);
                g2.drawLine(cx + size + 8, cy, cx + size + 18, cy);
                break;
                
            case AND:
                g2.drawLine(cx - size, cy - size/2, cx - size, cy + size/2);
                g2.drawLine(cx - size, cy - size/2, cx, cy - size/2);
                g2.drawLine(cx - size, cy + size/2, cx, cy + size/2);
                g2.drawArc(cx - size/2, cy - size/2, size, size, -90, 180);
                g2.drawLine(cx - size - 10, cy - size/4, cx - size, cy - size/4);
                g2.drawLine(cx - size - 10, cy + size/4, cx - size, cy + size/4);
                g2.drawLine(cx + size/2, cy, cx + size/2 + 10, cy);
                break;
                
            case OR:
                Path2D path = new Path2D.Double();
                path.moveTo(cx - size, cy - size/2);
                path.quadTo(cx - size/2, cy - size/2, cx, cy - size/3);
                path.quadTo(cx + size/2, cy, cx + size/2, cy);
                path.quadTo(cx + size/2, cy, cx, cy + size/3);
                path.quadTo(cx - size/2, cy + size/2, cx - size, cy + size/2);
                path.quadTo(cx - size/2, cy, cx - size, cy - size/2);
                g2.draw(path);
                g2.drawLine(cx - size - 10, cy - size/4, cx - size + 5, cy - size/4);
                g2.drawLine(cx - size - 10, cy + size/4, cx - size + 5, cy + size/4);
                g2.drawLine(cx + size/2, cy, cx + size/2 + 10, cy);
                break;
                
            case NAND:
                drawGateShape(g2, ComponentType.AND, cx - 4, cy, size);
                g2.drawOval(cx + size/2 + 6, cy - 4, 8, 8);
                break;
                
            case NOR:
                drawGateShape(g2, ComponentType.OR, cx - 4, cy, size);
                g2.drawOval(cx + size/2 + 6, cy - 4, 8, 8);
                break;
                
            case XOR:
                drawGateShape(g2, ComponentType.OR, cx, cy, size);
                g2.drawArc(cx - size - 5, cy - size/2, 10, size, -90, 180);
                break;
        }
    }
    
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(color.brighter(), 12, 2),
            BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(255, 255, 255));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
    }
    
    public void refreshView() {
        if (drawingPanel != null) {
            drawingPanel.repaint();
        }
    }
    
    // DrawingPanel inner class
    class DrawingPanel extends JPanel {
        private LogicGateController controller;
        private GateComponent draggingComponent;
        private Point dragOffset;
        private boolean connectionMode = false;
        private GateComponent connectionSource;
        private Point tempWireEnd;
        
        public DrawingPanel(LogicGateController controller) {
            this.controller = controller;
            setBackground(new Color(245, 247, 250));
            setBorder(new RoundedBorder(new Color(100, 150, 230), 20, 3));
            setPreferredSize(new Dimension(800, 600));
            
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (connectionMode) {
                        for (int i = controller.getComponents().size() - 1; i >= 0; i--) {
                            GateComponent comp = controller.getComponents().get(i);
                            if (comp.getBounds().contains(e.getPoint())) {
                                if (connectionSource == null) {
                                    connectionSource = comp;
                                } else {
                                    if (connectionSource != comp) {
                                        controller.addWire(connectionSource, comp);
                                    }
                                    connectionSource = null;
                                }
                                repaint();
                                return;
                            }
                        }
                        connectionSource = null;
                        repaint();
                    } else {
                        for (int i = controller.getComponents().size() - 1; i >= 0; i--) {
                            GateComponent comp = controller.getComponents().get(i);
                            if (comp.getBounds().contains(e.getPoint())) {
                                draggingComponent = comp;
                                dragOffset = new Point(
                                    e.getX() - comp.getBounds().x,
                                    e.getY() - comp.getBounds().y
                                );
                                break;
                            }
                        }
                    }
                    repaint();
                }
                
                public void mouseReleased(MouseEvent e) {
                    draggingComponent = null;
                }
            });
            
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (draggingComponent != null) {
                        int newX = Math.max(0, Math.min(getWidth() - draggingComponent.getBounds().width, 
                            e.getX() - dragOffset.x));
                        int newY = Math.max(0, Math.min(getHeight() - draggingComponent.getBounds().height, 
                            e.getY() - dragOffset.y));
                        controller.moveComponent(draggingComponent, newX, newY);
                    }
                }
                
                public void mouseMoved(MouseEvent e) {
                    if (connectionMode && connectionSource != null) {
                        tempWireEnd = e.getPoint();
                        repaint();
                    }
                }
            });
        }
        
        public void setConnectionMode(boolean mode) {
            this.connectionMode = mode;
            this.connectionSource = null;
            repaint();
        }
        
        public boolean isConnectionMode() {
            return connectionMode;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw grid
            g2.setColor(new Color(220, 225, 235, 80));
            for (int i = 0; i < getWidth(); i += 25) {
                g2.drawLine(i, 0, i, getHeight());
            }
            for (int i = 0; i < getHeight(); i += 25) {
                g2.drawLine(0, i, getWidth(), i);
            }
            
            // Draw wires
            for (ConnectionWire wire : controller.getWires()) {
                drawWire(g2, wire);
            }
            
            // Draw temporary wire
            if (connectionMode && connectionSource != null && tempWireEnd != null) {
                g2.setColor(new Color(255, 100, 150, 200));
                g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                
                Point sourcePoint = getConnectionPoint(connectionSource, tempWireEnd);
                drawCurvedLine(g2, sourcePoint.x, sourcePoint.y, tempWireEnd.x, tempWireEnd.y);
            }
            
            // Draw components
            for (GateComponent comp : controller.getComponents()) {
                drawComponent(g2, comp);
            }
            
            // Highlight connection source
            if (connectionSource != null) {
                g2.setColor(new Color(255, 200, 100, 120));
                g2.setStroke(new BasicStroke(4));
                Rectangle bounds = connectionSource.getBounds();
                g2.drawRoundRect(bounds.x - 5, bounds.y - 5, 
                                bounds.width + 10, bounds.height + 10, 25, 25);
            }
        }
        
        private void drawWire(Graphics2D g2, ConnectionWire wire) {
            GateComponent source = wire.getSource();
            GateComponent target = wire.getTarget();
            
            Point sourcePoint = getConnectionPoint(source, target.getBounds().getLocation());
            Point targetPoint = getConnectionPoint(target, source.getBounds().getLocation());
            
            g2.setColor(new Color(wire.getColor().getRed(), wire.getColor().getGreen(), 
                                 wire.getColor().getBlue(), 200));
            g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            drawCurvedLine(g2, sourcePoint.x, sourcePoint.y, targetPoint.x, targetPoint.y);
            
            // Draw connection points
            g2.setColor(new Color(100, 150, 255, 150));
            g2.fillOval(sourcePoint.x - 6, sourcePoint.y - 6, 12, 12);
            g2.fillOval(targetPoint.x - 6, targetPoint.y - 6, 12, 12);
            
            g2.setColor(new Color(80, 120, 200));
            g2.fillOval(sourcePoint.x - 4, sourcePoint.y - 4, 8, 8);
            g2.fillOval(targetPoint.x - 4, targetPoint.y - 4, 8, 8);
        }
        
        private void drawComponent(Graphics2D g2, GateComponent comp) {
            Rectangle bounds = comp.getBounds();
            Color color = comp.getColor();
            
            // Shadow
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(bounds.x + 4, bounds.y + 4, bounds.width, bounds.height, 20, 20);
            
            // Main component
            GradientPaint gradient = new GradientPaint(
                bounds.x, bounds.y, color.brighter().brighter(),
                bounds.x, bounds.y + bounds.height, color.darker()
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);
            
            // Highlight state if active
            if (comp.getState() && (comp.getType() == ComponentType.BULB || comp.getType() == ComponentType.OUTPUT)) {
                g2.setColor(new Color(255, 255, 200, 100));
                g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);
            }
            
            // Border
            g2.setColor(color.darker().darker());
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);
            
            // Draw component content
            drawComponentContent(g2, comp);
            
            // Show input count for gates that support multiple inputs
            if (comp.getType() != ComponentType.HIGH_INPUT && comp.getType() != ComponentType.LOW_INPUT &&
                comp.getType() != ComponentType.BULB && comp.getType() != ComponentType.OUTPUT) {
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                String inputCount = "Inputs: " + comp.getInputs().size();
                g2.drawString(inputCount, bounds.x + 5, bounds.y + 15);
            }
        }
        
        private void drawComponentContent(Graphics2D g2, GateComponent comp) {
            Rectangle bounds = comp.getBounds();
            int cx = bounds.x + bounds.width / 2;
            int cy = bounds.y + bounds.height / 2;
            
            g2.setColor(Color.WHITE);
            
            switch (comp.getType()) {
                case HIGH_INPUT:
                    g2.setFont(new Font("Arial", Font.BOLD, 38));
                    drawCenteredString(g2, "1", cx, cy);
                    break;
                case LOW_INPUT:
                    g2.setFont(new Font("Arial", Font.BOLD, 38));
                    drawCenteredString(g2, "0", cx, cy);
                    break;
                case BULB:
                    g2.setFont(new Font("Arial", Font.PLAIN, 42));
                    drawCenteredString(g2, comp.getState() ? "💡" : "💡", cx, cy);
                    break;
                case OUTPUT:
                    g2.setFont(new Font("Arial", Font.BOLD, 38));
                    drawCenteredString(g2, comp.getState() ? "1" : "0", cx, cy);
                    break;
                case NOT:
                case AND:
                case OR:
                case NAND:
                case NOR:
                case XOR:
                    drawGateShape(g2, comp, cx, cy);
                    break;
            }
        }
        
       private void drawGateShape(Graphics2D g2, GateComponent comp, int cx, int cy) {
    g2.setColor(Color.WHITE);
    g2.setStroke(new BasicStroke(2.8f));
    
    int size = 22;
    
    switch (comp.getType()) {
        case NOT:
            // NOT gate now shows it can have multiple inputs
            int[] xPoints = {cx - size, cx + size - 8, cx - size};
            int[] yPoints = {cy - size/2, cy, cy + size/2};
            g2.fillPolygon(xPoints, yPoints, 3);
            g2.fillOval(cx + size - 8, cy - 5, 10, 10);
            
            // Draw multiple input lines - FIXED: prevent division by zero
            int inputCount = Math.max(1, Math.min(comp.getInputs().size() + 1, 3));
            for (int i = 0; i < inputCount; i++) {
                int yOffset;
                if (inputCount == 1) {
                    yOffset = 0; // Single input in the center
                } else {
                    yOffset = -size/4 + (i * size/2) / (inputCount - 1);
                }
                g2.drawLine(cx - size - 8, cy + yOffset, cx - size, cy + yOffset);
            }
            g2.drawLine(cx + size + 2, cy, cx + size + 10, cy);
            break;
            
        // ... rest of your cases remain the same
        case AND:
            g2.drawLine(cx - size, cy - size/2, cx - size, cy + size/2);
            g2.drawLine(cx - size, cy - size/2, cx - 2, cy - size/2);
            g2.drawLine(cx - size, cy + size/2, cx - 2, cy + size/2);
            g2.fillArc(cx - size/2 - 2, cy - size/2, size, size, -90, 180);
            g2.drawLine(cx - size - 8, cy - size/4, cx - size, cy - size/4);
            g2.drawLine(cx - size - 8, cy + size/4, cx - size, cy + size/4);
            g2.drawLine(cx + size/2 - 2, cy, cx + size/2 + 6, cy);
            break;
            
        case OR:
            Path2D path = new Path2D.Double();
            path.moveTo(cx - size, cy - size/2);
            path.quadTo(cx - size/2, cy - size/2, cx + 2, cy - size/3);
            path.quadTo(cx + size/2, cy, cx + size/2, cy);
            path.lineTo(cx + size/2, cy);
            path.quadTo(cx + size/2, cy, cx + 2, cy + size/3);
            path.quadTo(cx - size/2, cy + size/2, cx - size, cy + size/2);
            path.quadTo(cx - size/2, cy, cx - size, cy - size/2);
            g2.fill(path);
            g2.drawLine(cx - size - 8, cy - size/4, cx - size + 5, cy - size/4);
            g2.drawLine(cx - size - 8, cy + size/4, cx - size + 5, cy + size/4);
            g2.drawLine(cx + size/2, cy, cx + size/2 + 8, cy);
            break;
            
        case NAND:
            drawGateShapeForComponent(g2, ComponentType.AND, cx - 4, cy);
            g2.fillOval(cx + size/2 + 4, cy - 5, 10, 10);
            break;
            
        case NOR:
            drawGateShapeForComponent(g2, ComponentType.OR, cx - 4, cy);
            g2.fillOval(cx + size/2 + 4, cy - 5, 10, 10);
            break;
            
        case XOR:
            drawGateShapeForComponent(g2, ComponentType.OR, cx, cy);
            g2.drawArc(cx - size - 8, cy - size/2, 12, size, -90, 180);
            break;
    }
}
        
        // Helper method to avoid recursion issues
        private void drawGateShapeForComponent(Graphics2D g2, ComponentType type, int cx, int cy) {
            int size = 22;
            
            switch (type) {
                case AND:
                    g2.drawLine(cx - size, cy - size/2, cx - size, cy + size/2);
                    g2.drawLine(cx - size, cy - size/2, cx - 2, cy - size/2);
                    g2.drawLine(cx - size, cy + size/2, cx - 2, cy + size/2);
                    g2.fillArc(cx - size/2 - 2, cy - size/2, size, size, -90, 180);
                    g2.drawLine(cx - size - 8, cy - size/4, cx - size, cy - size/4);
                    g2.drawLine(cx - size - 8, cy + size/4, cx - size, cy + size/4);
                    g2.drawLine(cx + size/2 - 2, cy, cx + size/2 + 6, cy);
                    break;
                    
                case OR:
                    Path2D path = new Path2D.Double();
                    path.moveTo(cx - size, cy - size/2);
                    path.quadTo(cx - size/2, cy - size/2, cx + 2, cy - size/3);
                    path.quadTo(cx + size/2, cy, cx + size/2, cy);
                    path.lineTo(cx + size/2, cy);
                    path.quadTo(cx + size/2, cy, cx + 2, cy + size/3);
                    path.quadTo(cx - size/2, cy + size/2, cx - size, cy + size/2);
                    path.quadTo(cx - size/2, cy, cx - size, cy - size/2);
                    g2.fill(path);
                    g2.drawLine(cx - size - 8, cy - size/4, cx - size + 5, cy - size/4);
                    g2.drawLine(cx - size - 8, cy + size/4, cx - size + 5, cy + size/4);
                    g2.drawLine(cx + size/2, cy, cx + size/2 + 8, cy);
                    break;
            }
        }
        
        private Point getConnectionPoint(GateComponent comp, Point otherPoint) {
            Rectangle bounds = comp.getBounds();
            int centerX = bounds.x + bounds.width / 2;
            int centerY = bounds.y + bounds.height / 2;
            
            // Simplified connection point logic
            return new Point(bounds.x, centerY); // Always connect from left for simplicity
        }
        
        private void drawCurvedLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
            int ctrlX = (x1 + x2) / 2;
            int ctrlY = (y1 + y2) / 2 - Math.abs(x2 - x1) / 4;
            
            Path2D path = new Path2D.Double();
            path.moveTo(x1, y1);
            path.quadTo(ctrlX, ctrlY, x2, y2);
            g2.draw(path);
        }
        
        private void drawCenteredString(Graphics2D g2, String text, int cx, int cy) {
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            g2.drawString(text, cx - textWidth/2, cy + textHeight/2 - 2);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogicGateSimulator());
    }
}