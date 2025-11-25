package controller;

import model.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LogicGateController {
    private LogicGateModel model;
    private view.LogicGateSimulator view;
    
    public LogicGateController(view.LogicGateSimulator view) {
        this.model = new LogicGateModel();
        this.view = view;
    }
    
    public void addComponent(ComponentType type, int x, int y) {
        GateComponent component = new GateComponent(type, x, y);
        model.addComponent(component);
        view.refreshView();
    }
    
    public void removeComponent(GateComponent component) {
        model.removeComponent(component);
        view.refreshView();
    }
    
    public void addWire(GateComponent source, GateComponent target) {
        if (target.canAcceptMoreInputs()) {
            ConnectionWire wire = new ConnectionWire(source, target);
            model.addWire(wire);
            view.refreshView();
        }
    }
    
    public void removeWire(ConnectionWire wire) {
        model.removeWire(wire);
        view.refreshView();
    }
    
    public void runSimulation() {
        // Calculate states for all components
        for (GateComponent component : model.getComponents()) {
            boolean state = component.calculateOutput();
            component.setState(state);
            
            if (component.getType() == ComponentType.OUTPUT || component.getType() == ComponentType.BULB) {
                model.updateOutputState(component.getId(), state);
            }
        }
        
        view.refreshView();
        view.updateOutputPanel();
    }
    
    public void clearAll() {
        model.clear();
        view.refreshView();
        view.updateOutputPanel();
    }
    
    public List<GateComponent> getComponents() {
        return model.getComponents();
    }
    
    public List<ConnectionWire> getWires() {
        return model.getWires();
    }
    
    public Map<String, Boolean> getOutputStates() {
        return model.getOutputStates();
    }
    
    public void moveComponent(GateComponent component, int x, int y) {
        component.getBounds().setLocation(x, y);
        view.refreshView();
    }
}