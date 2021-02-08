import java.util.ArrayList;
import java.util.List;

public class RealValueRepresentation extends LinearRepresentation {

    public List<Float> encoding;

    RealValueRepresentation() {
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

    public boolean objectiveFunction(float[][] array) {
        /*
            The Gaussian algorithm for solving the system of equations.
         */
        int n = array.length;
        boolean isSolution = reduceMatrix(array, n);
        isSolution = certifySolution(array, n, isSolution);
        calculateResult(array, n);
        return isSolution;
    }

    public boolean reduceMatrix(float[][] array, int n) {
        int i, j, k, c;
        boolean isSolution = false;
        float pro = 0;

        for (i = 0; i < n; i++) {
            if (array[i][i] == 0) {
                c = 1;
                while ((i + c) < n && array[i + c][i] == 0)
                    c++;
                if ((i + c) == n) {
                    isSolution = true;
                    break;
                }
                for (j = i, k = 0; k <= n; k++)
                    swap(array, j, k, c);
            }

            for (j = 0; j < n; j++) {
                if (i != j) {
                    pro = array[j][i] / array[i][i];
                    for (k = 0; k <= n; k++)
                        array[j][k] = array[j][k] - (array[i][k]) * pro;
                }
            }
        }
        return isSolution;
    }

    public boolean certifySolution(float[][] array, int n, boolean isSolution) {
        float sum = 0;
        if (!isSolution) {
            for (int i = 0; i < n; i++) {
                sum = 0;
                int j;
                for (j = 0; j < n; j++)
                    sum = sum + array[i][j];
                if (sum == array[i][j])
                    try {
                        throw new Exception("Infinite solution");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return isSolution;
    }

    public void calculateResult(float[][] a, int n) {
        for (int i = 0; i < n; i++)
            this.encoding.add(a[i][n] / a[i][i]);
    }

    private void swap(float[][] array, int j, int k, int c) {
        float temp = array[j][k];
        array[j][k] = array[j + c][k];
        array[j + c][k] = temp;
    }

    public void test() {
        System.out.println("----\nReal value representation:");
//        int[][] realValueRepresentationData = Utility.generateRandomMatrix(3, 4, 24);
        float[][] realValueRepresentationData = {
                {0, 2, 1, 4},
                {1, 1, 2, 6},
                {2, 1, 1, 7}
        };
        Utility.printMatrix(realValueRepresentationData);
        boolean isSolution = this.objectiveFunction(realValueRepresentationData);
        this.printEncoding();
    }
}

