package edu.abdsul.forecaster;

import edu.abdsul.forecaster.source.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerTest {
    private static ForecasterController forecasterController;

    @Mock
    private DataSource mockDataSource;

    @BeforeEach
    void init() {
        forecasterController = new ForecasterController();
    }

    @Test
    void whenRateAndCodeThenForecastWithDefaultParameters() {
        String rate = forecasterController.getForecast("rate USD");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(1));
        assertThat(rate).hasLineCount(2)
                .containsIgnoringCase("USD")
                .containsIgnoringCase(date);

    }

    @Test
    void whenPeriodWeekThenEightLines() {
        String rate = forecasterController.getForecast("rate USD -period week");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(1));
        assertThat(rate).hasLineCount(8)
                .containsIgnoringCase("USD")
                .containsIgnoringCase(date);
    }

    @Test
    void whenDateTomorrowThenTwoLinesAndYesterdayDate() {
        String rate = forecasterController.getForecast("rate USD -date tomorrow");
        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now().plusDays(1));
        assertThat(rate).hasLineCount(2)
                .containsIgnoringCase("USD")
                .containsIgnoringCase(date);
    }

    @Test
    void whenDateThenTwoLinesAndDate() {
        String rate = forecasterController.getForecast("rate USD -date 27.03.2022");
        assertThat(rate).hasLineCount(2)
                .containsIgnoringCase("USD")
                .contains("27.03.2022");
    }

    @Test
    void whenOutputGraphThenOneLine() {
        String rate = forecasterController.getForecast("rate USD -period week -output graph");
        assertThat(rate).hasLineCount(1)
                .doesNotContain ("USD");
    }


}
