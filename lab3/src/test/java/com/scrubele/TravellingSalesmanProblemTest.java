package test.java.com.scrubele;

import main.java.com.scrubele.TravellingSalesmanProblem;
import main.java.com.scrubele.strategies.SelectionStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.ArrayList;

public class TravellingSalesmanProblemTest {

    public void test1(int i) {
        Utility.clearTheFile("result.xls");
        Utility.clearTheFile("descents.xls");
        TravellingSalesmanProblem travellingSalesmanProblem = new TravellingSalesmanProblem(SelectionStrategy.BEST_IMPROVEMENT);
        ArrayList<ArrayList<Float>> inputMatrix = Utility.readMatrixFromFile("gr17.2085.tsp");
        System.out.println("City data:");
        Utility.printMatrix(inputMatrix);
        int maxEpoch = 20;
        travellingSalesmanProblem.objectiveFunction(inputMatrix, maxEpoch);
    }

    public void test() {
        test1(1);
    }
}


