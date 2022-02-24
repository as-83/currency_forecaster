package forecaster;

import forecaster.domain.Rate;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Класс Client осуществляет взаимодействие с пользователем:
 *
 * получает от пользователя комманду выполнить прогноз курса
 *
 * выбранной из списка валюты, передает её обработчику
 *
 * и выводит результат работы в консоль в отформатированном виде
 * 1. Чтобы работало в одном jar-нике из консоли
 * 2. Ориентироваться на имена команд
 * 3. вынести парсинг входных команд в отдельный класс
 *                  4. Бигдецимал для денег.
 * 5. Может измениться период, лучше не тернарка.
 * 6. Код валют в енам
 * 7. не использовать throws
 *                  8. assertJ
 */
public class Client {

    private static final int CURRENCY_CODE_INDEX = 1;
    private static final int FORECAST_DURATION_INDEX = 2;
    public static final String MENU_TEXT = "\nВведите команду. Примеры:\n" +
            "Получить прогноз курса валюты на завтра - " + "rate TRY tomorrow\n" +
            "Получить прогноз курса валюты на 7 дней - " + "rate USD week\n" +
            "USD - доллар США,  TRY - турецкая лира, EUR -  Евро\n" +
            "Выйти из программы - exit";
    private static final String IOEXCEPTION_MESSAGE = "\nЧто-то пошло не так... Попробуйте еще раз.\n";
    private static final String INCORRECT_COMMAND_MESSAGE = "\nНе верный формат комманды! Попробуйте еще раз!\n";

    private static final Scanner scanner = new Scanner(System.in);
    public static final String EXIT_MESSAGE = "The program closed";
    public static final String TOMORROW = "tomorrow";

    public static void main(String[] args) {
        Forecaster forecaster = new Forecaster();

        while (true) {
            System.out.println(MENU_TEXT);
            String[] commands = scanner.nextLine().split(" ");

            if (commands.length != 0 && commands[0].equalsIgnoreCase("exit")) {
                System.out.println(EXIT_MESSAGE);
                break;
            }

            if (commands.length == 3) {
                String forecastPeriod = commands[FORECAST_DURATION_INDEX];
                String currencyCode = commands[CURRENCY_CODE_INDEX];
                int forecastDuration = TOMORROW.equalsIgnoreCase(forecastPeriod) ? 1 : 7;//TODO
                List<Rate> rates = null;
                try {
                    rates = forecaster.getForecast(currencyCode, forecastDuration);
                    printForecast(rates);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(IOEXCEPTION_MESSAGE);
                }
            } else {
                System.out.println(INCORRECT_COMMAND_MESSAGE);
            }

        }

    }


    /**
     * Форматированный вывод прогноза курса валюты в консоль
     * Пример: Вт 22.02.2022 - 75,45
     *
     * @param rates Прогноз курса валюты
     */
    private static void printForecast(List<Rate> rates) {
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
