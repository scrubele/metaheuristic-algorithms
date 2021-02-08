package main.java.com.scrubele;

import main.java.com.scrubele.representations.MaximizationProblemSolution;
import main.java.com.scrubele.strategies.CoolingStrategy;
import main.java.com.scrubele.strategies.EquilibriumStateStrategy;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.TemperatureStrategy;
import main.java.com.scrubele.utilities.Utility;

public class MaximizationProblem extends SimulatedAnnealing {

    MaximizationProblemSolution solution;
    MaximizationProblemSolution bestSolution;

    public MaximizationProblemSolution objectiveFunction(int minValue, int maxValue, Object weights) {
        solution = new MaximizationProblemSolution(minValue, maxValue, weights);
        bestSolution = solution;
        equilibriumStateStrategy.setValue(solution.getSize());
        int iteration = 0;
        while (temperatureStrategy.isStoppedCriteria(temperature)) {
            int epoch = 0;
            while (equilibriumStateStrategy.isConditionAccepted(epoch)) {
                MaximizationProblemSolution neighbourSolution = solution.generateRandomNeighbour();
                neighbourSolution.loss = findLoss(solution, neighbourSolution);
                neighbourSolution.temperature = temperature;
                System.out.println("Iteration: " + iteration + " | Epoch: " + epoch + " | " + neighbourSolution + "\t|" +
                        " Loss: " + neighbourSolution.loss + " | " +
                        "Temperature: " + temperature);
                acceptSolution(neighbourSolution);
                /* prepare for plotting */
                candidateSolutionValues.add(neighbourSolution);
                epoch++;
                iteration++;
            }
            updateTemperature(iteration);
        }
        System.out.println("Iteration number: "+ iteration);
        writeToCSV();
        return bestSolution;
    }

    public double findLoss(MaximizationProblemSolution previousSolution, MaximizationProblemSolution solution) {
        return this.optimizationStrategy.getSolutionDifference(previousSolution.value, solution.value);
    }

    public boolean isBetterSolution(MaximizationProblemSolution previousSolution,
                                    MaximizationProblemSolution solution) {
        return this.optimizationStrategy.isBetterSolution(previousSolution.value, solution.value);
    }


    public void acceptSolution(MaximizationProblemSolution neighbourSolution) {
        if (isBetterSolution(solution, neighbourSolution)) {
            solution = neighbourSolution;
        } else if (equilibriumStateStrategy.isSolutionAccepted(neighbourSolution.loss, temperature)) {
            solution = neighbourSolution;
        }
        if (isBetterSolution(bestSolution, this.solution)) {
            bestSolution = this.solution;
        }
    }

    public void writeToCSV() {
        Utility.writeSolutionsToCSV(candidateSolutionValues, "maximization_algo.xls");
    }

}
