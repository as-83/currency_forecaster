package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Command;

public interface Parser {
    Command parse(String commandLine);
}
