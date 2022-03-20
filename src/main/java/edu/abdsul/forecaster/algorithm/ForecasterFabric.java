package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс ForecasterFabric создает объекты классов реализующих интерфей
 * Forecaster
 */
public class ForecasterFabric {
    private static final Logger logger = LoggerFactory.getLogger(ForecasterFabric.class);

    /**
     * Создание объектов классов реализующих интерфей Forecaster
     *
     * @param algorithm перечисление содержащее название алгоритмов прогнозирования курса валюты
     * @return Объект класса реализующего интерфей Forecaster
     */
    public Forecaster getForecaster(Algorithm algorithm) {
        Forecaster forecaster;
        switch (algorithm) {
            case ACTUAL:
                forecaster = new ActualAlgorithmForecaster();
                logger.debug("ActualAlgorithmForecaster created");
                break;
            case MYSTIC:
                forecaster = new MysticAlgorithmForecaster();
                logger.debug("MysticAlgorithmForecaster created");
                break;
            case LINEAR_REGRESSION:
                forecaster = new LinearRegressionForecaster();
                logger.debug("LinearRegressionForecaster created");
                break;
            default:
                forecaster = new LastSevenAvgForecaster();
        }
        return forecaster;
    }
}
