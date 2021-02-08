package test.java.com.scrubele;

import main.java.com.scrubele.MaximizationProblem;
import main.java.com.scrubele.strategies.SelectionStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MaximizationProblemTest {

    public void test1(int i) {
        Utility.clearTheFile("result.xls");
        MaximizationProblem maximizationProblem = new MaximizationProblem(SelectionStrategy.FIRST_IMPROVEMENT);
        List<Integer> equationCoefficients = Arrays.asList(1, -60, 900, 100);
        int minResultValue = 9;
        int maxResultValue = 32;
        int maxEpoch = 20;
        Map<List<Integer>, Float> results = maximizationProblem.objectiveFunction(equationCoefficients, minResultValue,
                maxResultValue, maxEpoch);
    }

    public void test() {
        test1(1);
    }
}


