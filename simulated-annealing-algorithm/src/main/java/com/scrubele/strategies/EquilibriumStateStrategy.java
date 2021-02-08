package main.java.com.scrubele.strategies;

public enum EquilibriumStateStrategy {

    STATIC {
    },
    ADAPTIVE {
    };

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getProbability(Double equilibriumStateDifference, Double temperature) {
        /* Boltzmann distribution */
        return Math.exp(-(equilibriumStateDifference) / temperature);
    }

    public boolean isConditionAccepted(int epoch) {
        return epoch < this.getValue();
    }

    public boolean isSolutionAccepted(Double equilibriumStateDifference, Double temperature) {
        if (this.getProbability(equilibriumStateDifference, temperature) > Math.random()) {
            return true;
        } else
            return false;
    }
}
