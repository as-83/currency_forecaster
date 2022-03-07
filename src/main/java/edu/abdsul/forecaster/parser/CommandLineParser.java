package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс CommandLineParser выполняет
 * парсинг команд из строки:
 * код валюты и срок прогноза в днях
 */
public class CommandLineParser implements Parser {

    private Command command;
    private static final int ACTION_COMMAND_INDEX = 0;
    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_PERIOD_INDEX = 2;
    private static final String ACTION_COMMAND = "rate";

    public static final String DATE_PATTERN = "^\\d{2}.\\d{2}.\\d{4}$";

    /**
     * Парсинг команд из строки
     *
     * @param commandLine строка с командами вида  "rate USD,TRY -period week -alg moon -output graph"
     * @return класс Command с полями: код валюты и срок прогноза в днях
     */
    @Override
    public List<Command> parse(String commandLine) {
        List<Command> commands = new ArrayList<>();

        command = new Command();

        String[] commandLineParts = commandLine.trim().toUpperCase().split(" ");

        if (isValid(commandLineParts)) {
            String currencyCode = commandLineParts[CURRENCY_CODE_INDEX];
            String fPeriod = commandLineParts[FORECAST_PERIOD_INDEX];
            ForecastPeriod forecastPeriod = ForecastPeriod.valueOf(fPeriod);
            command.setCurrencyCode(CurrencyCode.valueOf(currencyCode));
            command.setForecastPeriod(forecastPeriod.getDayCount());
            command.setCorrect(true);
        }
        commands.add(command);
        return commands;
    }

    /**
     * Валидация команд
     *
     * @param commandLineParts массив комманд
     * @return true если все комманды корректные, иначе false
     */
    private boolean isValid(String[] commandLineParts) {
        if (commandLineParts.length < 3) {
            return false;
        }

        boolean isActionArgValid = ACTION_COMMAND.equalsIgnoreCase(commandLineParts[ACTION_COMMAND_INDEX]);

        boolean isCurrencyCodeValid = isCurrencyCodesValid(commandLineParts[CURRENCY_CODE_INDEX]);

        boolean isPeriodValid = Arrays.stream(ForecastPeriod.values())
                .map(Enum::name)
                .anyMatch(p -> p.equalsIgnoreCase(commandLineParts[FORECAST_PERIOD_INDEX]))
                || commandLineParts[FORECAST_PERIOD_INDEX].matches(DATE_PATTERN);

        return isActionArgValid && isPeriodValid && isCurrencyCodeValid;
    }

    public boolean isCurrencyCodesValid(String commandLinePart) {
        return Arrays.stream(commandLinePart.split(","))
                .allMatch(s -> Arrays.stream(CurrencyCode.values())
                        .map(Enum::name)
                        .anyMatch(c -> c.equalsIgnoreCase(s)));
    }
}
