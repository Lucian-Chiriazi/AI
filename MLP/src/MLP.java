import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MLP {
	
	private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;
    private double[][] weightsInputToHidden; // Weights from input to hidden layer
    private double[][] weightsHiddenToOutput; // Weights from hidden to output layer
    private double learningRate = 0.01;
    
    public MLP(int inputNodes, int hiddenNodes, int outputNodes) {
    	this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        // Initialize weights with random small values
        weightsInputToHidden = new double[inputNodes][hiddenNodes];
        weightsHiddenToOutput = new double[hiddenNodes][outputNodes];
        initializeWeights(weightsInputToHidden);
        initializeWeights(weightsHiddenToOutput);
    }
    
    private void initializeWeights(double[][] weights) {
    	Random random = new Random(4);
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = random.nextDouble() - 0.5; // Random values between -0.5 and 0.5
            }
        }
    }
    
    private double[] feedForward(double[] inputs) {
        double[] hiddenInputs = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < inputNodes; j++) {
                hiddenInputs[i] += inputs[j] * weightsInputToHidden[j][i];
            }
            hiddenInputs[i] = sigmoid(hiddenInputs[i]);
        }

        double[] outputs = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                outputs[i] += hiddenInputs[j] * weightsHiddenToOutput[j][i];
            }
            outputs[i] = sigmoid(outputs[i]);
        }

        return outputs;
    }

    private void train(double[] inputs, double[] targets) {
        double[] hiddenInputs = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < inputNodes; j++) {
                hiddenInputs[i] += inputs[j] * weightsInputToHidden[j][i];
            }
            hiddenInputs[i] = sigmoid(hiddenInputs[i]);
        }

        double[] outputs = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                outputs[i] += hiddenInputs[j] * weightsHiddenToOutput[j][i];
            }
            outputs[i] = sigmoid(outputs[i]);
        }

        double[] outputErrors = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            outputErrors[i] = targets[i] - outputs[i];
        }

        double[] hiddenErrors = new double[hiddenNodes];
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                hiddenErrors[i] += outputErrors[j] * weightsHiddenToOutput[i][j];
            }
        }

        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                weightsHiddenToOutput[i][j] += learningRate * outputErrors[j] * sigmoidDerivative(outputs[j]) * hiddenInputs[i];
            }
        }

        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                weightsInputToHidden[i][j] += learningRate * hiddenErrors[j] * sigmoidDerivative(hiddenInputs[j]) * inputs[i];
            }
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }
    
    public static List<double[]> loadData(String filePath) {
        List<double[]> dataset = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                double[] data = new double[line.length];
                for (int i = 0; i < line.length; i++) {
                    data[i] = Double.parseDouble(line[i]);
                }
                dataset.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataset;
    }
    
	public static void main(String[] args) {
		String dataset1Path = "dataset1.csv";
        String dataset2Path = "dataset2.csv";

        List<double[]> dataset1 = loadData(dataset1Path);
        List<double[]> dataset2 = loadData(dataset2Path);

        int inputNodes = 64;
        int hiddenNodes = 15;
        int outputNodes = 10;

//        // Train on dataset1 and test on dataset2
//        MLP mlp = new MLP(inputNodes, hiddenNodes, outputNodes);
//        for (double[] data : dataset1) {
//            double[] inputs = new double[inputNodes];
//            double[] targets = new double[outputNodes];
//            System.arraycopy(data, 0, inputs, 0, inputNodes);
//            int labelIndex = (int) data[inputNodes];
//targets[labelIndex] = 1.0;
//            mlp.train(inputs, targets);
//        }
//
//        System.out.println("Testing on dataset2...");
//        for (double[] data : dataset2) {
//            double[] inputs = new double[inputNodes];
//            double[] targets = new double[outputNodes];
//            System.arraycopy(data, 0, inputs, 0, inputNodes);
//            int labelIndex = (int) data[inputNodes];
//targets[labelIndex] = 1.0;
//            double[] outputs = mlp.feedForward(inputs);
//            System.out.println("Expected: " + (int) data[inputNodes] + ", Predicted: " + argMax(outputs));
//        }
//
//        // Train on dataset2 and test on dataset1
//        mlp = new MLP(inputNodes, hiddenNodes, outputNodes);
//        for (double[] data : dataset2) {
//            double[] inputs = new double[inputNodes];
//            double[] targets = new double[outputNodes];
//            System.arraycopy(data, 0, inputs, 0, inputNodes);
//            int labelIndex = (int) data[inputNodes];
//targets[labelIndex] = 1.0;
//            mlp.train(inputs, targets);
//        }
//
//        System.out.println("Testing on dataset1...");
//        for (double[] data : dataset1) {
//            double[] inputs = new double[inputNodes];
//            double[] targets = new double[outputNodes];
//            System.arraycopy(data, 0, inputs, 0, inputNodes);
//            int labelIndex = (int) data[inputNodes];
//targets[labelIndex] = 1.0;
//            double[] outputs = mlp.feedForward(inputs);
//            System.out.println("Expected: " + (int) data[inputNodes] + ", Predicted: " + argMax(outputs));
//        }
      
        
        
        // Fold 1: Train on dataset1, test on dataset2
        double accuracy1 = trainAndTest(dataset1, dataset2, inputNodes, hiddenNodes, outputNodes);

        // Fold 2: Train on dataset2, test on dataset1
        double accuracy2 = trainAndTest(dataset2, dataset1, inputNodes, hiddenNodes, outputNodes);

        // Print results
        System.out.printf("Test 1 Accuracy: %.2f%%%n", accuracy1 * 100);
        System.out.printf("Test 2 Accuracy: %.2f%%%n", accuracy2 * 100);
        System.out.printf("Average Accuracy: %.2f%%%n", (accuracy1 + accuracy2) / 2 * 100);
    }

    private static double trainAndTest(List<double[]> trainSet, List<double[]> testSet, int inputNodes, int hiddenNodes, int outputNodes) {
        MLP mlp = new MLP(inputNodes, hiddenNodes, outputNodes);

        // Train on the training set
        for (double[] data : trainSet) {
            double[] inputs = new double[inputNodes];
            double[] targets = new double[outputNodes];
            System.arraycopy(data, 0, inputs, 0, inputNodes);
            targets[(int) data[inputNodes]] = 1.0;
            mlp.train(inputs, targets);
        }

        // Test on the test set
        int correct = 0;
        for (double[] data : testSet) {
            double[] inputs = new double[inputNodes];
            int actual = (int) data[inputNodes];
            System.arraycopy(data, 0, inputs, 0, inputNodes);

            double[] outputs = mlp.feedForward(inputs);
            int predicted = maxValue(outputs);

            if (predicted == actual) {
                correct++;
            }
        }

        return (double) correct / testSet.size();
    }

    private static int maxValue(double[] outputs) {
        int maxIndex = 0;
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > outputs[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
		
}


