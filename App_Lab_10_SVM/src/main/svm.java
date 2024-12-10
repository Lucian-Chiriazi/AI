package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class svm {
	
	private static final int PATTERNS  = 2810;
	private static final int FEATURES = 64;
	
	public static void main(String[] args) {
		
		int[][] digitDataSet1 = new int[PATTERNS][FEATURES];
		
		
		
	}
	
	private static void readDataSet (String fileName, int[][] digitData, int[] categories) {
		try (Scanner scanner = new Scanner(new File(fileName))) {
            int rowCount = 0;

            while (scanner.hasNextLine() && rowCount < digitData.length) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                // Store the first 64 values in digitData
                for (int FEATURE_INDEX = 0; FEATURE_INDEX < 64; FEATURE_INDEX++) {
                    digitData[rowCount][FEATURE_INDEX] = Integer.parseInt(values[FEATURE_INDEX]);
                }

                // Store the category (last value)
                categories[rowCount] = Integer.parseInt(values[64]);

                rowCount++;
            }
        } catch (IOException error) {
            System.out.println("Error reading file: " + fileName + " - " + error.getMessage());
        }
    
	}
	
	private static double[] computeCentroid7(double[][] dataset) {
        double[] centroid = new double[FEATURES - 1]; // Exclude the category column
        int count = 0;

        for (int i = 0; i < dataset.length; i++) {
            // Check if the last column (category) is 7
            if (dataset[i][FEATURES - 1] == 7) {
                // Add feature values to the centroid sum
                for (int j = 0; j < FEATURES - 1; j++) {
                    centroid[j] += dataset[i][j];
                }
                count++;
            }
        }
        
        return centroid;
        }
        
}
