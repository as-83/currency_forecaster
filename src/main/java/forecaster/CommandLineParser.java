package forecaster;

import forecaster.domain.Command;
import forecaster.domain.Currency;
import forecaster.domain.ForecastPeriod;

import java.util.Arrays;

public class CommandLineParser {

    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_PERIOD_INDEX = 2;

    public static Command parse(String commandLine) {

        Command command = new Command();

        String[] commandLineParts = commandLine.trim().split(" ");

        if (isValid(commandLineParts)) {
            String currencyCode = commandLineParts[CURRENCY_CODE_INDEX];
            int forecastPeriod = Integer.parseInt(commandLineParts[FORECAST_PERIOD_INDEX]);
            command.setCurrencyCode(Currency.valueOf(currencyCode));
            command.setForecastPeriod(forecastPeriod);
            command.setCorrect(true);
        }

        return command;
    }

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
