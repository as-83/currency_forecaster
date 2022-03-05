package edu.abdsul.forecaster.source;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Получение списка исторических значений
 * курса валюты по коду данной валюты
 */
public interface DataSource {
    /**
     * @param currencyCode код валюты
     * @return Исторических значения курса валюты
     */
    //List<Rate> getAllRates(CurrencyCode currencyCode);

    Rate getAllRates(CurrencyCode currencyCode);

}
