package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс ExtCommandLineParser выполняет
 * парсинг команд из строки, содержащей
 * код валюты, срок прогноза и вариант вывода прогноза
 */
public class ExtCommandLineParser implements Parser {


    private static final String ACTION_COMMAND = "RATE";
    private static final String DATE_PATTERN = "^\\d{2}.\\d{2}.\\d{4}$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private List<CurrencyCode> currencyCodes;
    private String period;
    private String forecastStartDate;
    private String algorithm;
    private String output;
    private boolean allPartsValid;

    /**
     * Парсинг команд из строки
     *
     * @param commandLine строка с командами вида  "rate USD,TRY -period week -alg moon -output graph"
     * @return класс Command с полями: код валюты и срок прогноза в днях
     */
    @Override
    public List<Command> parse(String commandLine) {
        List<Command> commands = new ArrayList<>();
        List<String> commandLineParts = Arrays.stream(commandLine.trim().toUpperCase().split(" "))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (!commandLineParts.get(0).equals(ACTION_COMMAND)) {
            return commands;
        }

        boolean correctOptions = commandLineParts.stream().filter(s ->s.startsWith("-"))
                .map(s -> s.substring(1))
                .allMatch(s -> Arrays.stream(Options.values()).anyMatch(o -> s.equalsIgnoreCase(o.name())));


        boolean correctCurrency = Arrays.stream(commandLineParts.get(1).split(","))
                .allMatch(s -> Arrays.stream(CurrencyCode.values()).anyMatch(c -> s.equalsIgnoreCase(c.name())))
                && commandLineParts.get(1).split(",").length < 6;

        if (!correctOptions || !correctCurrency) {
            return commands;
        }
        this.currencyCodes = Arrays.stream(commandLineParts.get(1).split(","))
                .distinct()
                .map(CurrencyCode::valueOf)
                .collect(Collectors.toList());


        for (int i = 2; i < commandLineParts.size(); i++) {
            String option = commandLineParts.get(i);
            if (!option.startsWith("-") || i + 1 < commandLineParts.size()) {
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


        return initCommands();
    }

    private List<Command> initCommands() {
        validateCommands();
        List<Command> commands = new ArrayList<>();//TODO checking commands
        if (!allPartsValid) {
           return commands;
        }
        for (CurrencyCode currencyCode : currencyCodes) {
            Command command = new Command();
            command.setCurrencyCode(currencyCode);
            command.setAlgorithm(Algorithm.valueOf(algorithm));
            command.setForecastPeriod(ForecastPeriod.valueOf(period));
            command.setForecastStartDate(LocalDate.parse(forecastStartDate, DATE_FORMATTER));
            command.setOutput(Output.valueOf(output));
        }

        return commands;
    }

    private void validateCommands() {
        boolean validAlgo = Arrays.stream(Algorithm.values()).anyMatch(a -> a.name().equals(algorithm));
        if (period == null) {
            period = ForecastPeriod.TOMORROW.name();
        }
        boolean validPeriod = Arrays.stream(ForecastPeriod.values()).anyMatch(f -> f.name().equals(period));
        if (forecastStartDate == null) {//TODO
        }
        boolean validDate = Arrays.stream(ForecastPeriod.values()).anyMatch(f -> f.name().equals(period));
        boolean validOutput = Arrays.stream(ForecastPeriod.values()).anyMatch(f -> f.name().equals(period));

    }


}
