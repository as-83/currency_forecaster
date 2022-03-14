package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.*;
import org.jetbrains.annotations.Nullable;

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

    private static final String ACTION_COMMAND = "RATE";
    private static final String DATE_PATTERN = "^\\d{2}.\\d{2}.\\d{4}$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
        this.output = Output.LIST.name();
        this.allPartsValid = true;
    }

    /**
     * Парсинг команд из строки
     *
     * @param commandLine строка с командами вида  "rate USD,TRY -period week -alg moon -output graph"
     * @return класс Command с полями: код валюты и срок прогноза в днях
     */
    @Override
    public List<Command> parse(String commandLine) {

        List<String> commandLineParts = Arrays.stream(commandLine.trim().toUpperCase().split(" "))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (!isValid(commandLineParts)) {
            return Collections.emptyList();
        }

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
        return initCommands();
    }

    private boolean isValid(List<String> commandLineParts) {
         boolean hasAction = commandLineParts.get(0).equals(ACTION_COMMAND);

        boolean correctOptions = commandLineParts.stream().filter(s ->s.startsWith("-"))
                .map(s -> s.substring(1))
                .allMatch(s -> Arrays.stream(Options.values()).anyMatch(o -> s.equalsIgnoreCase(o.name())));

        boolean correctCurrency = Arrays.stream(commandLineParts.get(1).split(","))
                .allMatch(s -> Arrays.stream(CurrencyCode.values()).anyMatch(c -> s.equalsIgnoreCase(c.name())))
                && commandLineParts.get(1).split(",").length < 6;

        return hasAction && correctOptions && correctCurrency;
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
            if (forecastStartDate.matches(DATE_PATTERN)) {
                command.setForecastStartDate(LocalDate.parse(forecastStartDate, DATE_FORMATTER));
            } else {
                command.setForecastStartDate(LocalDate.now().plusDays(1));
            }

            command.setOutput(Output.valueOf(output));
            command.setCorrect(true);
            commands.add(command);
        }

        return commands;
    }

    private void validateCommands() {
        boolean validAlgo = Arrays.stream(Algorithm.values()).anyMatch(a -> a.name().equals(algorithm));
        boolean validPeriod = Arrays.stream(ForecastPeriod.values()).anyMatch(f -> f.name().equals(period));
        boolean validOutput = Arrays.stream(Output.values()).anyMatch(o -> o.name().equals(output));

        boolean validDate = forecastStartDate.equalsIgnoreCase("TOMORROW") ||
                forecastStartDate.matches(DATE_PATTERN);
        allPartsValid  = validAlgo && validDate && validPeriod && validOutput;

        if (forecastStartDate.matches(DATE_PATTERN)) {
            try{
                LocalDate.parse(forecastStartDate, DATE_FORMATTER);
            } catch(Exception e) {
                allPartsValid = false;
            }
        }
    }


}
