import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    // Initialize weights with random values between -0.5 and 0.5
    private void initializeWeights(double[][] weights) {
        Random random = new Random();
        for (int inputIndex = 0; inputIndex < weights.length; inputIndex++) {
            for (int hiddenIndex = 0; hiddenIndex < weights[inputIndex].length; hiddenIndex++) {
                weights[inputIndex][hiddenIndex] = random.nextDouble() - 0.5;
            }
        }
    }

    // Feedforward process to compute the outputs
    private double[] feedForward(double[] inputs) {
        double[] hiddenLayerInputs = new double[hiddenNodes];

        // Compute activations for the hidden layer
        for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
            for (int inputIndex = 0; inputIndex < inputNodes; inputIndex++) {
                hiddenLayerInputs[hiddenIndex] += inputs[inputIndex] * weightsInputToHidden[inputIndex][hiddenIndex];
            }
            hiddenLayerInputs[hiddenIndex] = sigmoid(hiddenLayerInputs[hiddenIndex]);
        }

        double[] outputLayerOutputs = new double[outputNodes];

        // Compute activations for the output layer
        for (int outputIndex = 0; outputIndex < outputNodes; outputIndex++) {
            for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
                outputLayerOutputs[outputIndex] += hiddenLayerInputs[hiddenIndex] * weightsHiddenToOutput[hiddenIndex][outputIndex];
            }
            outputLayerOutputs[outputIndex] = sigmoid(outputLayerOutputs[outputIndex]);
        }

        return outputLayerOutputs;
    }

    // Train the network using backpropagation
    private void train(double[] inputs, double[] targets) {
        double[] hiddenLayerInputs = new double[hiddenNodes];

        // Compute activations for the hidden layer
        for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
            for (int inputIndex = 0; inputIndex < inputNodes; inputIndex++) {
                hiddenLayerInputs[hiddenIndex] += inputs[inputIndex] * weightsInputToHidden[inputIndex][hiddenIndex];
            }
            hiddenLayerInputs[hiddenIndex] = sigmoid(hiddenLayerInputs[hiddenIndex]);
        }

        double[] outputLayerOutputs = new double[outputNodes];

        // Compute activations for the output layer
        for (int outputIndex = 0; outputIndex < outputNodes; outputIndex++) {
            for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
                outputLayerOutputs[outputIndex] += hiddenLayerInputs[hiddenIndex] * weightsHiddenToOutput[hiddenIndex][outputIndex];
            }
            outputLayerOutputs[outputIndex] = sigmoid(outputLayerOutputs[outputIndex]);
        }

        // Calculate output errors
        double[] outputErrors = new double[outputNodes];
        for (int outputIndex = 0; outputIndex < outputNodes; outputIndex++) {
            outputErrors[outputIndex] = targets[outputIndex] - outputLayerOutputs[outputIndex];
        }

        // Calculate hidden layer errors
        double[] hiddenErrors = new double[hiddenNodes];
        for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
            for (int outputIndex = 0; outputIndex < outputNodes; outputIndex++) {
                hiddenErrors[hiddenIndex] += outputErrors[outputIndex] * weightsHiddenToOutput[hiddenIndex][outputIndex];
            }
        }

        // Update weights from hidden to output layer
        for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
            for (int outputIndex = 0; outputIndex < outputNodes; outputIndex++) {
                weightsHiddenToOutput[hiddenIndex][outputIndex] += learningRate * outputErrors[outputIndex] * sigmoidDerivative(outputLayerOutputs[outputIndex]) * hiddenLayerInputs[hiddenIndex];
            }
        }

        // Update weights from input to hidden layer
        for (int inputIndex = 0; inputIndex < inputNodes; inputIndex++) {
            for (int hiddenIndex = 0; hiddenIndex < hiddenNodes; hiddenIndex++) {
                weightsInputToHidden[inputIndex][hiddenIndex] += learningRate * hiddenErrors[hiddenIndex] * sigmoidDerivative(hiddenLayerInputs[hiddenIndex]) * inputs[inputIndex];
            }
        }
    }

    // Sigmoid activation function
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    // Derivative of sigmoid function
    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    // Load data from a file
    public static ArrayList<double[]> loadData(String filePath) {
        ArrayList<double[]> dataset = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                double[] data = new double[line.length];
                for (int dataIndex = 0; dataIndex < line.length; dataIndex++) {
                    data[dataIndex] = Double.parseDouble(line[dataIndex]);
                }
                dataset.add(data);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return dataset;
    }

    // Main method for training and testing
    public static void main(String[] args) {
        String dataset1Path = "dataset1.csv";
        String dataset2Path = "dataset2.csv";

        ArrayList<double[]> dataset1 = loadData(dataset1Path);
        ArrayList<double[]> dataset2 = loadData(dataset2Path);

        int inputNodes = 64;
        int hiddenNodes = 500;
        int outputNodes = 10;

        // Fold 1: Train on dataset1, test on dataset2
        double accuracy1 = trainAndTest(dataset1, dataset2, inputNodes, hiddenNodes, outputNodes);

        // Fold 2: Train on dataset2, test on dataset1
        double accuracy2 = trainAndTest(dataset2, dataset1, inputNodes, hiddenNodes, outputNodes);

        // Print results
        System.out.printf("Test 1 Accuracy: %.2f%%\n", accuracy1 * 100);
        System.out.printf("Test 2 Accuracy: %.2f%%\n", accuracy2 * 100);
        System.out.printf("Average Accuracy: %.2f%%\n", (accuracy1 + accuracy2) / 2 * 100);
    }

    // Train and test the network on given datasets
    private static double trainAndTest(ArrayList<double[]> trainSet, ArrayList<double[]> testSet, int inputNodes, int hiddenNodes, int outputNodes) {
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
        int correctPredictions = 0;
        for (double[] data : testSet) {
            double[] inputs = new double[inputNodes];
            int actualLabel = (int) data[inputNodes];
            System.arraycopy(data, 0, inputs, 0, inputNodes);

            double[] outputs = mlp.feedForward(inputs);
            int predictedLabel = maxValue(outputs);

            if (predictedLabel == actualLabel) {
                correctPredictions++;
            }
        }

        return (double) correctPredictions / testSet.size();
    }

    // Find the index of the maximum value in the array
    private static int maxValue(double[] outputs) {
        int maxIndex = 0;
        for (int outputIndex = 1; outputIndex < outputs.length; outputIndex++) {
            if (outputs[outputIndex] > outputs[maxIndex]) {
                maxIndex = outputIndex;
            }
        }
        return maxIndex;
    }
}
