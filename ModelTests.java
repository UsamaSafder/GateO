// First, try importing the classes
import model.ComponentType;
import model.GateComponent;
import model.LogicGateModel;
import model.ConnectionWire;

public class ModelTests {
    
    public static void assertEquals(Object expected, Object actual, String testName) {
        if (!expected.equals(actual)) {
            throw new AssertionError(testName + " FAILED: Expected " + expected + " but got " + actual);
        }
        System.out.println("✓ " + testName);
    }
    
    public static void assertTrue(boolean condition, String testName) {
        if (!condition) {
            throw new AssertionError(testName + " FAILED");
        }
        System.out.println("✓ " + testName);
    }
    
    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE MODEL TESTS ===\n");
        
        try {
            testAllComponentTypes();
            testAllLogicGateOperations();
            testWireConnections();
            testModelManagement();
            testInputLimits();
            testSerialization();
            
            System.out.println("\n✅ ALL MODEL TESTS PASSED!");
            
        } catch (AssertionError e) {
            System.out.println("\n❌ TEST FAILED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ UNEXPECTED ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void testAllComponentTypes() {
        System.out.println("1. TESTING ALL COMPONENT TYPES:");
        
        // Test creation of every component type
        GateComponent high = new GateComponent(ComponentType.HIGH_INPUT, 0, 0);
        GateComponent low = new GateComponent(ComponentType.LOW_INPUT, 50, 0);
        GateComponent bulb = new GateComponent(ComponentType.BULB, 100, 0);
        GateComponent output = new GateComponent(ComponentType.OUTPUT, 150, 0);
        GateComponent not = new GateComponent(ComponentType.NOT, 200, 0);
        GateComponent and = new GateComponent(ComponentType.AND, 250, 0);
        GateComponent or = new GateComponent(ComponentType.OR, 300, 0);
        GateComponent nand = new GateComponent(ComponentType.NAND, 350, 0);
        GateComponent nor = new GateComponent(ComponentType.NOR, 400, 0);
        GateComponent xor = new GateComponent(ComponentType.XOR, 450, 0);
        
        assertTrue(high.getType() == ComponentType.HIGH_INPUT, "HIGH_INPUT type correct");
        assertTrue(high.getState() == true, "HIGH_INPUT state is true");
        
        assertTrue(low.getType() == ComponentType.LOW_INPUT, "LOW_INPUT type correct");
        assertTrue(low.getState() == false, "LOW_INPUT state is false");
        
        assertTrue(bulb.getType() == ComponentType.BULB, "BULB type correct");
        assertTrue(output.getType() == ComponentType.OUTPUT, "OUTPUT type correct");
        assertTrue(not.getType() == ComponentType.NOT, "NOT type correct");
        assertTrue(and.getType() == ComponentType.AND, "AND type correct");
        assertTrue(or.getType() == ComponentType.OR, "OR type correct");
        assertTrue(nand.getType() == ComponentType.NAND, "NAND type correct");
        assertTrue(nor.getType() == ComponentType.NOR, "NOR type correct");
        assertTrue(xor.getType() == ComponentType.XOR, "XOR type correct");
    }
    
    static void testAllLogicGateOperations() {
        System.out.println("\n2. TESTING ALL LOGIC GATE OPERATIONS:");
        
        GateComponent high = new GateComponent(ComponentType.HIGH_INPUT, 0, 0);
        GateComponent low = new GateComponent(ComponentType.LOW_INPUT, 50, 0);
        
        // Test NOT gate
        GateComponent not = new GateComponent(ComponentType.NOT, 100, 0);
        not.addInput(high);
        assertTrue(!not.calculateOutput(), "NOT(HIGH) = LOW");
        
        not.removeInput(high);
        not.addInput(low);
        assertTrue(not.calculateOutput(), "NOT(LOW) = HIGH");
        
        // Test AND gate
        GateComponent and = new GateComponent(ComponentType.AND, 150, 0);
        and.addInput(high);
        and.addInput(low);
        assertTrue(!and.calculateOutput(), "AND(HIGH, LOW) = LOW");
        
        // Test OR gate
        GateComponent or = new GateComponent(ComponentType.OR, 200, 0);
        or.addInput(high);
        or.addInput(low);
        assertTrue(or.calculateOutput(), "OR(HIGH, LOW) = HIGH");
        
        // Test NAND gate
        GateComponent nand = new GateComponent(ComponentType.NAND, 250, 0);
        nand.addInput(high);
        nand.addInput(high);
        assertTrue(!nand.calculateOutput(), "NAND(HIGH, HIGH) = LOW");
        
        // Test NOR gate
        GateComponent nor = new GateComponent(ComponentType.NOR, 300, 0);
        nor.addInput(low);
        nor.addInput(low);
        assertTrue(nor.calculateOutput(), "NOR(LOW, LOW) = HIGH");
        
        // Test XOR gate
        GateComponent xor = new GateComponent(ComponentType.XOR, 350, 0);
        xor.addInput(high);
        xor.addInput(low);
        assertTrue(xor.calculateOutput(), "XOR(HIGH, LOW) = HIGH");
    }
    
    static void testWireConnections() {
        System.out.println("\n3. TESTING WIRE CONNECTIONS:");
        
        GateComponent high = new GateComponent(ComponentType.HIGH_INPUT, 0, 0);
        GateComponent and = new GateComponent(ComponentType.AND, 100, 0);
        GateComponent output = new GateComponent(ComponentType.OUTPUT, 200, 0);
        
        // Test adding inputs
        assertTrue(and.canAcceptMoreInputs(), "AND gate can accept inputs initially");
        and.addInput(high);
        assertTrue(and.canAcceptMoreInputs(), "AND gate can accept more inputs after first");
        and.addInput(high);
        assertTrue(!and.canAcceptMoreInputs(), "AND gate cannot accept more than 2 inputs");
        
        // Test output connection
        output.addInput(and);
        assertTrue(!output.canAcceptMoreInputs(), "Output cannot accept more than 1 input");
        
        // Test wire creation
        ConnectionWire wire = new ConnectionWire(high, and);
        assertEquals(high, wire.getSource(), "Wire source is high input");
        assertEquals(and, wire.getTarget(), "Wire target is AND gate");
    }
    
    static void testModelManagement() {
        System.out.println("\n4. TESTING MODEL MANAGEMENT:");
        
        LogicGateModel model = new LogicGateModel();
        
        // Test adding components
        GateComponent high = new GateComponent(ComponentType.HIGH_INPUT, 0, 0);
        GateComponent and = new GateComponent(ComponentType.AND, 100, 0);
        GateComponent output = new GateComponent(ComponentType.OUTPUT, 200, 0);
        
        model.addComponent(high);
        model.addComponent(and);
        model.addComponent(output);
        
        assertEquals(3, model.getComponents().size(), "Model has 3 components");
        assertEquals(1, model.getOutputStates().size(), "Model tracks 1 output");
        
        // Test wire management
        ConnectionWire wire = new ConnectionWire(high, and);
        model.addWire(wire);
        assertEquals(1, model.getWires().size(), "Model has 1 wire");
        
        // Test component removal (should also remove wires)
        model.removeComponent(high);
        assertEquals(2, model.getComponents().size(), "Model has 2 components after removal");
        assertEquals(0, model.getWires().size(), "Wires auto-removed when source removed");
        
        // Test clear
        model.clear();
        assertEquals(0, model.getComponents().size(), "Model cleared - 0 components");
        assertEquals(0, model.getWires().size(), "Model cleared - 0 wires");
        assertEquals(0, model.getOutputStates().size(), "Model cleared - 0 output states");
    }
    
    static void testInputLimits() {
        System.out.println("\n5. TESTING INPUT LIMITS:");
        
        // Test NOT gate (max 1 input)
        GateComponent not = new GateComponent(ComponentType.NOT, 0, 0);
        GateComponent high1 = new GateComponent(ComponentType.HIGH_INPUT, 50, 0);
        GateComponent high2 = new GateComponent(ComponentType.HIGH_INPUT, 100, 0);
        
        not.addInput(high1);
        assertTrue(!not.canAcceptMoreInputs(), "NOT gate cannot accept more than 1 input");
        
        // Test AND/OR gates (max 2 inputs)
        GateComponent and = new GateComponent(ComponentType.AND, 150, 0);
        and.addInput(high1);
        and.addInput(high2);
        assertTrue(!and.canAcceptMoreInputs(), "AND gate cannot accept more than 2 inputs");
        
        // Test OUTPUT/BULB (max 1 input)
        GateComponent output = new GateComponent(ComponentType.OUTPUT, 200, 0);
        output.addInput(high1);
        assertTrue(!output.canAcceptMoreInputs(), "Output cannot accept more than 1 input");
    }
    
    static void testSerialization() {
        System.out.println("\n6. TESTING SERIALIZATION FEATURES:");
        
        // Test that components have proper fields for serialization
        GateComponent comp = new GateComponent(ComponentType.AND, 100, 200);
        
        assertTrue(comp.getId() != null, "Component has ID for serialization");
        assertTrue(comp.getBounds() != null, "Component has bounds");
        assertTrue(comp.getColor() != null, "Component has color");
        assertTrue(comp.getInputs() != null, "Component has inputs list");
        
        // Test that model implements Serializable
        LogicGateModel model = new LogicGateModel();
        assertTrue(model.getComponents() != null, "Model has components list");
        assertTrue(model.getWires() != null, "Model has wires list");
        assertTrue(model.getOutputStates() != null, "Model has output states map");
    }
}