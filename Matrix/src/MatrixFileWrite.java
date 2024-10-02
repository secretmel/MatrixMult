import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MatrixFileWrite {

    // Method to write a matrix to a file
    public static void writeMatrixFile(String filePath, int[][] matrix) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : matrix) {
                for (int element : row) {
                    writer.write(element + " ");
                }
                writer.newLine();
            }
        }
    }

    // Method to read a matrix from a file
    public static int[][] readMatrixFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int rows = 0;
            int cols = 0;
            String line;

            // Determine the size of the matrix (rows and columns)
            while ((line = reader.readLine()) != null) {
                rows++;
                String[] elements = line.split(" ");
                if (cols == 0) {
                    cols = elements.length;
                }
            }

            // Create matrix to store values
            int[][] matrix = new int[rows][cols];

            // Read the values into the matrix
            try (BufferedReader resetReader = new BufferedReader(new FileReader(filePath))) {
                int currentRow = 0;
                while ((line = resetReader.readLine()) != null) {
                    String[] elements = line.split(" ");
                    for (int i = 0; i < elements.length; i++) {
                        matrix[currentRow][i] = Integer.parseInt(elements[i]);
                    }
                    currentRow++;
                }
            }

            return matrix;
        }
    }

    // Method to multiply two matrices
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    // Main method
    public static void main(String[] args) {
        try {
            // Pre-defined file names
            String file1 = "matrix1.txt";
            String file2 = "matrix2.txt";

            // Read matrices from the files
            int[][] matrix1 = readMatrixFile(file1);
            int[][] matrix2 = readMatrixFile(file2);

            // Multiply matrices
            int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);

            // Write the result to matrix3.txt
            writeMatrixFile("matrix3.txt", resultMatrix);
            System.out.println("The result of the multiplication has been written to matrix3.txt");

        } catch (IOException e) {
            System.err.println("An IOException occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("An IllegalArgumentException occurred: " + e.getMessage());
        }
    }
}
