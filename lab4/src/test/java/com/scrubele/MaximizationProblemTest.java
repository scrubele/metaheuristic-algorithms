package test.java.com.scrubele;

import main.java.com.scrubele.MaximizationProblem;
import main.java.com.scrubele.SimulatedAnnealing;
import main.java.com.scrubele.representations.MaximizationProblemSolution;
import main.java.com.scrubele.strategies.CoolingStrategy;
import main.java.com.scrubele.strategies.EquilibriumStateStrategy;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.TemperatureStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.Arrays;
import java.util.List;

public class MaximizationProblemTest {

    public void test() {
        Utility.clearTheFile("maximization_algo.xls");

        OptimizationStrategy optimizationStrategy = OptimizationStrategy.MAXIMIZATION;
        TemperatureStrategy temperatureStrategy = TemperatureStrategy.ACCEPT_ALL;
        EquilibriumStateStrategy equilibriumStateStrategy = EquilibriumStateStrategy.STATIC;
        CoolingStrategy coolingStrategy = CoolingStrategy.GEOMETRIC;

        MaximizationProblem simulatedAnnealingAlgo = new MaximizationProblem();
        System.out.println(simulatedAnnealingAlgo);

        List<Integer> equationCoefficients = Arrays.asList(1, -60, 900, 100);
        int minResultValue = 9;
        int maxResultValue = 32;
        MaximizationProblemSolution maximizationProblemSolution =
                simulatedAnnealingAlgo.objectiveFunction(minResultValue, maxResultValue, equationCoefficients);
        System.out.println("result" + maximizationProblemSolution);
    }

}


