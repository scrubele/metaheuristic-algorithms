import java.util.List;

public class PermutationRepresentation extends LinearRepresentation {

    public boolean[] isVisited;

    PermutationRepresentation() {
        super();
    }

    @Override
    public final List<Number> getEncoding() {
        return super.getEncoding();
    }

    @Override
    public void printEncoding() {
        super.printEncoding();
    }

    public int objectiveFunction(int[][] citiesGraph, int currentPosition, int count, int cost) {
        /*
            Traveler Salesman Problem.
         */
        int numberOfCities = citiesGraph.length;
        boolean[] isVisited = new boolean[numberOfCities];
        isVisited[0] = true;
        int minWeight = Integer.MAX_VALUE;
        minWeight = calculateMinDistance(citiesGraph, isVisited, currentPosition, numberOfCities, count, cost, minWeight);
        return minWeight;
    }

    public int calculateMinDistance(int[][] citiesGraph, boolean[] isVisited,
                                    int currentPosition, int numberOfCities,
                                    int count, int cost, int result) {
        if (count == numberOfCities && citiesGraph[currentPosition][0] > 0) {
            result = Math.min(result, cost + citiesGraph[currentPosition][0]);
            return result;
        }

        for (int i = 0; i < numberOfCities; i++) {
            if (isVisited[i] == false && citiesGraph[currentPosition][i] > 0) {
                isVisited[i] = true;
                result = calculateMinDistance(citiesGraph, isVisited, i, numberOfCities, count + 1,
                        cost + citiesGraph[currentPosition][i], result);
                isVisited[i] = false;
            }
        }
        return result;
    }

    public void test() {
        System.out.println("----\nPermutation representation:");
//        int[][] permutationRepresentationData = Utility.generateRandomMatrix(4, 4, 24);
        int[][] permutationRepresentationData = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        Utility.printMatrix(permutationRepresentationData);
        int minWeight = this.objectiveFunction(permutationRepresentationData, 0, 1, 0);
        System.out.println("Permutation representation result: " + minWeight);
    }
}