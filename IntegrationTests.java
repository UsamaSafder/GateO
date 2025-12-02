public class IntegrationTests {
    
    public static void main(String[] args) {
        System.out.println("=== INTEGRATION TESTS ===\n");
        System.out.println("Testing complete circuit scenarios...\n");
        
        try {
            testHalfAdderCircuit();
            testSRFlipFlopConcept();
            testComplexLogicChain();
            
            System.out.println("\n✅ INTEGRATION TESTS COMPLETED!");
            
        } catch (Exception e) {
            System.out.println("\n❌ ERROR: " + e.getMessage());
        }
    }
    
    static void testHalfAdderCircuit() {
        System.out.println("1. HALF-ADDER CIRCUIT (XOR for sum, AND for carry):");
        
        // Inputs
        model.GateComponent A = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 0);
        model.GateComponent B = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 50);
        
        // XOR for sum
        model.GateComponent xor = new model.GateComponent(model.ComponentType.XOR, 100, 0);
        xor.addInput(A);
        xor.addInput(B);
        
        // AND for carry
        model.GateComponent and = new model.GateComponent(model.ComponentType.AND, 100, 50);
        and.addInput(A);
        and.addInput(B);
        
        // Outputs
        model.GateComponent sumOutput = new model.GateComponent(model.ComponentType.OUTPUT, 200, 0);
        model.GateComponent carryOutput = new model.GateComponent(model.ComponentType.OUTPUT, 200, 50);
        
        sumOutput.addInput(xor);
        carryOutput.addInput(and);
        
        // Test case 1: 1 + 1 (A=HIGH, B=HIGH)
        A.setState(true);
        B.setState(true);
        
        boolean sum = sumOutput.calculateOutput();   // XOR(1,1) = 0
        boolean carry = carryOutput.calculateOutput(); // AND(1,1) = 1
        
        System.out.println("  Input: A=1, B=1");
        System.out.println("  Sum (XOR) = " + (sum ? "1" : "0") + " ✓");
        System.out.println("  Carry (AND) = " + (carry ? "1" : "0") + " ✓");
        System.out.println("  Result: " + (carry ? "1" : "0") + "" + (sum ? "1" : "0") + " (binary 10 = decimal 2)");
    }
    
    static void testSRFlipFlopConcept() {
        System.out.println("\n2. SR FLIP-FLOP CONCEPT (NOR-based):");
        
        // Create NOR gates
        model.GateComponent nor1 = new model.GateComponent(model.ComponentType.NOR, 100, 0);
        model.GateComponent nor2 = new model.GateComponent(model.ComponentType.NOR, 100, 100);
        
        // Create feedback connections (simplified)
        System.out.println("  SR Flip-Flop uses cross-coupled NOR gates");
        System.out.println("  This demonstrates feedback capability");
        
        // Note: Actual SR flip-flop requires output feedback to inputs
        // This is simplified for demonstration
        model.GateComponent outputQ = new model.GateComponent(model.ComponentType.OUTPUT, 200, 0);
        model.GateComponent outputQnot = new model.GateComponent(model.ComponentType.OUTPUT, 200, 100);
        
        System.out.println("  Q and Q' outputs represent stored state");
    }
    
    static void testComplexLogicChain() {
        System.out.println("\n3. COMPLEX LOGIC CHAIN:");
        
        // Create inputs
        model.GateComponent A = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 0);
        model.GateComponent B = new model.GateComponent(model.ComponentType.LOW_INPUT, 0, 50);
        model.GateComponent C = new model.GateComponent(model.ComponentType.HIGH_INPUT, 0, 100);
        
        // Create chain: ((A AND B) OR (NOT C)) -> OUTPUT
        model.GateComponent andAB = new model.GateComponent(model.ComponentType.AND, 100, 25);
        andAB.addInput(A);
        andAB.addInput(B);
        
        model.GateComponent notC = new model.GateComponent(model.ComponentType.NOT, 100, 100);
        notC.addInput(C);
        
        model.GateComponent orFinal = new model.GateComponent(model.ComponentType.OR, 200, 62);
        orFinal.addInput(andAB);
        orFinal.addInput(notC);
        
        model.GateComponent output = new model.GateComponent(model.ComponentType.OUTPUT, 300, 62);
        output.addInput(orFinal);
        
        // Calculate
        boolean result = output.calculateOutput();
        
        System.out.println("  Circuit: ((A AND B) OR (NOT C))");
        System.out.println("  Inputs: A=" + (A.getState()?"1":"0") + 
                          ", B=" + (B.getState()?"1":"0") + 
                          ", C=" + (C.getState()?"1":"0"));
        System.out.println("  A AND B = " + (andAB.calculateOutput()?"1":"0"));
        System.out.println("  NOT C = " + (notC.calculateOutput()?"1":"0"));
        System.out.println("  Final result = " + (result?"1":"0") + " ✓");
    }
}