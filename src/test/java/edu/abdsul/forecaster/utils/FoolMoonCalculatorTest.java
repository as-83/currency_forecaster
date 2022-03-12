package edu.abdsul.forecaster.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class FoolMoonCalculatorTest {

    @Test
    void foolMoonInMarch2022() {
        LocalDate foolMoonDate = FoolMoonCalculator.getDate(LocalDate.of(2022, 3, 12));
        assertThat(foolMoonDate).isEqualTo(LocalDate.of(2022, 3, 18));
    }

    @Test
    void foolMoonInMay2009() {
        LocalDate foolMoonDate = FoolMoonCalculator.getDate(LocalDate.of(2009, 5, 1));
        assertThat(foolMoonDate).isEqualTo(LocalDate.of(2009, 5, 9));
    }

    @Test
    void foolMoonInAugust2010() {
        LocalDate foolMoonDate = FoolMoonCalculator.getDate(LocalDate.of(2014, 8, 1));
        assertThat(foolMoonDate).isEqualTo(LocalDate.of(2014, 8, 11));
    }
}
