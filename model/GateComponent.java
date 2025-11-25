package model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GateComponent implements Serializable {
    private String id;
    private ComponentType type;
    private Rectangle bounds;
    private Color color;
    private List<GateComponent> inputs;
    private GateComponent output;
    private boolean state;
    private boolean evaluated; // Track if component has been evaluated
    
    public GateComponent(ComponentType type, int x, int y) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.inputs = new ArrayList<>();
        this.evaluated = false;
        
        switch (type) {
            case HIGH_INPUT:
            case LOW_INPUT:
            case BULB:
            case OUTPUT:
                this.bounds = new Rectangle(x, y, 80, 80);
                break;
            default:
                this.bounds = new Rectangle(x, y, 120, 80);
        }
        
        this.state = (type == ComponentType.HIGH_INPUT);
        
        switch (type) {
            case HIGH_INPUT: color = new Color(80, 255, 150); break;
            case LOW_INPUT: color = new Color(255, 100, 130); break;
            case BULB: color = new Color(255, 200, 80); break;
            case OUTPUT: color = new Color(150, 180, 255); break;
            case NOT: color = new Color(255, 100, 150); break;
            case AND: color = new Color(100, 170, 255); break;
            case OR: color = new Color(100, 255, 200); break;
            case NAND: color = new Color(255, 180, 100); break;
            case NOR: color = new Color(200, 120, 255); break;
            case XOR: color = new Color(255, 230, 100); break;
        }
    }
    
    public String getId() { return id; }
    public ComponentType getType() { return type; }
    public Rectangle getBounds() { return bounds; }
    public void setBounds(Rectangle bounds) { this.bounds = bounds; }
    public void setPosition(int x, int y) { this.bounds.setLocation(x, y); }
    public Color getColor() { return color; }
    public List<GateComponent> getInputs() { return inputs; }
    public GateComponent getOutput() { return output; }
    public void setOutput(GateComponent output) { this.output = output; }
    public boolean getState() { return state; }
    public void setState(boolean state) { this.state = state; }
    public boolean isEvaluated() { return evaluated; }
    public void setEvaluated(boolean evaluated) { this.evaluated = evaluated; }
    
    public void addInput(GateComponent input) {
        if (canAcceptMoreInputs()) {
            inputs.add(input);
            input.setOutput(this);
        }
    }
    
    public void removeInput(GateComponent input) {
        inputs.remove(input);
        if (input.getOutput() == this) {
            input.setOutput(null);
        }
    }
    
    public boolean canAcceptMoreInputs() {
        switch (type) {
            case HIGH_INPUT:
            case LOW_INPUT:
                return false; // Input components cannot accept inputs
            case NOT:
                return inputs.size() < 1; // FIXED: NOT gate only accepts 1 input
            case BULB:
            case OUTPUT:
                return inputs.size() < 1; // Output components accept 1 input
            case AND:
            case OR:
            case NAND:
            case NOR:
            case XOR:
                return inputs.size() < 4; // Logic gates can accept up to 4 inputs
            default:
                return inputs.size() < 2;
        }
    }
    
    public boolean calculateOutput() {
        switch (type) {
            case HIGH_INPUT:
                return true;
                
            case LOW_INPUT:
                return false;
                
            case NOT:
                // FIXED: NOT gate with exactly 1 input
                if (inputs.isEmpty()) return false;
                return !inputs.get(0).calculateOutput();
                
            case AND:
                if (inputs.isEmpty()) return false;
                boolean andResult = true;
                for (GateComponent input : inputs) {
                    andResult = andResult && input.calculateOutput();
                }
                return andResult;
                
            case OR:
                if (inputs.isEmpty()) return false;
                boolean orResult = false;
                for (GateComponent input : inputs) {
                    orResult = orResult || input.calculateOutput();
                }
                return orResult;
                
            case NAND:
                return !calculateANDResult();
                
            case NOR:
                return !calculateORResult();
                
            case XOR:
                if (inputs.isEmpty()) return false;
                boolean xorResult = inputs.get(0).calculateOutput();
                for (int i = 1; i < inputs.size(); i++) {
                    xorResult = xorResult ^ inputs.get(i).calculateOutput();
                }
                return xorResult;
                
            case BULB:
            case OUTPUT:
                if (inputs.isEmpty()) return false;
                return inputs.get(0).calculateOutput();
                
            default:
                return false;
        }
    }
    
    private boolean calculateANDResult() {
        if (inputs.isEmpty()) return false;
        boolean result = true;
        for (GateComponent input : inputs) {
            result = result && input.calculateOutput();
        }
        return result;
    }
    
    private boolean calculateORResult() {
        if (inputs.isEmpty()) return false;
        boolean result = false;
        for (GateComponent input : inputs) {
            result = result || input.calculateOutput();
        }
        return result;
    }
}