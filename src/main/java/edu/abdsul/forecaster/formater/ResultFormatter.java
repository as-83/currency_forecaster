package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.Rate;

import java.util.List;

public interface ResultFormatter {
    String EMPTY_RESULT_MESSAGE = "По данному запросу прогноз не возможен";
    String DATE_PATTERN = "E dd.MM.yyyy";

    String format(List<Rate> rate);
}
