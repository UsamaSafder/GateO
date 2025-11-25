package model;

import java.awt.*;
import java.io.Serializable;

public class ConnectionWire implements Serializable {
    private GateComponent source;
    private GateComponent target;
    private Color color;
    
    public ConnectionWire(GateComponent source, GateComponent target) {
        this.source = source;
        this.target = target;
        this.color = new Color(70, 100, 180);
        
        target.addInput(source);
    }
    
    public GateComponent getSource() { return source; }
    public GateComponent getTarget() { return target; }
    public Color getColor() { return color; }
}