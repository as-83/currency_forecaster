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

public class FileDataSource implements DataSource {
    public static final String PATH = "D:/sul/java/currency_forecaster/data";
    private static final String SUFFIX = "_F01_02_2002_T01_02_2022.csv";
    public static final int DATE_ROW_NUMBER = 0;
    public static final int RATE_ROW_NUMBER = 1;


    @Override
    public List<Rate> getRates(String currencyCode) throws IOException {
        List<Rate> rates = new ArrayList<>();
        Path path = Paths.get(PATH + "/" + currencyCode + SUFFIX);

        Scanner scanner = new Scanner(path);
        scanner.nextLine();
        while(scanner.hasNext()) {
            String[] rowSells = scanner.nextLine().split(";");
            double value = Double.parseDouble(rowSells[RATE_ROW_NUMBER].replace(",", "."));
            String dateValue = rowSells[DATE_ROW_NUMBER];
            LocalDate date = LocalDate.parse(dateValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            rates.add(new Rate(date, value));

        }
        return rates;

    }
}
