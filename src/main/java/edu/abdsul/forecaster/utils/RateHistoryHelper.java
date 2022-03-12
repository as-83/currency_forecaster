package edu.abdsul.forecaster.utils;

import edu.abdsul.forecaster.domain.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RateHistoryHelper {
    public static double[] format(Rate rateHistory) {
        LinkedHashMap<LocalDate, BigDecimal> rates = rateHistory.getRates();
        double[] lastMonthValues = new double[30];

        LocalDate lastDate = rates.keySet().stream().findFirst().orElse(LocalDate.now().minusDays(100));

        for (int i = 0; i < 30; i++) {
            BigDecimal rateValue = getRateFromPast(rateHistory, lastDate.minusDays(i));
            lastMonthValues[29 - i] = rateValue.doubleValue();
        }

        return lastMonthValues;//TODO last month
    }

    public static BigDecimal getRateFromPast(Rate rateHistory, LocalDate pastDate) {
        LocalDate nearestDate = pastDate;
        BigDecimal rateInPast = rateHistory.getRates().get(pastDate);
        while (rateInPast == null) {
            nearestDate = nearestDate.minusDays(1);
            rateInPast = rateHistory.getRates().get(nearestDate);
        }
        return rateInPast;
    }
}
