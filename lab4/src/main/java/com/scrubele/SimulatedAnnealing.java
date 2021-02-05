package main.java.com.scrubele;

import main.java.com.scrubele.representations.IntegerRepresentationSolution;
import main.java.com.scrubele.representations.MaximizationProblemSolution;
import main.java.com.scrubele.strategies.CoolingStrategy;
import main.java.com.scrubele.strategies.EquilibriumStateStrategy;
import main.java.com.scrubele.strategies.OptimizationStrategy;
import main.java.com.scrubele.strategies.TemperatureStrategy;

import java.util.LinkedList;

public class SimulatedAnnealing {

    OptimizationStrategy optimizationStrategy;
    TemperatureStrategy temperatureStrategy;
    EquilibriumStateStrategy equilibriumStateStrategy;
    CoolingStrategy coolingStrategy;

    double temperature;
    LinkedList<IntegerRepresentationSolution> candidateSolutionValues = new LinkedList<>();

    public SimulatedAnnealing() {
        this.optimizationStrategy = OptimizationStrategy.MINIMIZATION;
        this.temperatureStrategy = TemperatureStrategy.ACCEPT_ALL;
        this.equilibriumStateStrategy = EquilibriumStateStrategy.STATIC;
        this.coolingStrategy = CoolingStrategy.GEOMETRIC;
        this.temperature = this.temperatureStrategy.getValue();
    }

    public SimulatedAnnealing(OptimizationStrategy optimizationStrategy,
                              TemperatureStrategy temperatureStrategy,
                              EquilibriumStateStrategy equilibriumStateStrategy,
                              CoolingStrategy coolingStrategy) {
        this.optimizationStrategy = optimizationStrategy;
        this.temperatureStrategy = temperatureStrategy;
        this.equilibriumStateStrategy = equilibriumStateStrategy;
        this.coolingStrategy = coolingStrategy;
    }


    public void updateTemperature(int iteration) {
        this.temperature = coolingStrategy.updateTemperature(this.temperatureStrategy.getValue(), this.temperature,
                iteration);
    }


    @Override
    public String toString() {
        return "SimulatedAnnealingAlgo{" +
                "optimizationStrategy=" + optimizationStrategy +
                ", temperatureStrategy=" + temperatureStrategy +
                ", equilibriumStateStrategy=" + equilibriumStateStrategy +
                ", coolingStrategy=" + coolingStrategy +
                '}';
    }
}
