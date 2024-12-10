package main;
import java.util.Random;

public class NeuralNetwork {
    double weightIH1, weightIH2, weightHO1, weightHO2, biasH1, biasH2, biasO;
    double learningRate = 0.05;

    public NeuralNetwork() {
        Random random = new Random();
        weightIH1 = random.nextDouble() - 0.5;
        weightIH2 = random.nextDouble() - 0.5;
        weightHO1 = random.nextDouble() - 0.5;
        weightHO2 = random.nextDouble() - 0.5;
        biasH1 = random.nextDouble() - 0.5;
        biasH2 = random.nextDouble() - 0.5;
        biasO = random.nextDouble() - 0.5;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public double[] forwardPass(double input1, double input2) {
        // Hidden layer
        double hiddenInput1 = (input1 * weightIH1) + (input2 * weightIH2) + biasH1;
        double hiddenOutput1 = sigmoid(hiddenInput1);

        double hiddenInput2 = (input1 * weightIH1) + (input2 * weightIH2) + biasH2;
        double hiddenOutput2 = sigmoid(hiddenInput2);

        // Output layer
        double outputInput = (hiddenOutput1 * weightHO1) + (hiddenOutput2 * weightHO2) + biasO;
        double finalOutput = sigmoid(outputInput);

        return new double[]{hiddenOutput1, hiddenOutput2, finalOutput};
    }

    public void train(double[][] inputs, double[] targets, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalError = 0;
            for (int i = 0; i < inputs.length; i++) {
                double[] input = inputs[i];
                double target = targets[i];

                // Forward pass
                double[] outputs = forwardPass(input[0], input[1]);
                double hiddenOutput1 = outputs[0];
                double hiddenOutput2 = outputs[1];
                double finalOutput = outputs[2];

                // Calculate output layer error
                double outputError = target - finalOutput;
                double outputGradient = outputError * sigmoidDerivative(finalOutput);

                // Adjust hidden-to-output weights
                weightHO1 += learningRate * outputGradient * hiddenOutput1;
                weightHO2 += learningRate * outputGradient * hiddenOutput2;
                biasO += learningRate * outputGradient;

                // Calculate hidden layer errors
                double hiddenError1 = outputGradient * weightHO1;
                double hiddenError2 = outputGradient * weightHO2;

                double hiddenGradient1 = hiddenError1 * sigmoidDerivative(hiddenOutput1);
                double hiddenGradient2 = hiddenError2 * sigmoidDerivative(hiddenOutput2);

                // Adjust input-to-hidden weights
                weightIH1 += learningRate * hiddenGradient1 * input[0];
                weightIH2 += learningRate * hiddenGradient1 * input[1];
                biasH1 += learningRate * hiddenGradient1;

                weightIH1 += learningRate * hiddenGradient2 * input[0];
                weightIH2 += learningRate * hiddenGradient2 * input[1];
                biasH2 += learningRate * hiddenGradient2;

                // Accumulate total error
                totalError += Math.pow(outputError, 2);
            }

            System.out.println("Epoch " + epoch + " Error: " + totalError);
        }
    }

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork();
        double[][] inputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] targets = {0, 1, 1, 0};

        nn.train(inputs, targets, 30000);

        for (double[] input : inputs) {
            double[] outputs = nn.forwardPass(input[0], input[1]);
            System.out.println("Input: " + input[0] + ", " + input[1] + " Output: " + outputs[2]);
        }
    }
}
