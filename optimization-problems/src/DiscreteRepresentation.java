import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DiscreteRepresentation extends LinearRepresentation {

    public List<Integer> encoding;

    DiscreteRepresentation() {
        super();
        this.encoding = new ArrayList<>();
    }

    @Override
    public final List<Number> getEncoding() {
        return new ArrayList<>(this.encoding);
    }

    @Override
    public void printEncoding() {
        super.printEncoding();
    }

    public int objectiveFunction(int[][] matrix) {
        /*
        The Hungarian algorithm.
         */
        int height = matrix.length + 1;
        int width = matrix[0].length + 1;
        int[] potentialsU = new int[height];
        int[] potentialsV = new int[width];
        int[] pairing = new int[width];
        int[] way = new int[width];
        int[] minPotentialsV = new int[width];
        boolean[] used = new boolean[width];
        int i, j, prev_i, prev_j, minPotentialVColumn, minPotentialV, current;

        for (i = 1; height > i; i++) {
            pairing[0] = i;
            prev_j = 0;
            Arrays.fill(minPotentialsV, Integer.MAX_VALUE);
            Arrays.fill(used, false);
            do {
                /*
                    Looking for the free column.
                 */
                used[prev_j] = true;
                prev_i = pairing[prev_j];
                minPotentialV = Integer.MAX_VALUE;
                minPotentialVColumn = 0;
                for (j = 1; width > j; j++) {
                    if (!used[j]) {
                        current = matrix[prev_i - 1][j - 1] - potentialsU[prev_i] - potentialsV[j];
                        if (minPotentialsV[j] > current) {
                            minPotentialsV[j] = current;
                            way[j] = prev_j;
                        }
                        if (minPotentialV > minPotentialsV[j]) {
                            minPotentialV = minPotentialsV[j];
                            minPotentialVColumn = j;
                        }
                    }
                }
                for (j = 0; width > j; j++) {
                    if (used[j]) {
                        potentialsU[pairing[j]] += minPotentialV;
                        potentialsV[j] -= minPotentialV;
                    } else {
                        minPotentialsV[j] -= minPotentialV;
                    }
                }
                prev_j = minPotentialVColumn;
            } while (0 != pairing[prev_j]);
            do {
                minPotentialVColumn = way[prev_j];
                pairing[prev_j] = pairing[minPotentialVColumn];
                prev_j = minPotentialVColumn;
            } while (0 != prev_j);
        }
        formResult(width, pairing);
        return -potentialsV[0];
    }

    public void formResult(int width, int[] pairing){
        for (int i = 1; i < width; i++) {
            this.encoding.add(0);
        }
        if (null != this.encoding) {
            for (int i = 1; i < width; i++) {
                int prev_i = pairing[i];
                if (0 != prev_i) {
                    this.encoding.set(pairing[i] - 1, i - 1);
                }
            }
        }
    };
    public void test() {
        System.out.println("----\nDiscrete representation:");
//        int[][] discreteRepresentationData = Utility.generateRandomMatrix(4, 4, 24);
        int[][] discreteRepresentationData = {
                {14, 10, 15, 20},
                {10, 10, 35, 25},
                {15, 35, 20, 30},
                {20, 25, 30, 1}
        };
        Utility.printMatrix(discreteRepresentationData);
        int generalizedAssignedProblemResult = this.objectiveFunction(discreteRepresentationData);
        System.out.println("Discrete representation result: " + generalizedAssignedProblemResult);
        this.printEncoding();

    }
}