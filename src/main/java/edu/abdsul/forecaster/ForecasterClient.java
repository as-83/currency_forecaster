package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.parser.CommandLineParser;
import edu.abdsul.forecaster.parser.Parser;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Класс ForecasterClient осуществляет взаимодействие с пользователем:
 * получает от пользователя комманду выполнить прогноз курса
 * выбранной из списка валюты  и выводит результат работы в
 * консоль в отформатированном виде
 */
public class ForecasterClient {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String EXIT_MESSAGE = "Работа программы завершена";
    private static final String EXIT_COMMAND = "exit";
    private static final String MENU_TEXT = "\nВведите команду. Примеры:\n" +
                        "Получить прогноз курса валюты на завтра - " + "rate TRY tomorrow\n" +
                        "Получить прогноз курса валюты на 7 дней - " + "rate USD week\n" +
                        "USD - доллар США,  TRY - турецкая лира, EUR -  Евро\n" +
                        "Выйти из программы - exit";

    public static void main(String[] args) {

        Controller controller = new Controller();

        while (true) {
            System.out.println(MENU_TEXT);
            String commandLine = scanner.nextLine();

            if (EXIT_COMMAND.equalsIgnoreCase(commandLine.trim())) {
                System.out.println(EXIT_MESSAGE);
                break;
            }

            String forecast = controller.getForecast(commandLine);

            System.out.println(forecast);

        }

    }

}
