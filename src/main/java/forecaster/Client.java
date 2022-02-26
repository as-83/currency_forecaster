package forecaster;

import forecaster.domain.Command;
import forecaster.domain.Rate;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/*
    1. Чтобы работало в одном jar-нике из консоли
    2. Ориентироваться на имена команд
    3. вынести парсинг входных команд в отдельный класс
    4. Бигдецимал для денег.
    5. Может измениться период, лучше не тернарка.
    6. Код валют в енам
    7. не использовать throws
    8. assertJ
*/

/**
 * Класс Client осуществляет взаимодействие с пользователем:
 * получает от пользователя комманду выполнить прогноз курса
 * выбранной из списка валюты, передает её обработчику
 * и выводит результат работы в консоль в отформатированном виде
 */
public class Client {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String INCORRECT_COMMAND_MESSAGE = "\nНеверный формат комманды! Попробуйте еще раз!\n";
    private static final String EMPTY_RESULT_MESSAGE = "Результат отсутствует!";
    public static final String EXIT_MESSAGE = "Работа программы завершена";
    public static final String EXIT_COMMAND = "exit";
    public static final String MENU_TEXT = "\nВведите команду. Примеры:\n" +
                        "Получить прогноз курса валюты на завтра - " + "rate TRY tomorrow\n" +
                        "Получить прогноз курса валюты на 7 дней - " + "rate USD week\n" +
                        "USD - доллар США,  TRY - турецкая лира, EUR -  Евро\n" +
                        "Выйти из программы - exit";

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

            String currencyCode = command.getCurrencyCode().name();
            int forecastPeriod = command.getForecastPeriod();
            List<Rate> rates = forecaster.getForecast(currencyCode, forecastPeriod);

            printForecast(rates);

        }

    }


    /**
     * Форматированный вывод прогноза курса валюты в консоль
     * Пример: Вт 22.02.2022 - 75,45
     *
     * @param rates Прогноз курса валюты
     */
    private static void printForecast(List<Rate> rates) {
        if (rates.isEmpty()) {
            System.out.println(EMPTY_RESULT_MESSAGE);
            return;
        }
        DecimalFormat valueFormatter = new DecimalFormat("###.##");
        String datePattern = "E dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        for (Rate rate : rates) {
            String date = dateFormatter.format(rate.getDate());
            date = date.substring(0, 1).toUpperCase() + date.substring(1);
            String value = valueFormatter.format(rate.getValue());
            System.out.println(date + " - " + value);
        }
    }

}
