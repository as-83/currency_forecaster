package edu.abdsul.forecaster;

import edu.abdsul.forecaster.algorithm.Forecaster;
import edu.abdsul.forecaster.algorithm.LastSevenAvgForecaster;
import edu.abdsul.forecaster.domain.Algorithm;
import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Output;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.formater.ConsoleResultFormatter;
import edu.abdsul.forecaster.formater.ResultFormatter;
import edu.abdsul.forecaster.parser.CommandLineParser;
import edu.abdsul.forecaster.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс Forecaster выполняет прогноз курса валюты
 * на заданный период в днях, по заданному алгоритму
 * прогнозирования  основываясь на исторических данных курса валюты
 * Алгоритм по умолчанию: среднее значение последних семи дней
 * Источник данных по умолчанию - csv-файл
 */
public class Controller {
    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private Forecaster forecaster;
    private Parser parser;
    private ResultFormatter resultFormatter;

    /**
     * Прогноз курса выбранной валюты, на заданный период
     * основыванный на исторических данных курса валюты
     *
     * @param commandLine строка содержащая команды
     * @return Строка с прогнозом курса валюты на заданное количество дней или расположение файла
     */
    public String getForecast(String commandLine) {
        parser = new CommandLineParser();
        List<Command> commands = parser.parse(commandLine);
        if (commands.isEmpty()) {
            return INCORRECT_COMMAND_MESSAGE;
        }

        forecaster = getNeededForecaster(commands.get(0).getAlgorithm());//TODO fabric

        List<Rate> forecasts = getForecasts(commands);

        resultFormatter = getResultFormatter(commands.get(0).getOutput());
        return resultFormatter.format(forecasts);
    }

    private List<Rate> getForecasts(List<Command> commands) {
        List<Rate> forecasts = new ArrayList<>();
        for (Command command : commands) {
            Rate forecast = forecaster.getForecast(command);
            forecasts.add(forecast);
        }
        return forecasts;
    }

    private ResultFormatter getResultFormatter(Output output) {
        if (output == Output.GRAPH) {
            return new GraphResultFormatter();
        }
        return new ConsoleResultFormatter();
    }

    private Forecaster getNeededForecaster(Algorithm algorithm) {
        return new LastSevenAvgForecaster();
    }
}
