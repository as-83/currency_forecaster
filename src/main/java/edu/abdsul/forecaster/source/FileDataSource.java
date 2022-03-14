package edu.abdsul.forecaster.source;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс FileDataSource считывает исторические данные курса
 * валюты из файла формата CSV. Разделитель - ';'
 */
public class FileDataSource implements DataSource {

    private static final String DATASOURCE_PATH = "./data";
    private static final String FILE_NAME_SUFFIX = "_F01_02_2005_T05_03_2022.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE_ROW_NUMBER = 1;
    private static final int RATE_ROW_NUMBER = 2;
    private static final String CELL_SEPARATOR = ";";

    /**
     * Предоставляет список заданного количества значений значений  исторических
     * данных курса валюты по заданному коду
     *
     * @param currencyCode код валюты
     * @param count количество значений  исторических данных курса валюты
     * @return список значений  исторических данных курса валюты
     */
    @Override
    public Rate getLastNRates(CurrencyCode currencyCode, int count) {
        Rate rateHistory = new Rate(currencyCode);
        Path path = Paths.get(DATASOURCE_PATH + "/" + currencyCode.name() + FILE_NAME_SUFFIX);
        LocalDate date = LocalDate.now();
        Scanner scanner;
        try {
            scanner = new Scanner(path);
            scanner.nextLine();
            int rowCount = 0;
            while (scanner.hasNext() && rowCount++ < count) {
                String[] rowSells = scanner.nextLine().replaceAll("\"", "").split(CELL_SEPARATOR);

                if (rateHistory.getNominal() == 0) {
                    rateHistory.setNominal(Integer.parseInt(rowSells[0]));
                }
                String val = rowSells[RATE_ROW_NUMBER].replace(",", ".");
                BigDecimal value = new BigDecimal(val);
                String dateValue = rowSells[DATE_ROW_NUMBER];
                date = LocalDate.parse(dateValue, DATE_FORMATTER);
                if (rateHistory.getRates().size() == 0) {
                    rateHistory.setFinishDate(date);
                }
                rateHistory.addRate(date, value);
            }
            rateHistory.setStartDate(date);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rateHistory;
    }

    /**
     * Предоставляет список всех значений  исторических данных курса валюты
     * по заданному коду
     *
     * @param currencyCode код валюты
     * @return список значений  исторических данных курса валюты
     */
    @Override
    public Rate getAllRates(CurrencyCode currencyCode) {
        return getLastNRates(currencyCode, Integer.MAX_VALUE);
    }




}
