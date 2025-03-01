package one.jackmyers.icwatask.model;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import one.jackmyers.icwatask.model.Command.PlaceCommand;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class TabletopStateTest {

  // region Argument Streams

  static Stream<Arguments> actionCommandCases() {
    return Stream.of(
        Arguments.of(Command.MOVE),
        Arguments.of(Command.LEFT),
        Arguments.of(Command.RIGHT),
        Arguments.of(Command.REPORT));
  }

  static Stream<Arguments> moveOutsideGridCases() {
    return Stream.of(
        // Top-left corner
        Arguments.of(new Vector2(0, 4), CardinalDirection.NORTH),
        Arguments.of(new Vector2(0, 4), CardinalDirection.WEST),
        // Bottom-right corner
        Arguments.of(new Vector2(4, 0), CardinalDirection.SOUTH),
        Arguments.of(new Vector2(4, 0), CardinalDirection.EAST));
  }

  static Stream<Arguments> rotateLeftRightCases() {
    return Stream.of(
        // Rotate left
        Arguments.of(CardinalDirection.NORTH, CardinalDirection.WEST, Command.LEFT),
        Arguments.of(CardinalDirection.WEST, CardinalDirection.SOUTH, Command.LEFT),
        Arguments.of(CardinalDirection.SOUTH, CardinalDirection.EAST, Command.LEFT),
        Arguments.of(CardinalDirection.EAST, CardinalDirection.NORTH, Command.LEFT),
        // Rotate right
        Arguments.of(CardinalDirection.NORTH, CardinalDirection.EAST, Command.RIGHT),
        Arguments.of(CardinalDirection.EAST, CardinalDirection.SOUTH, Command.RIGHT),
        Arguments.of(CardinalDirection.SOUTH, CardinalDirection.WEST, Command.RIGHT),
        Arguments.of(CardinalDirection.WEST, CardinalDirection.NORTH, Command.RIGHT));
  }

  // endregion

  // region Constructor Tests

  @Test
  @DisplayName("When constructing TabletopState with default constructor, grid size should be 5x5")
  void when_creatingWithDefaultConstructor_then_gridSizeShouldBe5x5() {
    // Arrange
    final var expected = new GridSize(5, 5);

    // Act
    final var state = new TabletopState();
    final var actual = state.getGridSize();

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName(
      "When constructing TabletopState with GridSize constructor, grid size should be correct")
  void when_creatingWithCustomGridSize_then_gridSizeShouldMatchProvidedSize() {
    // Arrange
    final var expected = new GridSize(7, 3);

    // Act
    final var state = new TabletopState(expected);
    final var actual = state.getGridSize();

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName(
      "When constructing TabletopState with default constructor, RobotState should be null")
  void when_creatingWithDefaultConstructor_then_robotStateShouldBeNull() {
    // Arrange + Act
    final var state = new TabletopState();
    final var actual = state.getRobotState();

    // Assert
    assertNull(actual);
  }

  @Test
  @DisplayName(
      "When constructing TabletopState with with GridSize constructor, RobotState should be null")
  void when_creatingWithCustomGridSize_then_robotStateShouldBeNull() {
    // Arrange + Act
    final var state = new TabletopState(new GridSize(7, 3));
    final var actual = state.getRobotState();

    // Assert
    assertNull(actual);
  }

  // endregion

  // region Place Command Tests

  @ParameterizedTest
  @CsvSource({
    "0, 0", // Bottom-left corner
    "0, 4", // Top-left corner
    "4, 4", // Top-right corner
    "4, 0", // Bottom-right corner
    "2, 2", // Center
  })
  void when_noRobot_and_validPlaceCommand_then_robotStateShouldBeUpdated(int x, int y) {
    // Arrange
    final var expectedPos = new Vector2(x, y);
    final var expectedDirection = CardinalDirection.SOUTH;
    final var expectedRobot = new RobotState(expectedPos, expectedDirection);

    final var state = new TabletopState();

    // Act
    state.handleCommand(new PlaceCommand(expectedPos, expectedDirection));
    final var actual = state.getRobotState();

    // Assert
    assertNotNull(actual);
    assertEquals(expectedRobot, actual);
  }

  @Test
  void when_existingRobot_and_validPlaceCommand_then_robotStateShouldBeUpdated() {
    // Arrange
    final var state = new TabletopState();

    {
      final var expectedPos = Vector2.ZERO;
      final var expectedDirection = CardinalDirection.NORTH;
      final var expectedRobot = new RobotState(expectedPos, expectedDirection);

      // Act
      state.handleCommand(new PlaceCommand(expectedPos, expectedDirection));
      final var actual = state.getRobotState();

      // Assert
      assertNotNull(actual);
      assertEquals(expectedRobot, actual);
    }

    // Re-place the robot in another location
    {
      final var expectedPos = new Vector2(2, 1);
      final var expectedDirection = CardinalDirection.EAST;
      final var expectedRobot = new RobotState(expectedPos, expectedDirection);

      // Act
      state.handleCommand(new PlaceCommand(expectedPos, expectedDirection));
      final var actual = state.getRobotState();

      // Assert
      assertNotNull(actual);
      assertEquals(expectedRobot, actual);
    }
  }

  @ParameterizedTest
  @CsvSource({
    // Outside around bottom-left corner
    "0, -1",
    "-1, -1",
    "-1, 0",

    // Outside around top-left corner
    "-1, 4",
    "-1, 5",
    "0, 5",

    // Outside around top-right corner
    "4, 5",
    "5, 5",
    "5, 4",

    // Outside around bottom-right corner
    "5, 0",
    "5, -1",
    "4, -1",
  })
  void when_noRobot_and_invalidPlaceCommand_then_robotStateShouldRemainNull(int x, int y) {
    // Arrange
    final var state = new TabletopState();

    // Act
    state.handleCommand(new PlaceCommand(new Vector2(x, y), CardinalDirection.NORTH));

    // Assert
    assertNull(state.getRobotState());
  }

  @Test
  void when_existingRobot_and_invalidPlaceCommand_then_robotStateShouldRemainUnchanged() {
    // Arrange
    final var expectedPos = Vector2.ONE;
    final var expectedDirection = CardinalDirection.NORTH;
    final var expectedRobot = new RobotState(expectedPos, expectedDirection);

    final var state = new TabletopState();
    state.handleCommand(new PlaceCommand(expectedPos, expectedDirection));

    // Act
    state.handleCommand(new PlaceCommand(new Vector2(5, 5), CardinalDirection.EAST));
    final var actual = state.getRobotState();

    // Assert
    assertEquals(expectedRobot, actual);
  }

  // endregion

  @ParameterizedTest
  @MethodSource("actionCommandCases")
  @DisplayName("When no robot and action command received, robot should stay null")
  void when_noRobot_and_actionCommand_then_robotStateShouldRemainNull(Command command) {
    // Arrange
    final var state = new TabletopState();

    // Act
    state.handleCommand(command);

    // Assert
    assertNull(state.getRobotState());
  }

  @ParameterizedTest
  @MethodSource("moveOutsideGridCases")
  @DisplayName(
      "When robot exists and move command would move robot out of the grid, robot should remain unchanged")
  void when_existingRobot_and_moveOutsideGrid_then_robotStateShouldRemainUnchanged(
      Vector2 pos, CardinalDirection direction) {
    // Arrange
    final var expected = new RobotState(pos, direction);

    final var state = new TabletopState();
    state.handleCommand(new PlaceCommand(pos, direction));

    // Act
    state.handleCommand(Command.MOVE);
    final var actual = state.getRobotState();

    // Assert
    assertNotNull(actual);
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @MethodSource("rotateLeftRightCases")
  @DisplayName("When robot exists and left/right command received, update robot direction")
  void when_existingRobot_andLeftRightCommand_then_robotStateShouldRotate(
      CardinalDirection initialDirection,
      CardinalDirection expectedDirection,
      Command rotateCommand) {
    // Arrange
    final var expectedPos = Vector2.ONE;
    final var expectedRobot = new RobotState(expectedPos, expectedDirection);

    final var state = new TabletopState();
    state.handleCommand(new PlaceCommand(expectedPos, initialDirection));

    // Act
    state.handleCommand(rotateCommand);
    final var actual = state.getRobotState();

    // Assert
    assertNotNull(actual);
    assertEquals(expectedRobot, actual);
  }

  // region Report Command Tests

  @Test
  void when_noRobot_and_reportCommand_then_noOutput() throws Exception {
    // Arrange
    final var state = new TabletopState();

    // Act
    final var actual = tapSystemOut(() -> state.handleCommand(Command.REPORT));

    // Assert
    assertEquals(StringUtils.EMPTY, actual);
  }

  @Test
  void when_existingRobot_and_reportCommand_then_printReport() throws Exception {
    // Arrange
    final var state = new TabletopState();
    state.handleCommand(new PlaceCommand(new Vector2(1, 2), CardinalDirection.SOUTH));

    // Act
    final var actual = tapSystemOut(() -> state.handleCommand(Command.REPORT));

    // Assert
    assertEquals("1, 2, SOUTH\n", actual);
  }

  // endregion

  @Test
  void icwaExampleInput() throws Exception {
    // Arrange
    final var state = new TabletopState();

    // Act + Assert
    state.handleCommand(new PlaceCommand(Vector2.ZERO, CardinalDirection.NORTH));

    assertNotNull(state.getRobotState());
    assertEquals(Vector2.ZERO, state.getRobotState().position());
    assertEquals(CardinalDirection.NORTH, state.getRobotState().direction());

    state.handleCommand(Command.MOVE);

    assertEquals(Vector2.Y, state.getRobotState().position());
    assertEquals(CardinalDirection.NORTH, state.getRobotState().direction());

    {
      final var actual = tapSystemOut(() -> state.handleCommand(Command.REPORT));

      assertEquals("0, 1, NORTH\n", actual);
    }

    state.handleCommand(new PlaceCommand(Vector2.ZERO, CardinalDirection.NORTH));

    assertEquals(Vector2.ZERO, state.getRobotState().position());
    assertEquals(CardinalDirection.NORTH, state.getRobotState().direction());

    state.handleCommand(Command.LEFT);

    assertEquals(Vector2.ZERO, state.getRobotState().position());
    assertEquals(CardinalDirection.WEST, state.getRobotState().direction());

    {
      final var actual = tapSystemOut(() -> state.handleCommand(Command.REPORT));

      assertEquals("0, 0, WEST\n", actual);
    }

    state.handleCommand(new PlaceCommand(new Vector2(1, 2), CardinalDirection.EAST));

    assertEquals(new Vector2(1, 2), state.getRobotState().position());
    assertEquals(CardinalDirection.EAST, state.getRobotState().direction());

    state.handleCommand(Command.MOVE);
    state.handleCommand(Command.MOVE);
    state.handleCommand(Command.LEFT);
    state.handleCommand(Command.MOVE);

    assertEquals(new Vector2(3, 3), state.getRobotState().position());
    assertEquals(CardinalDirection.NORTH, state.getRobotState().direction());

    {
      final var actual = tapSystemOut(() -> state.handleCommand(Command.REPORT));

      assertEquals("3, 3, NORTH\n", actual);
    }
  }
}
