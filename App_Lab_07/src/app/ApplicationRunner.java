package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ApplicationRunner {

	public static void main(String[] args) {
		
		int[][] digitData = new int[2810][64]; // Store pixel values
		int[] categories = new int[20]; // Store categories

		try (BufferedReader br = new BufferedReader(new FileReader("digits.csv"))) {
			String line;
			int rowCount = 0;

			// Read each line
			while ((line = br.readLine()) != null && rowCount < 20) {

				String[] values = line.split(",");

				// Print all values including category
				System.out.println("\nRow " + (rowCount + 1) + " values:");
				for (String value : values) {
					System.out.print(Integer.parseInt(value) + " ");
				}
				System.out.println();

				// Store the first 64 values in digitData
				for (int i = 0; i < 64; i++) {
					digitData[rowCount][i] = Integer.parseInt(values[i]);
				}

				// Store the category (last value)
				categories[rowCount] = Integer.parseInt(values[64]);

				System.out.println(
						"\nVisualization for row " + (rowCount + 1) + " (Category: " + categories[rowCount] + "):");
				visualizeDigit(digitData[rowCount]);

				rowCount++;
			}
			for (int i = 0; i < 10; i++) {
                double minDistance = Double.MAX_VALUE;
                int closestPattern = -1;
                
                // Compare with each of first 10
                for (int j = 10; j < 20; j++) {
                    double distance = calculateEuclideanDistance(digitData[i], digitData[j]);
                    
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestPattern = j;
                    }
                }
                
                // Print results
                System.out.printf("Pattern %d (category %d) is closest to pattern %d (category %d) with distance %.2f%n",
                                i + 1, categories[i],
                                closestPattern + 1, categories[closestPattern],
                                minDistance);
            }

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
			return;
		}
	}

	private static void visualizeDigit(int[] data) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int value = data[i * 8 + j];
				char c;
				if (value == 0) {
					c = ' ';
				} else if (value < 5) {
					c = '.';
				} else if (value < 10) {
					c = 'x';
				} else {
					c = 'X';
				}
				System.out.print(c + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static double calculateEuclideanDistance(int[] pattern1, int[] pattern2) {
        double sumOfSquares = 0.0;
        
        for (int i = 0; i < pattern1.length; i++) {
            int diff = pattern1[i] - pattern2[i];
            sumOfSquares += diff * diff;
        }
        
        return Math.sqrt(sumOfSquares);
    }

}
