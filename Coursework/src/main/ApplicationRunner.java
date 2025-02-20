package main;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class ApplicationRunner {
	private static final int PATTERNS  = 2810;
	private static final int FEATURES = 64;
	private static String dataSetOne = "dataSet2.csv";
	private static String dataSetTwo = "dataSet1.csv";

	public static void main(String[] args) {
		int[][] digitDataSet1 = new int[PATTERNS][FEATURES];
        int[][] digitDataSet2 = new int[PATTERNS][FEATURES];
        int[] categories1 = new int[PATTERNS];
        int[] categories2 = new int[PATTERNS];
        
     // Read data
        readDataSet(dataSetTwo, digitDataSet1, categories1);
        readDataSet(dataSetOne, digitDataSet2, categories2);
        
        int correctMatches = 0;
        
     // Perform Euclidean distance comparison
        for (int PATTERN_INDEX = 0; PATTERN_INDEX < PATTERNS; PATTERN_INDEX++) {
            double minDistance = Double.MAX_VALUE;
            int closestPattern = -1;

            // Compare with each pattern in the second dataset
            for (int COMPARISON_INDEX = 0; COMPARISON_INDEX < PATTERNS; COMPARISON_INDEX++) {
                double distance = calculateEuclideanDistance(digitDataSet1[PATTERN_INDEX], digitDataSet2[COMPARISON_INDEX]);
                
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPattern = COMPARISON_INDEX;
                }
            }
            
            if (categories1[PATTERN_INDEX] == categories2[closestPattern]) {
            	correctMatches++;
            }

        }
        
        double accuracy = (correctMatches / (double) PATTERNS) * 100;
        System.out.println("Overall accruacy: " + accuracy);

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
        } catch (IOException fileNotFoundException) {
            System.out.println("Error reading file: " + fileName + " - " + fileNotFoundException.getMessage());
        }
    
	}
	
	private static double calculateEuclideanDistance(int[] pattern1, int[] pattern2) {
        double sumOfSquares = 0.0;

        for (int FEATURE_INDEX = 0; FEATURE_INDEX < pattern1.length; FEATURE_INDEX++) {
            int diff = pattern1[FEATURE_INDEX] - pattern2[FEATURE_INDEX];
            sumOfSquares += diff * diff;
        }

        return Math.sqrt(sumOfSquares);
    }
}
