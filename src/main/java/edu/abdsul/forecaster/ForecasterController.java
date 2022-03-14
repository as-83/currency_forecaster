package edu.abdsul.forecaster;

import edu.abdsul.forecaster.algorithm.*;
import edu.abdsul.forecaster.domain.Algorithm;
import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Output;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.formater.TextResultFormatter;
import edu.abdsul.forecaster.formater.GraphResultFormatter;
import edu.abdsul.forecaster.formater.ResultFormatter;
import edu.abdsul.forecaster.parser.CommandLineParser;
import edu.abdsul.forecaster.parser.ExtCommandLineParser;
import edu.abdsul.forecaster.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ForecasterController выполняет прогноз курса валюты
 * на заданный период в днях, по заданному алгоритму
 * прогнозирования  основываясь на исторических данных курса валюты
 * Алгоритм по умолчанию: среднее значение последних семи дней
 * Источник данных по умолчанию - csv-файл
 */
public class ForecasterController {
    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private static final String INFO_MESSAGE = "Пример команд:\n\n rate USD -date tomorrow -alg MYSTIC -output list\n\n" +
            "Обязательно наличие первых двух команд: rate и код валюты - USD, TRY, EUR, AMD или BGN.\n" +
            "Опционольно -date - дата прогноза с возможными значениями  tomorrow или конкретной датой в формате 02.22.2022\n" +
            "Опционольно -period - период прогноза со значениями  week или month\n" +
            "Опционольно -alg - алгоритм прогнозирования со значениями  ACTUAL, MYSTIC, LINEAR_REGRESSION\n" +
            "Опционольно -output форма представления прогноза со значениями  list или graph\n" +
            "";
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

        if (commandLine.equals("/start")) {
            return INFO_MESSAGE;
        }

        parser = new ExtCommandLineParser();
        List<Command> commands = parser.parse(commandLine);
        if (commands.isEmpty()) {
            return INCORRECT_COMMAND_MESSAGE + INFO_MESSAGE;
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
        return new TextResultFormatter();
    }

    private Forecaster getNeededForecaster(Algorithm algorithm) {
        Forecaster forecaster;
        switch (algorithm) {
            case ACTUAL: forecaster = new ActualAlgorithmForecaster(); break;
            case MYSTIC: forecaster = new MysticAlgorithmForecaster(); break;
            case LINEAR_REGRESSION: forecaster = new LinearRegressionForecaster(); break;
            default: forecaster = new LastSevenAvgForecaster();
        }
        return forecaster;
    }
}
