package forecaster;

import forecaster.domain.Command;
import forecaster.domain.Currency;
import forecaster.domain.ForecastPeriod;

import java.util.Arrays;

/**
 * Класс CommandLineParser выполняет
 * парсинг команд из строки:
 * код валюты и срок прогноза в днях
 */
public class CommandLineParser {

    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_PERIOD_INDEX = 2;

    /**
     * Парсинг команд из строки
     *
     * @param commandLine     строка, введенная пользователем
     * @return класс Command с полями: код валюты и срок прогноза в днях
     */
    public static Command parse(String commandLine) {

        Command command = new Command();

        String[] commandLineParts = commandLine.trim().split(" ");

        if (isValid(commandLineParts)) {
            String currencyCode = commandLineParts[CURRENCY_CODE_INDEX].toUpperCase();
            String fPeriod = commandLineParts[FORECAST_PERIOD_INDEX].toUpperCase();
            ForecastPeriod forecastPeriod = ForecastPeriod.valueOf(fPeriod);
            command.setCurrencyCode(Currency.valueOf(currencyCode));
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
    public static boolean isValid(String[] commandLineParts) {
        if (commandLineParts.length != 3) {
            return false;
        }

        boolean isCurrencyCodeValid = Arrays.stream(Currency.values())
                .map(Enum::name)
                .anyMatch(c -> c.equalsIgnoreCase(commandLineParts[CURRENCY_CODE_INDEX]));
        boolean isPeriodValid = Arrays.stream(ForecastPeriod.values())
                .map(Enum::name)
                .anyMatch(p -> p.equalsIgnoreCase(commandLineParts[FORECAST_PERIOD_INDEX]));

        return isPeriodValid && isCurrencyCodeValid;
    }
}
