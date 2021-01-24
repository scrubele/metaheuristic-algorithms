package main.java.com.scrubele;

import main.java.com.scrubele.strategies.SelectionStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.List;
import java.util.Map;


public class MaximizationProblem extends LocalSearchAlgorithm {

    public MaximizationProblem() {
        super();
    }

    public MaximizationProblem(SelectionStrategy selectionStrategy) {
        super(selectionStrategy);
        System.out.println(this);
    }

    public Map<List<Integer>, Float> objectiveFunction(List<Integer> weights,
                                                       int minValue,
                                                       int maxValue,
                                                       int maxEpoch) {
        System.out.println("Equation coefficients: " + weights +
                "\nValues in range: [" + minValue + "," + maxValue + "]\n" + "MaxEpoch: " + maxEpoch + "\n-------");
        this.encoding = initializeSolution(minValue, maxValue);
        return localSearchAlgorithm(weights, maxEpoch);
    }

    public List<Integer> initializeSolution(int minValue, int maxValue) {
        List<Integer> initialSolution;
        int initialValue = Math.round((float) Math.random() * (maxValue - minValue) + minValue);
        initialSolution = Utility.convertFromIntegerToBinaryList(initialValue);
        return initialSolution;
    }

    public float getConvergence(Object weights, List<Integer> binarySolution) {
        List<Integer> localWeights = (List<Integer>) weights;
        int resultValue = 0;
        int solution = Utility.convertFromBinaryListToInteger(binarySolution);
        for (int i = 0; i < localWeights.size(); i++) {
            int power = localWeights.size() - i - 1;
            int coefficient = localWeights.get(i);
            int result = Math.round((float) Math.pow(solution, power) * coefficient);
            resultValue += result;
        }
        return resultValue;
    }

    @Override
    public String toString() {
        return "BinaryRepresentation{" +
                "selectionStrategy=" + selectionStrategy + ',' +
                "optimizationStrategy=" + optimizationStrategy +
                '}';
    }
}