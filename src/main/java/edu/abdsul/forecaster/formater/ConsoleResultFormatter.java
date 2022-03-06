package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.Rate;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class ConsoleResultFormatter implements ResultFormatter {
    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private static final String EMPTY_RESULT_MESSAGE = "Результат отсутствует!";
    private static final String DATE_PATTERN = "E dd.MM.yyyy";

    /**
     * Форматирует данные прогноза
     *  Пример: Вт 22.02.2022 - 75,45
     *
     * @param rate Обьект содержащий данные прогноза курса валюты
     * @return Отформатированный прогноз
     */
    @Override
    public String format(Rate rate) {
        StringBuilder result = new StringBuilder();
        if (rate.getRates().isEmpty()) {
            return EMPTY_RESULT_MESSAGE;
        }

        DecimalFormat valueFormatter = new DecimalFormat("###.##");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        rate.getRates().forEach((key, value1) -> {
            String date = dateFormatter.format(key);
            date = date.substring(0, 1).toUpperCase() + date.substring(1);
            String value = valueFormatter.format(value1);
            result.append(date).append(" - ").append(value).append("\n");
        });
        return result.toString();
    }
}
