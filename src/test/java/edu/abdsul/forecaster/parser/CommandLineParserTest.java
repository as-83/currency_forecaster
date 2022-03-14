package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Algorithm;
import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Output;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandLineParserTest {

    public static final String CORRECT_COMMAND_LINE = "rate USD,TRY -date 21.03.2022 -alg mystic -output graph";
    public static  ExtCommandLineParser commandLineParser;

    @BeforeAll
    public static void init() {
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
                .allMatch(c -> c.getOutput() == Output.GRAPH)
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
}
