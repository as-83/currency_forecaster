package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Algorithm;
import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.OutputType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

class CommandLineParserTest {

    public static final String CORRECT_COMMAND_LINE = "rate USD,TRY -date 21.03.2022 -alg mystic -output graph";
    public  ExtCommandLineParser commandLineParser;

    @BeforeEach
    public void init() {
        commandLineParser = new ExtCommandLineParser();
    }


    @Test
    void parseWhenCommandLineIsValid() {
        List<Command> commands = commandLineParser.parse(CORRECT_COMMAND_LINE);
        assertThat(commands.isEmpty()).isFalse();
        assertThat(commands).hasSize(2)
                .allMatch(Command::isCorrect)
                .allMatch(c -> c.getForecastPeriod().getDayCount() == 1)
                .allMatch(c -> c.getForecastStartDate().isEqual(LocalDate.of(2022, 3, 21)))
                .allMatch(c -> c.getOutput() == OutputType.GRAPH)
                .allMatch(c -> c.getAlgorithm() == Algorithm.MYSTIC);
    }

    @Test
    void whenOneWordInCommandLineInvalidThenCommandsListIsEmpty() {
        String[] commandParts = CORRECT_COMMAND_LINE.split(" ");

        for (int i = 0; i < commandParts.length; i++) {
            commandParts[i] = commandParts[i].substring(1);
            String invalidCommandLine = Arrays.stream(commandParts).reduce("", (s1, s2) -> s1 + s2);
            List<Command> commands = commandLineParser.parse(invalidCommandLine);
            assertThat(commands.isEmpty()).isTrue();
        }

    }

    @Test
    void whenOnlyRateAndCurrencyCodeThenDefaultSettingsForOver() {
        List<Command> commands = commandLineParser.parse("rate usd");
        assertThat(commands.isEmpty()).isFalse();
        assertThat(commands).hasSize(1)
                .allMatch(Command::isCorrect)
                .allMatch(c -> c.getForecastPeriod().getDayCount() == 1)
                .allMatch(c -> c.getForecastStartDate().isEqual(LocalDate.now().plusDays(1)))
                .allMatch(c -> c.getOutput() == OutputType.LIST)
                .allMatch(c -> c.getAlgorithm() == Algorithm.LINEAR_REGRESSION);

    }

    @Test
    void whenRateAndCurrencyCodeAndAlgoThenDefaultSettingsForOver() {
        List<Command> commands = commandLineParser.parse("rate usd -alg mystic");
        assertThat(commands.isEmpty()).isFalse();
        assertThat(commands).hasSize(1)
                .allMatch(Command::isCorrect)
                .allMatch(c -> c.getForecastPeriod().getDayCount() == 1)
                .allMatch(c -> c.getForecastStartDate().isEqual(LocalDate.now().plusDays(1)))
                .allMatch(c -> c.getOutput() == OutputType.LIST)
                .allMatch(c -> c.getAlgorithm() == Algorithm.MYSTIC)
                .allMatch(c -> c.getCurrencyCode().equals(CurrencyCode.USD));

    }

    @Test
    void whenRateAndCurrencyCodeAndDateThenDefaultSettingsForOver() {
        List<Command> commands = commandLineParser.parse("rate usd -date 22.03.2022");
        assertThat(commands.isEmpty()).isFalse();
        assertThat(commands).hasSize(1)
                .allMatch(Command::isCorrect)
                .allMatch(c -> c.getForecastPeriod().getDayCount() == 1)
                .allMatch(c -> c.getForecastStartDate().isEqual(LocalDate.of(2022,3,22)))
                .allMatch(c -> c.getOutput() == OutputType.LIST)
                .allMatch(c -> c.getAlgorithm() == Algorithm.LINEAR_REGRESSION)
                .allMatch(c -> c.getCurrencyCode().equals(CurrencyCode.USD));

    }

    @Test
    void whenDateIsPastThenEmptyList() {
        List<Command> commands = commandLineParser.parse("rate usd -date 22.03.2002");
        assertThat(commands.isEmpty()).isTrue();
    }

    @Test
    void whenOnlyRateThenEmptyListOfCommands() {
        List<Command> commands = commandLineParser.parse("rate");
        assertThat(commands.isEmpty()).isTrue();
    }


}
