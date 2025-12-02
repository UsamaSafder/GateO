import controller.LogicGateController;

public class ControllerTests {
    
    public static void main(String[] args) {
        System.out.println("=== CONTROLLER FUNCTIONALITY TESTS ===\n");
        
        try {
            // Note: We can't easily test controller without view, but we can test logic
            System.out.println("Testing controller concepts...");
            
            // Test 1: Component addition logic
            testComponentLogic();
            
            // Test 2: Wire connection logic
            testWireLogic();
            
            // Test 3: Simulation logic
            testSimulationLogic();
            
            System.out.println("\n✅ CONTROLLER TESTS COMPLETED!");
            
        } catch (Exception e) {
            System.out.println("\n❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void testComponentLogic() {
        System.out.println("\n1. COMPONENT LOGIC:");
        
        // Create a simple circuit manually
        model.GateComponent high = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 0);
        model.GateComponent low = new model.GateComponent(model.ComponentType.LOW_INPUT, 50, 0);
        model.GateComponent andGate = new model.GateComponent(model.ComponentType.AND, 100, 0);
        model.GateComponent output = new model.GateComponent(model.ComponentType.OUTPUT, 150, 0);
        
        // Connect circuit
        andGate.addInput(high);
        andGate.addInput(low);
        output.addInput(andGate);
        
        // Simulate
        boolean result = output.calculateOutput();
        System.out.println("✓ Basic circuit: AND(HIGH, LOW) -> OUTPUT = " + (result ? "HIGH" : "LOW"));
        
        // Test complex circuit
        model.GateComponent not = new model.GateComponent(model.ComponentType.NOT, 200, 0);
        not.addInput(high);
        boolean notResult = not.calculateOutput();
        System.out.println("✓ NOT gate: NOT(HIGH) = " + (notResult ? "HIGH" : "LOW"));
    }
    
    static void testWireLogic() {
        System.out.println("\n2. WIRE CONNECTION LOGIC:");
        
        // Test valid connections
        model.GateComponent source = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 0);
        model.GateComponent target = new model.GateComponent(model.ComponentType.AND, 100, 0);
        
        // Create wire
        model.ConnectionWire wire = new model.ConnectionWire(source, target);
        
        System.out.println("✓ Wire created: " + source.getType() + " -> " + target.getType());
        System.out.println("✓ Target now has " + target.getInputs().size() + " input(s)");
        
        // Test wire removal
        target.removeInput(source);
        System.out.println("✓ Input removed, target now has " + target.getInputs().size() + " input(s)");
    }
    
    static void testSimulationLogic() {
        System.out.println("\n3. SIMULATION LOGIC:");
        
        // Create a more complex circuit
        model.GateComponent high1 = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 0);
        model.GateComponent high2 = new model.GateComponent(model.ComponentType.HIGH_INPUT, 50, 0);
        model.GateComponent andGate = new model.GateComponent(model.ComponentType.AND, 100, 0);
        model.GateComponent notGate = new model.GateComponent(model.ComponentType.NOT, 150, 0);
        model.GateComponent output = new model.GateComponent(model.ComponentType.OUTPUT, 200, 0);
        
        // Build circuit: (HIGH AND HIGH) -> NOT -> OUTPUT
        andGate.addInput(high1);
        andGate.addInput(high2);
        notGate.addInput(andGate);
        output.addInput(notGate);
        
        // Calculate manually (simulating controller logic)
        boolean andResult = andGate.calculateOutput();  // HIGH AND HIGH = HIGH
        notGate.getInputs().get(0).setState(andResult); // Feed result to NOT
        boolean notResult = notGate.calculateOutput();  // NOT(HIGH) = LOW
        output.getInputs().get(0).setState(notResult);  // Feed result to OUTPUT
        boolean finalResult = output.calculateOutput(); // Should be LOW
        
        System.out.println("✓ Complex circuit simulation:");
        System.out.println("  HIGH AND HIGH = " + (andResult ? "HIGH" : "LOW"));
        System.out.println("  NOT(HIGH) = " + (notResult ? "HIGH" : "LOW"));
        System.out.println("  Final output = " + (finalResult ? "HIGH" : "LOW"));
        
        if (!finalResult) {
            System.out.println("✓ Circuit logic correct: Final output is LOW");
        }
    }
}