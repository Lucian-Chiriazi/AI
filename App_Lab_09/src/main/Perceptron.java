package main;

public class Perceptron {
	private double weight1;
	private double weight2;
	private double treshold;
	
	// Constructor to initialise the weights
	public Perceptron(double weight1, double weight2, double treshold) {
		this.weight1 = weight1;
		this.weight2 = weight2;
		this.treshold = treshold;
	}
	
	// Linear transfer function 
    private int transferFunction(double sum) {
    	return sum >= treshold ? 1 : 0; 
    }
    
    public int computeOutput(double input1, double input2) {
    	// Calculate weighted sum
    	double sum = (input1 * weight1) + (input2 * weight2);
    	
    	// Apply transfer function 
    	return transferFunction(sum);
    }
    
    public static void main(String[] args) {
//    	5.
//    	Perceptron perceptron1 = new Perceptron(0.5, 0.7, 1.0);
//    	Perceptron perceptron2 = new Perceptron(1.0, 0.5, 2.0);
//    	Perceptron perceptron3 = new Perceptron(0.3, 1.2, 1.5);
//    	
        
//    	6.
//    	Perceptron hidden1 = new Perceptron(0.5, 1.0, 1.0);
//        Perceptron hidden2 = new Perceptron(1.0, 0.5, 2.0);
//        Perceptron outputLayer = new Perceptron(1.0, 1.0, 1.5); // Combine outputs from hidden layer
//
//    	
//    	double data[][] = {
//    			{3.0, 6.0},
//    			{2.0, -5.0},
//    			{-2.0, 1.0},
//    			{0, 0}
//    	};
    	
    	double[][] data = {
                {0, 0}, 
                {0, 1}, 
                {1, 0}, 
                {1, 1}  
            };
    	int[] expectedOutputs = {0, 1, 1, 0}; // XOR outputs

        // Create two perceptrons for the hidden layer
        Perceptron hidden1 = new Perceptron(1.0, 1.0, 0.5); // Detects "OR" pattern
        Perceptron hidden2 = new Perceptron(-1.0, -1.0, -1.5); // Detects "AND" pattern

        Perceptron outputLayer = new Perceptron(1.0, 1.0, 1.5); // Combines patterns for XOR

    	for (int i = 0; i < data.length; i++) {
            double input1 = data[i][0];  
            double input2 = data[i][1];  
//			5.
//            // Compute and print the output for this data point
//            int output1 = perceptron1.computeOutput(input1, input2);
//            int output2 = perceptron2.computeOutput(input1, input2);
//            int output3 = perceptron3.computeOutput(input1, input2);
//            System.out.println("Data Point " + (i + 1) + ": (" + input1 + ", " + input2 +  ")");
//            
//            System.out.println("Perceptron 1 Output: " + output1);
//            System.out.println("Perceptron 2 Output: " + output2);
//            System.out.println("Perceptron 3 Output: " + output3);
//            System.out.println("-----------------------------------");
            
//            6.
//            int hiddenOutput1 = hidden1.computeOutput(input1, input2);
//            int hiddenOutput2 = hidden2.computeOutput(input1, input2);
//
//            int finalOutput = outputLayer.computeOutput(hiddenOutput1, hiddenOutput2);
//
//            // Print results
//            System.out.println("Data Point " + (i + 1) + ": (" + input1 + ", " + input2 + ")");
//            System.out.println("Hidden Layer Output: [" + hiddenOutput1 + ", " + hiddenOutput2 + "]");
//            System.out.println("Output Layer Output: " + finalOutput);
//            System.out.println("-----------------------------------");
            
            
         // Hidden layer outputs
            int hiddenOutput1 = hidden1.computeOutput(input1, input2); // OR
            int hiddenOutput2 = hidden2.computeOutput(input1, input2); // NOT AND

            // Output layer input: outputs of hidden layer perceptrons
            int finalOutput = outputLayer.computeOutput(hiddenOutput1, hiddenOutput2);

            // Print results
            System.out.println("Input: (" + (int) input1 + ", " + (int) input2 + ")");
            System.out.println("Expected Output: " + expectedOutputs[i]);
            System.out.println("Result Output: " + finalOutput);
            System.out.println("-----------------------------------");
        }
    }
}
