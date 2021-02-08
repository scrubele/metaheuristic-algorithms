package main.java.com.scrubele;

import main.java.com.scrubele.representations.TravellingSalesmanSolution;
import main.java.com.scrubele.strategies.CoolingStrategy;
import main.java.com.scrubele.strategies.EquilibriumStateStrategy;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.TemperatureStrategy;
import main.java.com.scrubele.utilities.Utility;

import java.util.ArrayList;

public class TravellingSalesmanProblem extends SimulatedAnnealing {

    TravellingSalesmanSolution solution;
    TravellingSalesmanSolution bestSolution;

    public TravellingSalesmanProblem(){
        super();
        this.optimizationStrategy = OptimizationStrategy.MINIMIZATION;
    }

    public TravellingSalesmanProblem(OptimizationStrategy optimizationStrategy,
                               TemperatureStrategy temperatureStrategy,
                               EquilibriumStateStrategy equilibriumStateStrategy,
                               CoolingStrategy coolingStrategy) {
        super(optimizationStrategy, temperatureStrategy, equilibriumStateStrategy, coolingStrategy);
    }

    public TravellingSalesmanSolution objectiveFunction(ArrayList<ArrayList<Float>> weights) {
        solution = new TravellingSalesmanSolution(weights);
        bestSolution = solution;
        equilibriumStateStrategy.setValue(solution.getSize());
        int iteration = 0;
        while (temperatureStrategy.isStoppedCriteria(temperature)) {
            int epoch = 0;
            while (equilibriumStateStrategy.isConditionAccepted(epoch)) {
                TravellingSalesmanSolution neighbourSolution = solution.generateRandomNeighbour();
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
        writeToCSV();
        return bestSolution;
    }

    public double findLoss(TravellingSalesmanSolution previousSolution, TravellingSalesmanSolution solution) {
        return this.optimizationStrategy.getSolutionDifference(previousSolution.value, solution.value);
    }

    public boolean isBetterSolution(TravellingSalesmanSolution previousSolution,
                                    TravellingSalesmanSolution solution) {
        return this.optimizationStrategy.isBetterSolution(previousSolution.value, solution.value);
    }


    public void acceptSolution(TravellingSalesmanSolution neighbourSolution) {
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
        Utility.writeSolutionsToCSV(candidateSolutionValues, "travelling_salesman.xls");
    }

}
