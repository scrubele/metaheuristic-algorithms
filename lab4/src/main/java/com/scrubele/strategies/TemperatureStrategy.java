package main.java.com.scrubele.strategies;

public enum TemperatureStrategy {

    ACCEPT_ALL {
        @Override
        public float getValue() {
            return 1;
        }
    },
    ACCEPT_DEVIATION {
        @Override
        public float getValue() {
            return 0;
        }
    },
    ACCEPT_RATIO {
        @Override
        public float getValue() {
            return 0;
        }
    };

    public double getBestTemperature() {
        return bestTemperature;
    }

    public void setBestTemperature(double bestTemperature) {
        this.bestTemperature = bestTemperature;
    }

    private double bestTemperature = 0.0001;

    //    public double setValues(OptimizationStrategy optimizationStrategy){
//        if(optimizationStrategy.equals(OptimizationStrategy.MAXIMIZATION)){
//
//        } else {
//
//        }
//    }
    public abstract float getValue();


    public boolean isStoppedCriteria(double temperature) {
        return temperature > bestTemperature;
    }

    ;
}