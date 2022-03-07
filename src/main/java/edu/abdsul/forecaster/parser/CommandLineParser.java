package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс CommandLineParser выполняет
 * парсинг команд из строки:
 * код валюты и срок прогноза в днях
 */
public class CommandLineParser implements Parser {

    private static final int ALGORITHM_INDEX = 5;
    private Command command;
    private static final int ACTION_COMMAND_INDEX = 0;
    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_PERIOD_INDEX = 3;
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
        String[] commandLineParts = commandLine.trim().toUpperCase().split(" ");

        if (isValid(commandLineParts)) {
            String[] currencyCodes = commandLineParts[CURRENCY_CODE_INDEX].split(",");
            String fPeriod = "";
            String algorithm = "";
            String output = "list";

            for (int i = 2; i < commandLineParts.length; i++) {
                switch (commandLineParts[i]) {
                    case "-PERIOD":
                        fPeriod = commandLineParts[++i]; break;
                    case "-ALG" :
                        algorithm = commandLineParts[++i]; break;
                    case "-OUTPUT" :
                        output = commandLineParts[++i]; break;
                }
            }
            for (String currencyCode : currencyCodes) {
                Command command = new Command();
                command.setCurrencyCode(CurrencyCode.valueOf(currencyCode));
                if (!fPeriod.isEmpty()) {
                    command.setForecastPeriod(ForecastPeriod.valueOf(fPeriod).getDayCount());//TODO only enum
                }
                command.setOutput(Output.valueOf(output.toUpperCase()));
                command.setAlgorithm(Algorithm.valueOf(algorithm));
                command.setCorrect(true);
                commands.add(command);
            }

        }
        return commands;
    }

    boolean checkKeysValidity(String[] commandLineParts) {
        return Arrays.stream(commandLineParts)
                .filter(s -> s.startsWith("-"))
                .map(s -> s.substring(1))
                .allMatch(s -> Arrays.stream(Options.values()).anyMatch(v -> v.name().equals(s.toUpperCase())));
    }

    /**
     * Валидация команд
     *
     * @param commandLineParts массив комманд
     * @return true если все комманды корректные, иначе false
     */
    private boolean isValid(String[] commandLineParts) {
        if (commandLineParts.length < 6) {
            return false;
        }

        boolean isActionArgValid = ACTION_COMMAND.equalsIgnoreCase(commandLineParts[ACTION_COMMAND_INDEX]);

        boolean isCurrencyCodeValid = isCurrencyCodesValid(commandLineParts[CURRENCY_CODE_INDEX]);

        boolean isPeriodValid = Arrays.stream(ForecastPeriod.values())
                .map(Enum::name)
                .anyMatch(p -> p.equalsIgnoreCase(commandLineParts[FORECAST_PERIOD_INDEX]))
                || commandLineParts[FORECAST_PERIOD_INDEX].matches(DATE_PATTERN);
        boolean isKeysValid = checkKeysValidity(commandLineParts);

        boolean isAlgoArgValid = Arrays.stream(Algorithm.values())
                .map(Enum::name)
                .anyMatch(p -> p.equalsIgnoreCase(commandLineParts[ALGORITHM_INDEX]));

        return isActionArgValid && isPeriodValid && isCurrencyCodeValid && isKeysValid && isAlgoArgValid;
    }

    public boolean isCurrencyCodesValid(String commandLinePart) {
        return Arrays.stream(commandLinePart.split(","))
                .allMatch(s -> Arrays.stream(CurrencyCode.values())
                        .map(Enum::name)
                        .anyMatch(c -> c.equalsIgnoreCase(s)));
    }
}
