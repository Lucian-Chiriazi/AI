package main;

import java.util.Arrays;

public class svm {
	// Step 1 and 2
	private static  double[][] dataPoints = {
			{1.0, 2.0}, {2.0, 3.0}, {3.0, 4.0}, {4.0, 5.0}, {5.0, 6.0}, {6.0, 7.0}
	};

	public static void main(String[] args) {
		// Step 3: Calculate distance
		double[] distanceFromOrigin = new double[dataPoints.length];
		for (int i = 0; i < dataPoints.length; i++) {
			distanceFromOrigin[i] = Math.sqrt(
					dataPoints[i][0] * dataPoints[i][0] + dataPoints[i][1] * dataPoints[i][1]
					);
		}
		System.out.println("Distance from origin: " + Arrays.toString(distanceFromOrigin));
		
		
		// Step 4: Combine original points with their distance from the origin to make 3D points
		double[][] points3D = new double[dataPoints.length][3];
		for (int i = 0; i < dataPoints.length; i++) {
			points3D[i][0] = dataPoints[i][0];
			points3D[i][1] = dataPoints[i][1];
			points3D[i][2] = distanceFromOrigin[i];
		}
		
		System.out.println();
		System.out.println("3D Points:");
		for (double[] point : points3D) {
			System.out.println(Arrays.toString(point));
		}
		
		// Step 5: Projection point
		double[] projectionPoint = {5.0, 5.0};
		double[] distanceFromProjection = new double[dataPoints.length];
		
		for (int i = 0; i < dataPoints.length; i++) {
			distanceFromProjection[i] = Math.sqrt(
					Math.pow(dataPoints[i][0] - projectionPoint[0], 2) +
					Math.pow(dataPoints[i][1] - projectionPoint[1], 2)
					);
		}
		System.out.println();
		System.out.println("Distance from projection point (5, 5): " + Arrays.toString(distanceFromProjection));
		
		
		// Step 6: Find the centroid of the original data
		double[] centroid = {0.0, 0.0};
		for (double[] point : dataPoints) {
			centroid[0] += point[0];
			centroid[1] += point[1];
		}
		
		centroid[0] /= dataPoints.length;
		centroid[1] /= dataPoints.length;
		
		System.out.println();
		System.out.println("Centroid of the original data: " + Arrays.toString(centroid));
		
		
	}

}
