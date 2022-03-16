package edu.abdsul.forecaster.formater;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import edu.abdsul.forecaster.domain.Rate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//rate USD,TRY -period week -alg LINEAR_REGRESSION -output list
public class GraphResultFormatter implements ResultFormatter {

    public static final String LEGEND_LOCATION = "upper left";
    public static final String ERROR_MESSAGE = "Ошибка! Попробуйте еще раз";
    public static final String FILE_LOCATION = "src/main/resources/img/";
    public static final String EXT = ".png";
    public static final String IMG_TITLE_PREFIX = "Прогноз курса на период: ";
    public static final String Y_LABEL = "курс";
    public static final String X_LABEL = "дни";

    /**
     * Отображает данные прогноза
     * на графике на изображении png-формата
     *
     * @param rates Список обьектов содержащих данные прогнозов курса валют
     * @return путь к файлу с изображением
     */
    @Override
    public String format(List<Rate> rates) {
        if (rates.size() < 1) {
            return ERROR_MESSAGE;
        }

        String imageLocation = FILE_LOCATION + UUID.randomUUID() + EXT;
        Plot plt = Plot.create();
        plt.title(createImageTitle(rates));

        List<Double> dates = Stream.iterate(1.0, i -> i + 1).limit(rates.get(0).getRates().size()).collect(Collectors.toList());
        for (Rate rate : rates) {
            List<Double> values = rate.getRates().values().stream()
                    .map(BigDecimal::doubleValue)
                    .collect(Collectors.toList());
            plt.plot().add(dates, values).label(rate.getCurrencyCode().name());
            plt.legend().loc(LEGEND_LOCATION);
        }

        plt.ylabel(Y_LABEL);
        plt.xlabel(X_LABEL);
        plt.savefig(imageLocation).dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
            return ERROR_MESSAGE;
        }
        plt.close();
        return imageLocation;
    }

    private String createImageTitle(List<Rate> rates) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String period = dateFormatter.format(rates.get(0).getStartDate()) + " - " + dateFormatter.format(rates.get(0).getFinishDate());
        return IMG_TITLE_PREFIX + period;
    }
}
