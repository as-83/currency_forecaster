package edu.abdsul.forecaster.parser;

import edu.abdsul.forecaster.domain.Command;

import java.util.List;

public interface Parser {
    List<Command> parse(String commandLine);
}
