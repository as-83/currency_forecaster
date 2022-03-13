package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.Rate;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TextResultFormatter implements ResultFormatter {


    /**
     * Форматирует данные прогноза
     * Пример: Вт 22.02.2022 - 75,45
     *
     * @param rates Список обьектов содержащих данные прогнозов курса валют
     * @return Отформатированный прогноз
     */
    @Override
    public String format(List<Rate> rates) {

        StringBuilder result = new StringBuilder();
        DecimalFormat valueFormatter = new DecimalFormat("###.##");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        if (rates.isEmpty()) {
            return EMPTY_RESULT_MESSAGE;
        }

        for (Rate rate : rates) {
            result.append(rate.getCurrencyCode()).append(" Номинал - ")
                    .append(rate.getNominal()).append("\n");
            rate.getRates().forEach((key, value1) -> {
                String date = dateFormatter.format(key);
                date = date.substring(0, 1).toUpperCase() + date.substring(1);
                String value = valueFormatter.format(value1);
                result.append(date).append(" - ").append(value).append("\n\n");
            });
        }

        return result.toString();
    }
}
