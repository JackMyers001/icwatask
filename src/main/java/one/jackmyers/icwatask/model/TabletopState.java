package one.jackmyers.icwatask.model;

import java.util.List;
import org.jetbrains.annotations.Nullable;

/** Represents a tabletop environment where a robot can be placed and controlled */
public class TabletopState {
  private static final int DEFAULT_GRID_WIDTH = 5;
  private static final int DEFAULT_GRID_HEIGHT = 5;

  private final GridSize gridSize;
  private @Nullable RobotState robotState = null;

  /** Creates a tabletop with the default 5x5 grid size */
  public TabletopState() {
    this.gridSize = new GridSize(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
  }

  /**
   * Creates a tabletop with the specified grid size
   *
   * @param gridSize the dimensions of the tabletop grid
   */
  public TabletopState(GridSize gridSize) {
    this.gridSize = gridSize;
  }

  /**
   * Returns the size of the tabletop grid
   *
   * @return the {@link GridSize} of this tabletop
   */
  public GridSize getGridSize() {
    return gridSize;
  }

  /**
   * Returns the current state of the robot, which may be null if not yet placed
   *
   * @return the current {@link RobotState} or null if the robot hasn't been placed
   */
  public @Nullable RobotState getRobotState() {
    return robotState;
  }

  /**
   * Processes a single robot command. Commands that would move the robot off the grid or that
   * require a robot that hasn't been placed yet are silently ignored
   */
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

  /**
   * Processes multiple robot commands in sequence
   *
   * @param commands the list of {@link Command}s to process
   */
  public void handleCommands(List<Command> commands) {
    commands.forEach(this::handleCommand);
  }

  /**
   * Attempts to place the robot at the specified position and direction. Silently fails if the
   * position is outside the grid boundaries
   *
   * @param position the position (coordinate) to place the robot
   * @param direction the direction the robot should face
   */
  private void robotPlace(Vector2 position, CardinalDirection direction) {
    if (!gridSize.positionIsWithinBounds(position)) {
      return;
    }

    robotState = new RobotState(position, direction);
  }

  /**
   * Attempts to move the robot one unit in its current facing direction. Silently fails if the
   * robot hasn't been placed or would move out of the grid
   */
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

  /** Rotates the robot 90° counterclockwise. Silently fails if the robot hasn't been placed */
  private void robotRotateLeft() {
    if (robotState == null) {
      return;
    }

    robotState = robotState.withRotateLeft();
  }

  /** Rotates the robot 90° clockwise. Silently fails if the robot hasn't been placed */
  private void robotRotateRight() {
    if (robotState == null) {
      return;
    }

    robotState = robotState.withRotateRight();
  }

  /**
   * Outputs the current position and direction of the robot to standard output. Silently fails if
   * the robot hasn't been placed
   */
  private void report() {
    if (robotState == null) {
      return;
    }

    System.out.printf(
        "%s, %s, %s\n",
        robotState.position().x(), robotState.position().y(), robotState.direction());
  }
}
