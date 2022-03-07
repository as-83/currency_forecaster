package edu.abdsul.forecaster.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineParserTest {

    @Test
    void isCurrencyCodesValidTrueThenThreeValidCodes() {
        CommandLineParser commandLineParser = new CommandLineParser();
        boolean actual = commandLineParser.isCurrencyCodesValid("TRY,USD,EUR");
        assertTrue(actual);
    }

    @Test
    void isCurrencyCodesValidFalseThenOneOfThreeNotValid() {
        CommandLineParser commandLineParser = new CommandLineParser();
        boolean actual = commandLineParser.isCurrencyCodesValid("TRY,USSR,EUR");
        assertFalse(actual);
    }

    @Test
    void isCurrencyCodesValidTrueThenOneValid() {
        CommandLineParser commandLineParser = new CommandLineParser();
        boolean actual = commandLineParser.isCurrencyCodesValid("TRY");
        assertTrue(actual);
    }
}