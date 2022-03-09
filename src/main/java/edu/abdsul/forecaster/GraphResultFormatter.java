package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.formater.ResultFormatter;

import java.util.List;

public class GraphResultFormatter implements ResultFormatter {
    @Override
    public String format(List<Rate> rates) {
        return "./src/main/resources/img/img1.jpg";
    }
}
