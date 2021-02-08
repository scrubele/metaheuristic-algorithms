package main.java.com.scrubele.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public enum SelectionStrategy {

    FIRST_IMPROVEMENT {
        @Override
        public Map<List<Integer>, Float> getSolution(Map<List<Integer>, Float> initialSolutionToValue,
                                                     Map<List<Integer>, Float> candidateSolutionsToValue,
                                                     OptimizationStrategy optimizationStrategy) {
            Map<List<Integer>, Float> firstDescent = initialSolutionToValue;
            float resultValue = initialSolutionToValue.get(initialSolutionToValue.keySet().stream().findFirst().get());
            for (Map.Entry<List<Integer>, Float> entry : candidateSolutionsToValue.entrySet()) {
                float value = entry.getValue();
                if (optimizationStrategy.isBetterSolution(value, resultValue)) {
                    firstDescent = Map.of(entry.getKey(), value);
                    break;
                }
            }
            System.out.println("Best neighbour local optima:" + firstDescent);
            return firstDescent;
        }
    },
    BEST_IMPROVEMENT {
        @Override
        public Map<List<Integer>, Float> getSolution(Map<List<Integer>, Float> initialSolutionToValue,
                                                     Map<List<Integer>, Float> candidateSolutionsToValue,
                                                     OptimizationStrategy optimizationStrategy) {
            Map<List<Integer>, Float> bestDescent = initialSolutionToValue;
            float resultValue = initialSolutionToValue.get(initialSolutionToValue.keySet().stream().findFirst().get());
            for (Map.Entry<List<Integer>, Float> entry : candidateSolutionsToValue.entrySet()) {
                float value = entry.getValue();
                if (optimizationStrategy.isBetterSolution(value, resultValue)) {
                    resultValue = value;
                    bestDescent = Map.of(entry.getKey(), value);
                }
            }
            System.out.println("Best neighbour local optima:" + bestDescent);
            return bestDescent;
        }
    },
    RANDOM_IMPROVEMENT {
        public List<Map<List<Integer>, Float>> generateDescentList(Map<List<Integer>, Float> initialSolutionToValue,
                                                                   Map<List<Integer>, Float> candidateSolutionsToValue,
                                                                   OptimizationStrategy optimizationStrategy) {
            float resultValue = initialSolutionToValue.get(initialSolutionToValue.keySet().stream().findFirst().get());
            List<Map<List<Integer>, Float>> descentList = new ArrayList<>();
            for (Map.Entry<List<Integer>, Float> entry : candidateSolutionsToValue.entrySet()) {
                float value = entry.getValue();
                if (optimizationStrategy.isBetterSolution(value, resultValue)) {
                    descentList.add(Map.of(entry.getKey(), value));
                }
            }
            return descentList;
        }

        public Map<List<Integer>, Float> pickRandom(List<Map<List<Integer>, Float>> descentList,
                                                    Map<List<Integer>, Float> initialSolutionToValue) {
            int descentListLen = descentList.size();
            Map<List<Integer>, Float> randomDescent;
            if (descentListLen > 0) {
                Random rand = new Random();
                randomDescent = descentList.get(rand.nextInt(descentListLen));
            } else {
                randomDescent = initialSolutionToValue;
            }
            return randomDescent;
        }

        @Override
        public Map<List<Integer>, Float> getSolution(Map<List<Integer>, Float> initialSolutionToValue,
                                                     Map<List<Integer>, Float> candidateSolutionsToValue,
                                                     OptimizationStrategy optimizationStrategy) {
            List<Map<List<Integer>, Float>> descentList = generateDescentList(initialSolutionToValue,
                    candidateSolutionsToValue,
                    optimizationStrategy);
            Map<List<Integer>, Float> randomDescent = pickRandom(descentList, initialSolutionToValue);
            System.out.println("Best neighbour local optima:" + randomDescent);
            return randomDescent;
        }
    };

    public abstract Map<List<Integer>, Float> getSolution(Map<List<Integer>, Float> initialSolutionToValue,
                                                          Map<List<Integer>, Float> candidateSolutionsToValue,
                                                          OptimizationStrategy optimizationStrategy);

}
