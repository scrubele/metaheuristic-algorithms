package main.java.com.scrubele;

import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.SelectionStrategy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TravellingSalesmanProblem extends LocalSearchAlgorithm {

    public TravellingSalesmanProblem() {
        super();
    }

    public TravellingSalesmanProblem(SelectionStrategy selectionStrategy) {
        super(selectionStrategy);
        this.optimizationStrategy = OptimizationStrategy.MINIMIZATION;
        System.out.println(this);
    }

    public Map<List<Integer>, Float> objectiveFunction(ArrayList<ArrayList<Float>> weights,
                                                       int maxEpoch) {
        maxEpoch = Math.min(maxEpoch, weights.size() - 1);
        this.encoding = initializeSolution(weights);
        System.out.println(this.encoding);
        this.resultSolutionToValue = getSolutionConvergences(Set.of(this.encoding), weights);
        this.prepareDataForPlotting(this.resultSolutionToValue);
        this.epoch = 0;
        while (epoch < maxEpoch) {
            System.out.println("\nEpoch:" + (this.epoch + 1) + this.encoding);
            Set<List<Integer>> candidateSolutions = generateSolutions(weights, this.resultSolutionToValue);
            LinkedHashMap<List<Integer>, Float> candidateSolutionsToValue = getSolutionConvergences(candidateSolutions,
                    weights);
            List<Integer> encoding = getCandidateSolution(this.resultSolutionToValue, candidateSolutionsToValue);
            this.encoding = encoding;
            this.prepareDataForPlotting(candidateSolutionsToValue, this.resultSolutionToValue);
            this.prepareDataForPlotting(this.resultSolutionToValue, "descents.xls");
            this.epoch += 1;
        }
        System.out.println("Global optimum solution:" + this.resultSolutionToValue);
        return this.resultSolutionToValue;
    }

    public List<Integer> initializeSolution(ArrayList<ArrayList<Float>> weights) {
        int solutionSetSize = weights.size();
        List<Integer> cityRange = IntStream.rangeClosed(0, solutionSetSize - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(cityRange);
        return cityRange;
    }

    public Set<List<Integer>> generateSolutions(ArrayList<ArrayList<Float>> weights,
                                                Map<List<Integer>, Float> initialSolutionToValue) {
        Set<List<Integer>> candidateSolutions = new HashSet<>();
        candidateSolutions.addAll(generateSolutionSet(candidateSolutions, weights,
                initialSolutionToValue));
        return candidateSolutions;
    }

    public Set<List<Integer>> generateSolutionSet(Set<List<Integer>> candidateSolutions,
                                                  ArrayList<ArrayList<Float>> weights,
                                                  Map<List<Integer>, Float> initialSolutionToValue) {
        List<Integer> initialSolution = initialSolutionToValue.keySet().stream().findFirst().get();
        Float weight = initialSolutionToValue.get(initialSolution);
        int len = initialSolution.size();
        for (int i = 1; i < len + 1; i++) {
            List<Integer> currentSolution = new ArrayList<>(initialSolution);
            if (i - this.epoch > 0) {
                currentSolution = generateSolution(currentSolution, this.epoch, i);
                float currentWeight = getConvergence(weights, currentSolution);
                candidateSolutions.add(currentSolution);
                if (OptimizationStrategy.MAXIMIZATION.isBetterSolution(currentWeight, weight)) {
                    initialSolution = currentSolution;
                    weight = currentWeight;
                    initialSolutionToValue = Map.of(initialSolution, weight);
//                    System.out.println(initialSolution+ " " + weight);
                    if (this.selectionStrategy != SelectionStrategy.FIRST_IMPROVEMENT)
                        candidateSolutions.addAll(generateSolutionSet(candidateSolutions, weights,
                                initialSolutionToValue));
                }
            }
        }
        return candidateSolutions;
    }

    public List<Integer> generateSolution(List<Integer> previousSolution, int startItem, int lastItem) {
        if (lastItem - startItem > 0) {
            List<Integer> newList = new ArrayList<>();
            List<Integer> beginning = new ArrayList<>();
            List<Integer> ending = new ArrayList<>();
            List<Integer> sublist = previousSolution.subList(startItem, lastItem);
            Collections.reverse(sublist);
            if (startItem > 0)
                beginning = previousSolution.subList(0, startItem);
            if (lastItem < previousSolution.size())
                ending = previousSolution.subList(lastItem, previousSolution.size());
            newList.addAll(beginning);
            newList.addAll(sublist);
            newList.addAll(ending);
            return newList;
        }
        return previousSolution;

    }


    public float getConvergence(Object weights, List<Integer> solution) {
        ArrayList<ArrayList<Float>> localWeights = (ArrayList<ArrayList<Float>>) weights;
        float solutionWeight = 0;
        List<Integer> currentSolution = new ArrayList<>(solution);
        currentSolution.add(0, solution.get(solution.size() - 1));
        for (int previousIndex = 0, currentIndex = 1; currentIndex < currentSolution.size(); previousIndex++,
                currentIndex++) {
            int cityFrom = currentSolution.get(previousIndex);
            int cityTo = currentSolution.get(currentIndex);
            float weight = localWeights.get(cityFrom).get(cityTo);
            solutionWeight += weight;
        }
        return solutionWeight;
    }

    @Override
    public String toString() {
        return "TravellingSalesmanProblem{" +
                "selectionStrategy=" + selectionStrategy + ',' +
                "optimizationStrategy=" + optimizationStrategy +
                '}';
    }
}