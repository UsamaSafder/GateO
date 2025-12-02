// SUPER SIMPLE TEST - NO REFLECTION
public class EasyTest {
    
    public static void main(String[] args) {
        System.out.println("🎯 EASY LOGIC GATE TEST\n");
        
        try {
            // Test 1: Can we create components?
            System.out.print("1. Creating logic gates... ");
            model.ComponentType andType = model.ComponentType.AND;
            model.ComponentType orType = model.ComponentType.OR;
            model.ComponentType highType = model.ComponentType.HIGH_INPUT;
            model.ComponentType lowType = model.ComponentType.LOW_INPUT;
            System.out.println("✓ DONE");
            
            // Test 2: Create actual components
            System.out.print("2. Creating gate components... ");
            model.GateComponent high = new model.GateComponent(highType, 0, 0);
            model.GateComponent low = new model.GateComponent(lowType, 50, 0);
            model.GateComponent andGate = new model.GateComponent(andType, 100, 0);
            System.out.println("✓ DONE");
            
            // Test 3: Check IDs
            System.out.print("3. Checking component IDs... ");
            String highId = high.getId();
            String andId = andGate.getId();
            if (highId != null && andId != null && !highId.equals(andId)) {
                System.out.println("✓ UNIQUE IDs: " + highId.substring(0, 8) + "..., " + andId.substring(0, 8) + "...");
            } else {
                System.out.println("✗ FAILED");
            }
            
            // Test 4: Connect wires
            System.out.print("4. Connecting components... ");
            andGate.addInput(high);
            andGate.addInput(low);
            System.out.println("✓ DONE");
            
            // Test 5: Calculate logic
            System.out.print("5. Testing AND(HIGH, LOW) logic... ");
            boolean result = andGate.calculateOutput();
            if (!result) { // Should be false (LOW)
                System.out.println("✓ CORRECT: AND(HIGH, LOW) = LOW");
            } else {
                System.out.println("✗ WRONG: Expected LOW, got HIGH");
            }
            
            // Test 6: Test OR gate
            System.out.print("6. Testing OR(HIGH, LOW) logic... ");
            model.GateComponent orGate = new model.GateComponent(orType, 200, 0);
            orGate.addInput(high);
            orGate.addInput(low);
            boolean orResult = orGate.calculateOutput();
            if (orResult) { // Should be true (HIGH)
                System.out.println("✓ CORRECT: OR(HIGH, LOW) = HIGH");
            } else {
                System.out.println("✗ WRONG: Expected HIGH, got LOW");
            }
            
            // Test 7: Test model
            System.out.print("7. Testing model operations... ");
            model.LogicGateModel model = new model.LogicGateModel();
            model.addComponent(andGate);
            model.addComponent(orGate);
            if (model.getComponents().size() == 2) {
                System.out.println("✓ Model has 2 components");
            } else {
                System.out.println("✗ Model should have 2 components");
            }
            
            System.out.println("\n✅ TEST COMPLETED SUCCESSFULLY!");
            
        } catch (Exception e) {
            System.out.println("\n❌ TEST FAILED WITH ERROR:");
            System.out.println("Error: " + e.getClass().getName());
            System.out.println("Message: " + e.getMessage());
            System.out.println("\nStack trace:");
            e.printStackTrace();
        }
    }
}