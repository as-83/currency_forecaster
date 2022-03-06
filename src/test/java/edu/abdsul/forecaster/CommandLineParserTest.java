package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.parser.CommandLineParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineParserTest {
    @Test
    public void whenValidArgsThenCommandWithAttributes() {
        String commandLine = "rate TRY week";
        Command command = new CommandLineParser().parse(commandLine);
        assertThat(command.isCorrect()).isTrue();
        assertThat(command.getCurrencyCode().name()).isEqualTo("TRY");
        assertThat(command.getForecastPeriod()).isEqualTo(7);
    }

    @Test
    public void whenInvalidArgsThenCommandIsCorrectIsFalse() {
        String commandLine = "rate asd week";
        Command command = new CommandLineParser().parse(commandLine);
        assertThat(command.isCorrect()).isFalse();
    }

    @Test
    public void whenLowerCaseCurrencyCode() {
        String commandLine = "rate usd week";
        Command command = new CommandLineParser().parse(commandLine);
        assertThat(command.getCurrencyCode().name()).isEqualTo("USD");
    }
}
