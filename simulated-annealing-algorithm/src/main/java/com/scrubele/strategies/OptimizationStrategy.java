package main.java.com.scrubele.strategies;

public enum OptimizationStrategy {
    MAXIMIZATION {
        @Override
        public boolean isBetterSolution(float value, float currentValue) {
            return currentValue > value;
        }
    },
    MINIMIZATION {
        @Override
        public boolean isBetterSolution(float value, float currentValue) {
            return currentValue < value;
        }
    };

    public abstract boolean isBetterSolution(float value, float currentValue);

    public float getSolutionDifference(float value, float currentValue) {
        return currentValue - value;
    }
}
