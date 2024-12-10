package main;

public class Main {

	public static void main(String[] args) {
		// Initialise points
        Point[] points = {
            new Point(1, 7, 1),
            new Point(2, 8, 1),
            new Point(3, 8, 1),
            new Point(6, 1, -1),
            new Point(7, 3, -1),
            new Point(8, 2, -1)
        };

        // Initialise and train classifier
        LinearClasifier classifier = new LinearClasifier();
        classifier.train(points, 1000);

        // Output the resulting line equation
        System.out.println("The line equation separating the categories is:");
        System.out.println("y = " + classifier.getSlope() + " * x + " + classifier.getIntercept());
    }
	}

