package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicGateModel implements Serializable {
    private List<GateComponent> components;
    private List<ConnectionWire> wires;
    private Map<String, Boolean> outputStates;
    
    public LogicGateModel() {
        components = new ArrayList<>();
        wires = new ArrayList<>();
        outputStates = new HashMap<>();
    }
    
    public void addComponent(GateComponent component) {
        components.add(component);
        if (component.getType() == ComponentType.OUTPUT || component.getType() == ComponentType.BULB) {
            outputStates.put(component.getId(), false);
        }
    }
    
    public void removeComponent(GateComponent component) {
        components.remove(component);
        outputStates.remove(component.getId());
        // Remove connected wires
        wires.removeIf(wire -> wire.getSource() == component || wire.getTarget() == component);
    }
    
    public void addWire(ConnectionWire wire) {
        wires.add(wire);
    }
    
    public void removeWire(ConnectionWire wire) {
        wires.remove(wire);
    }
    
    public List<GateComponent> getComponents() { return components; }
    public List<ConnectionWire> getWires() { return wires; }
    public Map<String, Boolean> getOutputStates() { return outputStates; }
    
    public void updateOutputState(String componentId, boolean state) {
        outputStates.put(componentId, state);
    }
    
    public void clear() {
        components.clear();
        wires.clear();
        outputStates.clear();
    }
}