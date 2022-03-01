package forecaster.source;

import forecaster.domain.CurrencyCode;
import forecaster.domain.Rate;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс FileDataSource считывает исторические данные курса
 * валюты из файла формата CSV. Разделитель - ';'
 */
public class FileDataSource implements DataSource {

    private static final String DATASOURCE_PATH = "./data";
    private static final String FILE_NAME_SUFFIX = "_F01_02_2002_T01_02_2022.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE_ROW_NUMBER = 0;
    private static final int RATE_ROW_NUMBER = 1;
    private static final String CELL_SEPARATOR = ";";

    /**
     * Предоставляет список значений  исторических данных курса валюты
     * по заданному коду
     *
     * @param currencyCode код валюты
     * @return список значений  исторических данных курса валюты
     */
    @Override
    public List<Rate> getRates(CurrencyCode currencyCode) {

        List<Rate> rates = new ArrayList<>();
        Path path = Paths.get(DATASOURCE_PATH + "/" + currencyCode.name() + FILE_NAME_SUFFIX);

        Scanner scanner;
        try {
            scanner = new Scanner(path);
            scanner.nextLine();

            while (scanner.hasNext()) {
                String[] rowSells = scanner.nextLine().split(CELL_SEPARATOR);
                double val = Double.parseDouble(rowSells[RATE_ROW_NUMBER].replace(",", "."));
                BigDecimal value = BigDecimal.valueOf (val);
                String dateValue = rowSells[DATE_ROW_NUMBER];
                LocalDate date = LocalDate.parse(dateValue, DATE_FORMATTER);
                rates.add(new Rate(date, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rates;
    }
}
