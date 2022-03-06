package edu.abdsul.forecaster;

import edu.abdsul.forecaster.algorithm.Forecaster;
import edu.abdsul.forecaster.algorithm.LastSevenAvgForecaster;
import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.formater.ConsoleResultFormatter;
import edu.abdsul.forecaster.formater.ResultFormatter;
import edu.abdsul.forecaster.parser.CommandLineParser;
import edu.abdsul.forecaster.parser.Parser;

/**
 * Класс Forecaster выполняет прогноз курса валюты
 * на заданный период в днях, по заданному алгоритму
 * прогнозирования  основываясь на исторических данных курса валюты
 * Алгоритм по умолчанию: среднее значение последних семи дней
 * Источник данных по умолчанию - csv-файл
 */
public class Controller {

    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private Forecaster forecaster = new LastSevenAvgForecaster();

    private Parser parser = new CommandLineParser();

    private ResultFormatter resultFormatter = new ConsoleResultFormatter();


    /**
     * Прогноз курса выбранной валюты, на заданный период
     * основыванный на исторических данных курса валюты
     *
     * @param commandLine     строка содержащая команды
     * @return Прогноз курса валюты на заданное количество дней
     */
    public String getForecast(String commandLine) {
        Command command = parser.parse(commandLine);
        if (!command.isCorrect()) {
            return INCORRECT_COMMAND_MESSAGE;
        }
        Rate forecast = forecaster.getForecast(command);
        return resultFormatter.format(forecast);
    }

    public Forecaster getForecastType() {
        return forecaster;
    }

    public void setForecastType(Forecaster forecaster) {
        this.forecaster = forecaster;
    }
}
