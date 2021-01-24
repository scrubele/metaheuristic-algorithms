package main.java.com.scrubele.strategies;

public enum OptimizationStrategy {
    MAXIMIZATION {
        @Override
        public boolean isBetterSolution(float value, float value1) {
            return value > value1;
        }
    },
    MINIMIZATION {
        @Override
        public boolean isBetterSolution(float value, float value1) {
            return value < value1;
        }

    };

    public abstract boolean isBetterSolution(float value, float value1);
}
