package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Algorithm;

public class ForecasterFabric {
    public Forecaster getForecaster(Algorithm algorithm) {
        Forecaster forecaster;
        switch (algorithm) {
            case ACTUAL: forecaster = new ActualAlgorithmForecaster(); break;
            case MYSTIC: forecaster = new MysticAlgorithmForecaster(); break;
            case LINEAR_REGRESSION: forecaster = new LinearRegressionForecaster(); break;
            default: forecaster = new LastSevenAvgForecaster();
        }
        return forecaster;
    }
}
