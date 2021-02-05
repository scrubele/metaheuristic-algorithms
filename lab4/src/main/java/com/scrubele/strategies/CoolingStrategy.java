package main.java.com.scrubele.strategies;

public enum CoolingStrategy {

    LINEAR {
        @Override
        public Double updateTemperature(double initTemperature, double previousTemperature, int iteration) {
            return initTemperature - iteration * getTemperatureUpdateRate();
        }
    },
    GEOMETRIC {
        @Override
        public Double updateTemperature(double initTemperature, double previousTemperature, int iteration) {
            return getTemperatureUpdateRate() * previousTemperature;
        }
    },
    LOGARITHMIC {
        @Override
        public Double updateTemperature(double initTemperature, double previousTemperature, int iteration) {
            return initTemperature / Math.log(iteration);
        }
    };

    public double getTemperatureUpdateRate() {
        return temperatureUpdateRate;
    }

    public void setTemperatureUpdateRate(double temperatureUpdateRate) {
        this.temperatureUpdateRate = temperatureUpdateRate;
    }

    private double temperatureUpdateRate;


    CoolingStrategy() {
        this.temperatureUpdateRate = 0.99;
    }

    CoolingStrategy(int rate) {
        this.temperatureUpdateRate = rate;
    }

    public abstract Double updateTemperature(double initTemperature, double previousTemperature, int iteration);

}
