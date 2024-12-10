package main;

public class LinearClasifier {
    private double m; // slope
    private double b; // y-intercept
    private double learningRate = 0.01;

    public LinearClasifier() {
        this.m = Math.random();
        this.b = Math.random();
    }

    // Predict category (-1 or 1) for a given point
    public int predict(Point p) {
        double lineValue = m * p.x + b;
        return lineValue >= p.y ? 1 : -1;
    }

    // Train classifier with data
    public void train(Point[] points, int epochs) {
        for (int i = 0; i < epochs; i++) {
            for (Point p : points) {
                int actualCategory = p.category;
                int predictedCategory = predict(p);

                // Update weights if prediction is incorrect
                if (predictedCategory != actualCategory) {
                    double error = actualCategory - predictedCategory;
                    m += learningRate * error * p.x;
                    b += learningRate * error;
                }
            }
        }
    }

    public double getSlope() {
        return m;
    }

    public double getIntercept() {
        return b;
    }
}
