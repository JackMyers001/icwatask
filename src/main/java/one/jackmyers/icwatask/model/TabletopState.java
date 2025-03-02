package one.jackmyers.icwatask.model;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class TabletopState {
  private static final int DEFAULT_GRID_WIDTH = 5;
  private static final int DEFAULT_GRID_HEIGHT = 5;

  private final GridSize gridSize;
  private @Nullable RobotState robotState = null;

  public TabletopState() {
    this.gridSize = new GridSize(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
  }

  public TabletopState(GridSize gridSize) {
    this.gridSize = gridSize;
  }

  public GridSize getGridSize() {
    return gridSize;
  }

  public @Nullable RobotState getRobotState() {
    return robotState;
  }

  public void handleCommand(Command command) {
    switch (command) {
      case Command.PlaceCommand(Vector2 position, CardinalDirection direction) ->
          robotPlace(position, direction);
      case Command.MoveCommand() -> robotMove();
      case Command.LeftCommand() -> robotRotateLeft();
      case Command.RightCommand() -> robotRotateRight();
      case Command.ReportCommand() -> report();
    }
  }

  public void handleCommands(List<Command> commands) {
    commands.forEach(this::handleCommand);
  }

  private void robotPlace(Vector2 position, CardinalDirection direction) {
    if (!gridSize.positionIsWithinBounds(position)) {
      return;
    }

    robotState = new RobotState(position, direction);
  }

  private void robotMove() {
    if (robotState == null) {
      return;
    }

    final var directionVector = robotState.direction().asVector();
    final var newPos = robotState.position().add(directionVector);

    if (!gridSize.positionIsWithinBounds(newPos)) {
      return;
    }

    robotState = robotState.withPosition(newPos);
  }

  private void robotRotateLeft() {
    if (robotState == null) {
      return;
    }

    robotState = robotState.withRotateLeft();
  }

  private void robotRotateRight() {
    if (robotState == null) {
      return;
    }

    robotState = robotState.withRotateRight();
  }

  private void report() {
    if (robotState == null) {
      return;
    }

    System.out.printf(
        "%s, %s, %s\n",
        robotState.position().x(), robotState.position().y(), robotState.direction());
  }
}
