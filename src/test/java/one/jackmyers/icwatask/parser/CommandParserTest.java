package one.jackmyers.icwatask.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;
import one.jackmyers.icwatask.model.CardinalDirection;
import one.jackmyers.icwatask.model.Command;
import one.jackmyers.icwatask.model.Command.PlaceCommand;
import one.jackmyers.icwatask.model.Vector2;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CommandParserTest {

  static Stream<Arguments> parseSinglePlacementCases() {
    return Stream.of(
        // Direction tests
        Arguments.of("PLACE 0,0,NORTH", Vector2.ZERO, CardinalDirection.NORTH),
        Arguments.of("PLACE 0,0,EAST", Vector2.ZERO, CardinalDirection.EAST),
        Arguments.of("PLACE 0,0,SOUTH", Vector2.ZERO, CardinalDirection.SOUTH),
        Arguments.of("PLACE 0,0,WEST", Vector2.ZERO, CardinalDirection.WEST),

        // XY position tests
        Arguments.of("PLACE 1,0,NORTH", Vector2.X, CardinalDirection.NORTH),
        Arguments.of("PLACE 0,1,NORTH", Vector2.Y, CardinalDirection.NORTH),
        Arguments.of("PLACE 1,1,NORTH", Vector2.ONE, CardinalDirection.NORTH),

        // Whitespace tests
        Arguments.of("PLACE 1, 1,NORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE 1,1, NORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE 1, 1, NORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE 1,  1,  NORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("  PLACE 1,  1,  NORTH  ", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE\n1\n,\n1\n,\nNORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE\r1\r,\r1\r,\rNORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE\t1\t,\t1\t,\tNORTH", Vector2.ONE, CardinalDirection.NORTH),
        Arguments.of("PLACE\n\r\t 1,\n\r\t 1,\n\r\t NORTH", Vector2.ONE, CardinalDirection.NORTH));
  }

  static Stream<Arguments> parseSingleActionCases() {
    return Stream.of(
        Arguments.of("MOVE", Command.MOVE),
        Arguments.of("LEFT", Command.LEFT),
        Arguments.of("RIGHT", Command.RIGHT),
        Arguments.of("REPORT", Command.REPORT));
  }

  static Stream<Arguments> parseMultipleCommandCases() {
    return Stream.of(
        // Multiple commands with whitespace
        Arguments.of("MOVE MOVE", List.of(Command.MOVE, Command.MOVE)),
        Arguments.of("MOVE\nMOVE", List.of(Command.MOVE, Command.MOVE)),
        Arguments.of("MOVE\rMOVE", List.of(Command.MOVE, Command.MOVE)),
        Arguments.of("MOVE\tMOVE", List.of(Command.MOVE, Command.MOVE)),

        // Multiple place commands
        Arguments.of(
            "PLACE 0,0,NORTH PLACE 1,1,SOUTH",
            List.of(
                new PlaceCommand(Vector2.ZERO, CardinalDirection.NORTH),
                new PlaceCommand(Vector2.ONE, CardinalDirection.SOUTH))),

        // Mixed commands
        Arguments.of(
            "MOVE\nLEFT\nRIGHT\nREPORT",
            List.of(Command.MOVE, Command.LEFT, Command.RIGHT, Command.REPORT)),
        Arguments.of(
            "MOVE PLACE 0,0,NORTH MOVE",
            List.of(
                Command.MOVE,
                new PlaceCommand(Vector2.ZERO, CardinalDirection.NORTH),
                Command.MOVE)));
  }

  @ParameterizedTest
  @MethodSource("parseSinglePlacementCases")
  void when_parseSinglePlaceCommand_then_returnCommand(
      String input, Vector2 expectedPos, CardinalDirection expectedDirection) {
    // Arrange
    final var expectedLength = 1;
    final var expectedCommand = new PlaceCommand(expectedPos, expectedDirection);

    // Act
    final var commands = CommandParser.parseString(input);

    // Assert
    assertEquals(expectedLength, commands.size());
    assertEquals(expectedCommand, commands.getFirst());
  }

  @ParameterizedTest
  @MethodSource("parseSingleActionCases")
  void when_parseSingleActionCommand_then_returnCommand(String input, Command expectedCommand) {
    // Arrange
    final var expectedLength = 1;

    // Act
    final var commands = CommandParser.parseString(input);

    // Assert
    assertEquals(expectedLength, commands.size());
    assertEquals(expectedCommand, commands.getFirst());
  }

  @ParameterizedTest
  @MethodSource("parseMultipleCommandCases")
  void when_parseMultipleCommands_then_returnAllCommands(
      String input, List<Command> expectedCommands) {
    // Arrange
    final var expectedLength = expectedCommands.size();

    // Act
    final var commands = CommandParser.parseString(input);

    // Assert
    assertEquals(expectedLength, commands.size());
    assertEquals(expectedCommands, commands);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "MOVEE",
        "LEEFT",
        "RIIGHT",
        "MOVE\nREEPORT",
        "MOVEE\nREPORT",
        "PLACE 0,0,WEEST",
        "PLACE e,0,NORTH",
        "PLACE 0,e,NORTH",
        "PLACE 1.1,0,NORTH",
        "PLACE 0,1.1,NORTH",
        "PLACE ,1,NORTH",
        "PLACE 1,,NORTH",
        "PLACE 1,1,",
        "PLACE 1,1,MOVE"
      })
  void when_parseInvalidCommands_then_throwException(String input) {
    // Act + Assert
    assertThrows(ParseCancellationException.class, () -> CommandParser.parseString(input));
  }
}
