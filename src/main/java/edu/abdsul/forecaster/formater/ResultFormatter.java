package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.Rate;

public interface ResultFormatter {
    String format(Rate rate);
}
