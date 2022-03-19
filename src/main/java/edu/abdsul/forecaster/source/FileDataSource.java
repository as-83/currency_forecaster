package edu.abdsul.forecaster.source;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс FileDataSource считывает исторические данные курса
 * валюты из файла формата CSV. Разделитель - ';'
 */
public class FileDataSource implements DataSource {

    private static final String DATASOURCE_PATH = "data/";
    private static final String FILE_NAME_SUFFIX = "_F01_02_2005_T05_03_2022.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE_ROW_NUMBER = 1;
    private static final int RATE_ROW_NUMBER = 2;
    private static final String CELL_SEPARATOR = ";";
    private static final Logger logger = LoggerFactory.getLogger(FileDataSource.class);

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

        InputStream is = getFileAsIOStream(currencyCode);
        Rate rateFromFile = new Rate(currencyCode);

        if (is == null) {
            return rateFromFile;
        }

        LocalDate date = LocalDate.now();

        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            int rowCount = 0;
            String line;
            while ((line = br.readLine()) != null && rowCount++ <= count) {
                if (rowCount == 1) {
                    continue;
                }
                String[] rowSells = line.replaceAll("\"", "").split(CELL_SEPARATOR);

                if (rateFromFile.getNominal() == 0) {
                    rateFromFile.setNominal(Integer.parseInt(rowSells[0]));
                }

                BigDecimal value = readValue(rowSells);
                date = readDate(rowSells);
                if (rateFromFile.getRates().size() == 0) {
                    rateFromFile.setFinishDate(date);
                }
                rateFromFile.addRate(date, value);
            }
            rateFromFile.setStartDate(date);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return rateFromFile;
    }

    private InputStream getFileAsIOStream(final CurrencyCode currencyCode)
    {
        String fileName = DATASOURCE_PATH + currencyCode.name() + FILE_NAME_SUFFIX;
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            logger.debug(fileName + " is not found");
        }
        return ioStream;
    }

    private LocalDate readDate(String[] rowSells) {
        String dateValue = rowSells[DATE_ROW_NUMBER];
        return  LocalDate.parse(dateValue, DATE_FORMATTER);
    }

    private BigDecimal readValue(String[] rowSells) {
        String val = rowSells[RATE_ROW_NUMBER].replace(",", ".");
        return  new BigDecimal(val);
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
