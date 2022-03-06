package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;

import java.util.Arrays;

/**
 * Класс CommandLineParser выполняет
 * парсинг команд из строки:
 * код валюты и срок прогноза в днях
 */
public class CommandLineParser implements Parser{

    private static final int ACTION_COMMAND_INDEX = 0;
    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_PERIOD_INDEX = 2;
    private static final String ACTION_COMMAND = "rate";

    /**
     * Парсинг команд из строки
     *
     * @param commandLine     строка с командами
     * @return класс Command с полями: код валюты и срок прогноза в днях
     */
    @Override
    public Command parse(String commandLine) {

        Command command = new Command();

        String[] commandLineParts = commandLine.trim().split(" ");

        if (isValid(commandLineParts)) {
            String currencyCode = commandLineParts[CURRENCY_CODE_INDEX].toUpperCase();
            String fPeriod = commandLineParts[FORECAST_PERIOD_INDEX].toUpperCase();
            ForecastPeriod forecastPeriod = ForecastPeriod.valueOf(fPeriod);
            command.setCurrencyCode(CurrencyCode.valueOf(currencyCode));
            command.setForecastPeriod(forecastPeriod.getDayCount());
            command.setCorrect(true);
        }

        return command;
    }

    /**
     * Валидация команд
     *
     * @param commandLineParts     массив комманд
     * @return true если все комманды корректные, иначе false
     */
    private boolean isValid(String[] commandLineParts) {
        if (commandLineParts.length != 3) {
            return false;
        }
        boolean isFirstArgValid = ACTION_COMMAND.equalsIgnoreCase(commandLineParts[ACTION_COMMAND_INDEX]);

        boolean isCurrencyCodeValid = Arrays.stream(CurrencyCode.values())
                .map(Enum::name)
                .anyMatch(c -> c.equalsIgnoreCase(commandLineParts[CURRENCY_CODE_INDEX]));
        boolean isPeriodValid = Arrays.stream(ForecastPeriod.values())
                .map(Enum::name)
                .anyMatch(p -> p.equalsIgnoreCase(commandLineParts[FORECAST_PERIOD_INDEX]));

        return isFirstArgValid && isPeriodValid && isCurrencyCodeValid;
    }
}
