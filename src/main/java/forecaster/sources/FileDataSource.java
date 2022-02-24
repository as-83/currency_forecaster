package forecaster.sources;

import forecaster.domain.Rate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс FileDataSource считывает исторические данные курса
 * <p>
 * валюты из файла формата CSV. Разделитель - ';'
 */
public class FileDataSource implements DataSource {

    public static final String DATASOURCE_PATH = "./data";
    private static final String FILE_NAME_SUFFIX = "_F01_02_2002_T01_02_2022.csv";
    public static final int DATE_ROW_NUMBER = 0;
    public static final int RATE_ROW_NUMBER = 1;
    public static final String CELL_SEPARATOR = ";";

    /**
     * Получение исторических данных курса валюты
     * из файла формата CSV, разделитель - ';'
     *
     * @param currencyCode код валюты
     * @return список ежедневного курса  валюты с заданным кодом
     * @throws IOException Если произошла ошибка при чтении файла
     */
    @Override
    public List<Rate> getRates(String currencyCode) throws IOException {

        List<Rate> rates = new ArrayList<>();
        Path path = Paths.get(DATASOURCE_PATH + "/" + currencyCode + FILE_NAME_SUFFIX);

        Scanner scanner = new Scanner(path);
        scanner.nextLine();

        while (scanner.hasNext()) {
            String[] rowSells = scanner.nextLine().split(CELL_SEPARATOR);
            double value = Double.parseDouble(rowSells[RATE_ROW_NUMBER].replace(",", "."));
            String dateValue = rowSells[DATE_ROW_NUMBER];
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dateValue, dateTimeFormatter);
            rates.add(new Rate(date, value));
        }

        return rates;
    }
}
