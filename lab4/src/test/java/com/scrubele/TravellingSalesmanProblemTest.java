package test.java.com.scrubele;

import main.java.com.scrubele.TravellingSalesmanProblem;
import main.java.com.scrubele.representations.TravellingSalesmanSolution;
import main.java.com.scrubele.strategies.CoolingStrategy;
import main.java.com.scrubele.strategies.EquilibriumStateStrategy;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.TemperatureStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.ArrayList;

public class TravellingSalesmanProblemTest {

    public void test1(int i) {
        Utility.clearTheFile("travelling_salesman.xls");

        TravellingSalesmanProblem simulatedAnnealingAlgo = new TravellingSalesmanProblem();
        System.out.println(simulatedAnnealingAlgo);

        ArrayList<ArrayList<Float>> inputMatrix = Utility.readMatrixFromFile("br17.39.atsp");
        System.out.println("City data:");
        Utility.printMatrix(inputMatrix);

        TravellingSalesmanSolution travellingSalesmanSolution = simulatedAnnealingAlgo.objectiveFunction(inputMatrix);
        System.out.println("result" + travellingSalesmanSolution);
    }

    public void test() {
        test1(1);
    }
}


