package main.java.com.scrubele;

import main.java.com.scrubele.representations.LinearRepresentation;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.SelectionStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.*;


public class LocalSearchAlgorithm extends LinearRepresentation {

    public List<Integer> encoding;
    public SelectionStrategy selectionStrategy;
    public OptimizationStrategy optimizationStrategy = OptimizationStrategy.MAXIMIZATION;
    public Map<List<Integer>, Float> resultSolutionToValue;
    int epoch;

    public LocalSearchAlgorithm() {
        super();
        this.encoding = new ArrayList<>();
        this.selectionStrategy = SelectionStrategy.BEST_IMPROVEMENT;
    }

    public LocalSearchAlgorithm(SelectionStrategy selectionStrategy) {
        super();
        this.encoding = new ArrayList<>();
        this.selectionStrategy = selectionStrategy;
    }

    @Override
    public final List<Number> getEncoding() {
        return new ArrayList<>(this.encoding);
    }

    @Override
    public void printEncoding() {
        super.printEncoding();
    }

    public Map<List<Integer>, Float> localSearchAlgorithm(Object weights, int maxEpoch) {
        this.resultSolutionToValue = getSolutionConvergences(Set.of(this.encoding), weights);
        this.epoch = 0;
        while (epoch < maxEpoch) {
            System.out.println("\nEpoch:" + (this.epoch + 1) + this.encoding);
            Set<List<Integer>> candidateSolutions = generateSolutions(this.encoding);
            LinkedHashMap<List<Integer>, Float> candidateSolutionsToValue = getSolutionConvergences(candidateSolutions,
                    weights);
            List<Integer> encoding = getCandidateSolution(this.resultSolutionToValue, candidateSolutionsToValue);
            if (isBestNeighbourLocalOptima(encoding)) {
                break;
            }
            this.encoding = encoding;
            this.prepareDataForPlotting(candidateSolutionsToValue);
            this.epoch += 1;
        }
        return this.resultSolutionToValue;
    }

    public boolean isBestNeighbourLocalOptima(List<Integer> encoding) {
        if (this.encoding.equals(encoding)) {
            System.out.println("Global optimum solution:" + this.resultSolutionToValue);
            return true;
        }
        return false;
    }

    public Set<List<Integer>> generateSolutions(List<Integer> initialSolution) {
        Set<List<Integer>> candidateSolutions = new HashSet<>();
        for (int i = 0; i < initialSolution.size(); i++) {
            List<Integer> currentSolution = new ArrayList<>(initialSolution);
            currentSolution = flipValueByIndex(currentSolution, i);
//            System.out.println(currentSolution);
            candidateSolutions.add(currentSolution);
        }
        return candidateSolutions;
    }

    public int flipValue(int value) {
        return value ^ 1;
    }

    public List<Integer> flipValueByIndex(List<Integer> previousSolution, int itemToFlip) {
        int newItemValue = flipValue(previousSolution.get(itemToFlip));
        previousSolution.set(itemToFlip, newItemValue);
        return previousSolution;
    }

    public List<Integer> getCandidateSolution(Map<List<Integer>, Float> initialSolutionToValue,
                                              Map<List<Integer>, Float> candidateSolutionsToValue) {
        this.resultSolutionToValue = selectionStrategy.getSolution(initialSolutionToValue,
                candidateSolutionsToValue,
                optimizationStrategy);
        return this.resultSolutionToValue.keySet().stream().findFirst().get();
    }

    public float getConvergence(Object weights, List<Integer> solution) {
        return 0;
    }

    public LinkedHashMap<List<Integer>, Float> getSolutionConvergences(Set<List<Integer>> candidateSolutions,
                                                                       Object weights) {
        LinkedHashMap<List<Integer>, Float> candidateSolutionValues = new LinkedHashMap<>();
        for (List<Integer> candidateSolution : candidateSolutions) {
            float value = getConvergence(weights, candidateSolution);
            candidateSolutionValues.put(candidateSolution, value);
        }
        for (Map.Entry<List<Integer>, Float> entry : candidateSolutionValues.entrySet()) {
            System.out.println(entry.getKey() + "|" + entry.getValue());
//            System.out.println(entry.getKey().stream().map(Object::toString).collect(Collectors.joining("_")) + "|" + entry.getValue());
        }
        return candidateSolutionValues;
    }

    public void prepareDataForPlotting(Map<List<Integer>, Float> candidateSolutionsToValue, String name) {
        Map<String, Float> candidateSolutionsToIntValue =
                Utility.convertMapKeyToString(candidateSolutionsToValue);
        Utility.writeHashMapToCsv(candidateSolutionsToIntValue, name);
    }

    public void prepareDataForPlotting(Map<List<Integer>, Float> candidateSolutionsToValue) {
        Map<String, Float> candidateSolutionsToIntValue =
                Utility.convertMapKeyToString(candidateSolutionsToValue);
        Utility.writeHashMapToCsv(candidateSolutionsToIntValue, "result.xls");
    }

    public void prepareDataForPlotting(Map<List<Integer>, Float> candidateSolutionsToValue,
                                       Map<List<Integer>, Float> descent) {
        Map<String, Float> candidateSolutionsToIntValue =
                Utility.convertMapKeyToString(candidateSolutionsToValue);
        Map<String, Float> descentT0Value =
                Utility.convertMapKeyToString(descent);
        Utility.writeHashMapToCsv(candidateSolutionsToIntValue, descentT0Value, "result.xls");
    }
}