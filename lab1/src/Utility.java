import java.util.Random;

public class Utility {

    private static final Random rng = new Random();

    public static int[][] generateRandomMatrix(int height, int width, int maxRand) {
        int[][] data = new int[height][width];
        int n, m;
        for (n = 0; height > n; n++) {
            for (m = 0; width > m; m++) {
                data[n][m] = rng.nextInt(maxRand);
            }
        }
        return data;
    }

    public static void printMatrix(float[][] matrix) {
        for (float[] floats : matrix) {
            for (int j = 0; j < floats.length; j++) {
                System.out.print(floats[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix(int[][] matrix){
        for (int[] ints : matrix) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
