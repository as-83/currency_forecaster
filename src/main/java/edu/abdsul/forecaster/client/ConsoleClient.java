package edu.abdsul.forecaster.client;

import edu.abdsul.forecaster.Controller;

import java.util.Scanner;

/**
 * Класс ConsoleClient осуществляет взаимодействие с пользователем:
 * получает от пользователя комманду выполнить прогноз курса
 * выбранной из списка валюты  и выводит результат работы в
 * консоль в отформатированном виде
 */
public class ConsoleClient {

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
