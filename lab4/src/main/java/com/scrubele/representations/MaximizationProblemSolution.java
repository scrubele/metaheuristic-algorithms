package main.java.com.scrubele.representations;

import main.java.com.scrubele.utilities.Utility;

import java.util.ArrayList;
import java.util.List;


public class MaximizationProblemSolution extends IntegerRepresentationSolution {

    int minValue;
    int maxValue;
    List<Integer> weights;

    public MaximizationProblemSolution(int minValue, int maxValue, Object weights) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.weights = (List<Integer>) weights;
        initializeSolution();
        getConvergence(this.weights);
        System.out.println(this);
    }

    public MaximizationProblemSolution(List<Integer> encoding, List<Integer> weights) {
        this.encoding = encoding;
        this.weights = weights;
        getConvergence(this.weights);
    }

    public void initializeSolution() {
        int initialValue = Math.round((float) Math.random() * (this.maxValue - this.minValue) + this.minValue);
        this.encoding = Utility.convertFromIntegerToBinaryList(initialValue);
    }

    public void getConvergence(Object weights) {
        List<Integer> localWeights = (List<Integer>) weights;
        int resultValue = 0;
        int solution = Utility.convertFromBinaryListToInteger(this.encoding);
        for (int i = 0; i < localWeights.size(); i++) {
            int power = localWeights.size() - i - 1;
            int coefficient = localWeights.get(i);
            int result = Math.round((float) Math.pow(solution, power) * coefficient);
            resultValue += result;
        }
        this.value = (float) resultValue;
    }

    public List<Integer> generateNeighbour(int itemToFlip) {
        List<Integer> currentSolution = new ArrayList<>(this.encoding);
        int newItemValue = Utility.flipValue(currentSolution.get(itemToFlip));
        currentSolution.set(itemToFlip, newItemValue);
        return currentSolution;
    }

    public MaximizationProblemSolution generateRandomNeighbour() {
        int itemToFlip = Math.round((float) Math.random() * (weights.size() - 1));
        return new MaximizationProblemSolution(generateNeighbour(itemToFlip), this.weights);
    }

    public int getSize() {
        return this.encoding.size();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
