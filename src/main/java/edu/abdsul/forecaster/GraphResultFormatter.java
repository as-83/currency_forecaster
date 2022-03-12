package edu.abdsul.forecaster;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.formater.ResultFormatter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
//rate USD,TRY -period week -alg LINEAR_REGRESSION -output list
public class GraphResultFormatter implements ResultFormatter {

    public void drawPicture() throws IOException, PythonExecutionException {
        List<Double> x = NumpyUtils.linspace(-Math.PI, Math.PI, 256);
        List<Double> C = x.stream().map(Math::cos).collect(Collectors.toList());
        List<Double> S = x.stream().map(Math::sin).collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, C);
        plt.plot().add(x, S);
        //plt.show();
        plt.savefig("./src/main/resources/img/img1.jpg").dpi(200);
        plt.executeSilently();
    }


    @Override
    public String format(List<Rate> rates) {
        Plot plt = Plot.create();
        plt.title("Currency forecast");

        List<List<Double>> allRates = new ArrayList<>();
        /*List<Double> dates = rates.get(0).getRates().entrySet().stream().map(r -> r.getKey().getMonthValue() + r.getKey().getDayOfMonth() * 0.033)
                .collect(Collectors.toList());*/
        List<Double> dates = Stream.iterate(1.0, i -> i + 1).limit(rates.get(0).getRates().size()).collect(Collectors.toList());
        for (Rate rate : rates) {
            List<Double> values = rate.getRates().values().stream()
                    .map(BigDecimal::doubleValue)
                    .collect(Collectors.toList());
            plt.plot().add(dates, values).label(rate.getCurrencyCode().name());
            plt.legend().loc("upper left");
        }
        plt.savefig("./src/main/resources/img/img1.png").dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
            return "Error! Try again";
        }

        return "./src/main/resources/img/img1.png";
    }
}
