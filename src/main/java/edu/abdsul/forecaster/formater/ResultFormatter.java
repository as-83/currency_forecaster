package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.Rate;

import java.util.List;

public interface ResultFormatter {
    String format(List<Rate> rate);
}
