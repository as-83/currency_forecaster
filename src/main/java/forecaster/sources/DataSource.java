package forecaster.sources;

import forecaster.domain.Rate;

import java.util.List;

/**
 * Получение списка исторических значений
 * курса валюты по коду данной валюты
 */
public interface DataSource {
    /**
     * @param currencyCode код валюты
     * @return Исторических значения курса валюты
     */
    List<Rate> getRates(String currencyCode);
}
