package edu.abdsul.forecaster.formater;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.domain.RateBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class GraphResultFormatterTest {

    private static List<Rate> rates = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Rate rate = new RateBuilder().setCurrencyCode(CurrencyCode.TRY)
                .setNominal(10)
                .setStartDate(LocalDate.now().plusDays(1))
                .setFinishDate(LocalDate.now().plusDays(7)).build();
        rate.addRate(LocalDate.now().plusDays(1), new BigDecimal("55.55"));
        rates.add(rate);
    }

    @Test
    public void whenCorrectRateThenCreatesFile() {
        ResultFormatter formatter = new GraphResultFormatter();

        String result = formatter.format(rates);
        File file = new File(result);
        assertThat(file.exists());
    }

}
