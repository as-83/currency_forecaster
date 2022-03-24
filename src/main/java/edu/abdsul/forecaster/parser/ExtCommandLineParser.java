package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс ExtCommandLineParser выполняет
 * парсинг команд из строки, содержащей
 * код валюты, срок прогноза и форму вывода прогноза
 */
public class ExtCommandLineParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(ExtCommandLineParser.class);
    private static final String ACTION_COMMAND = "RATE";
    private static final String DATE_PATTERN = "^\\d{2}.\\d{2}.\\d{4}$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final int CURRENCY_MAX_COUNT = 5;
    private List<CurrencyCode> currencyCodes;
    private String period;
    private String forecastStartDate;
    private String algorithm;
    private String output;
    private boolean allPartsValid;

    public ExtCommandLineParser() {
        this.period = ForecastPeriod.DATE.name();
        this.forecastStartDate = DATE_FORMATTER.format(LocalDate.now().plusDays(1));
        this.algorithm = Algorithm.LINEAR_REGRESSION.name();
        this.output = OutputType.LIST.name();
        this.allPartsValid = true;
    }

    /**
     * Парсинг команд из строки
     *
     * @param commandLine строка с командами вида  "rate USD,TRY -period week -alg moon -output graph"
     * @return список объетов класса Command с полями: код валюты, период прогноза,
     * алгоритм прогнозирования и способ представления результата
     */
    @Override
    public List<Command> parse(String commandLine) {

        List<String> commandLineParts = split(commandLine);

        if (!isValid(commandLineParts)) {
            logger.debug("Invalid currency code ore option");
            return Collections.emptyList();
        }

        extractCommands(commandLineParts);

        validateCommands();

        return initCommands();
    }

    //Разбиение строки на слова
    private List<String> split(String commandLine) {
        return Arrays.stream(commandLine.trim().toUpperCase().split(" "))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    //Распределение команд по типу
    private void extractCommands(List<String> commandLineParts) {
        this.currencyCodes = Arrays.stream(commandLineParts.get(1).split(","))
                .distinct()
                .map(CurrencyCode::valueOf)
                .collect(Collectors.toList());

        for (int i = 2; i < commandLineParts.size(); i++) {
            String option = commandLineParts.get(i);
            if (!option.startsWith("-") || i + 1 >= commandLineParts.size()) {
                allPartsValid = false;
                break;
            }

            Options opt = Options.valueOf(option.substring(1));
            switch (opt) {
                case DATE:
                    forecastStartDate = commandLineParts.get(++i);
                    break;
                case PERIOD:
                    period = commandLineParts.get(++i);
                    break;
                case ALG:
                    algorithm = commandLineParts.get(++i);
                    break;
                case OUTPUT:
                    output = commandLineParts.get(++i);
                    break;
            }
        }
    }

    //Проверка валидности опций и кода валюты
    private boolean isValid(List<String> commandLineParts) {
        boolean hasAction = commandLineParts.get(0).equals(ACTION_COMMAND);

        boolean correctOptions = commandLineParts.stream().filter(s -> s.startsWith("-"))
                .map(s -> s.substring(1))
                .allMatch(s -> Arrays.stream(Options.values()).anyMatch(o -> s.equalsIgnoreCase(o.name())));

        boolean correctCurrency = commandLineParts.size() > 1 && Arrays.stream(commandLineParts.get(1).split(","))
                .allMatch(s -> Arrays.stream(CurrencyCode.values()).anyMatch(c -> s.equalsIgnoreCase(c.name())))
                && commandLineParts.get(1).split(",").length <= CURRENCY_MAX_COUNT;

        return hasAction && correctOptions && correctCurrency;
    }

    private List<Command> initCommands() {
        List<Command> commands = new ArrayList<>();
        if (!allPartsValid) {
            return commands;
        }
        for (CurrencyCode currencyCode : currencyCodes) {
            Command command = new Command();
            command.setCurrencyCode(currencyCode);
            command.setAlgorithm(Algorithm.valueOf(algorithm));
            command.setForecastPeriod(ForecastPeriod.valueOf(period));
            if (forecastStartDate.matches(DATE_PATTERN)) {
                command.setForecastStartDate(LocalDate.parse(forecastStartDate, DATE_FORMATTER));
            } else {
                command.setForecastStartDate(LocalDate.now().plusDays(1));
            }

            command.setOutput(OutputType.valueOf(output));
            command.setCorrect(true);
            commands.add(command);
        }
        logger.debug("CommandLine is valid");
        return commands;
    }

    private void validateCommands() {
        boolean validAlgo = Arrays.stream(Algorithm.values()).anyMatch(a -> a.name().equals(algorithm));
        boolean validPeriod = Arrays.stream(ForecastPeriod.values()).anyMatch(f -> f.name().equals(period));
        boolean validOutput = Arrays.stream(OutputType.values()).anyMatch(o -> o.name().equals(output));

        boolean validDate = forecastStartDate.equalsIgnoreCase(ForecastPeriod.TOMORROW.name()) ||
                forecastStartDate.matches(DATE_PATTERN);

        allPartsValid = validAlgo && validDate && validPeriod && validOutput;

        if (forecastStartDate.matches(DATE_PATTERN)) {
            try {
                LocalDate date = LocalDate.parse(forecastStartDate, DATE_FORMATTER);
                if (date.isBefore(LocalDate.now())) {
                    allPartsValid = false;
                }
            } catch (Exception e) {
                logger.debug("Invalid date in commandLine");
                allPartsValid = false;
            }
        }
    }


}
