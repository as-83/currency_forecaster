package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс ForecasterClient осуществляет взаимодействие с пользователем:
 * получает от пользователя комманду выполнить прогноз курса
 * выбранной из списка валюты  и выводит результат работы в
 * консоль в отформатированном виде
 */
public class ForecasterClient {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private static final String EMPTY_RESULT_MESSAGE = "Результат отсутствует!";
    private static final String EXIT_MESSAGE = "Работа программы завершена";
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "\nВведите команду. Примеры:\n" +
                        "Получить прогноз курса валюты на завтра - " + "rate TRY tomorrow\n" +
                        "Получить прогноз курса валюты на 7 дней - " + "rate USD week\n" +
                        "USD - доллар США,  TRY - турецкая лира, EUR -  Евро\n" +
                        "Выйти из программы - exit";
    private static final String DATE_PATTERN = "E dd.MM.yyyy";

    public static void main(String[] args) {

        Forecaster forecaster = new Forecaster();

        while (true) {
            System.out.println(MENU_TEXT);
            String commandLine = scanner.nextLine();

            if (EXIT_COMMAND.equalsIgnoreCase(commandLine.trim())) {
                System.out.println(EXIT_MESSAGE);
                break;
            }

            Command command = CommandLineParser.parse(commandLine);

            if (!command.isCorrect()) {
                System.out.println(INCORRECT_COMMAND_MESSAGE);
                continue;
            }

            CurrencyCode currencyCode = command.getCurrencyCode();
            int forecastPeriod = command.getForecastPeriod();
            Rate rates = forecaster.getForecast(currencyCode, forecastPeriod);

            printForecast(rates);

        }

    }

    /**
     * Форматированный вывод прогноза курса валюты в консоль
     * Пример: Вт 22.02.2022 - 75,45
     *
     * @param rates Прогноз курса валюты
     */
    private static void printForecast(Rate rates) {
        if (rates.getRates().isEmpty()) {
            System.out.println(EMPTY_RESULT_MESSAGE);
            return;
        }
        DecimalFormat valueFormatter = new DecimalFormat("###.##");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        rates.getRates().forEach((key, value1) -> {
            String date = dateFormatter.format(key);
            date = date.substring(0, 1).toUpperCase() + date.substring(1);
            String value = valueFormatter.format(value1);
            System.out.println(date + " - " + value);
        });
    }

}
