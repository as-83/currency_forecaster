package edu.abdsul.forecaster.source;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;

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
    List<Rate> getRates(CurrencyCode currencyCode);
}
