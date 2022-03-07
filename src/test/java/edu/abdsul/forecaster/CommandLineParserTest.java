package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.parser.CommandLineParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLineParserTest {
    @Test
    public void whenValidArgsThenCommandWithAttributes() {
        String commandLine = "rate TRY week";
        List<Command> commands = new CommandLineParser().parse(commandLine);
        assertThat(commands.get(0).isCorrect()).isTrue();
        assertThat(commands.get(0).getCurrencyCode().name()).isEqualTo("TRY");
        assertThat(commands.get(0).getForecastPeriod()).isEqualTo(7);
    }

    @Test
    public void whenInvalidArgsThenCommandIsCorrectIsFalse() {
        String commandLine = "rate asd week";
        List<Command> commands = new CommandLineParser().parse(commandLine);
        assertThat(commands.get(0).isCorrect()).isFalse();
    }

    @Test
    public void whenLowerCaseCurrencyCode() {
        String commandLine = "rate usd week";
        List<Command> commands = new CommandLineParser().parse(commandLine);
        assertThat(commands.get(0).getCurrencyCode().name()).isEqualTo("USD");
    }
}
