package one.jackmyers.icwatask.model;

/** Represents commands that can be issued to control a robot */
public sealed interface Command {
  /**
   * @see MoveCommand
   */
  Command MOVE = new MoveCommand();

  /**
   * @see LeftCommand
   */
  Command LEFT = new LeftCommand();

  /**
   * @see RightCommand
   */
  Command RIGHT = new RightCommand();

  /**
   * @see ReportCommand
   */
  Command REPORT = new ReportCommand();

  /**
   * Command to place a robot in a specific position and orientation
   *
   * @param position the coordinate to place the robot at
   * @param direction the direction the robot should face
   */
  record PlaceCommand(Vector2 position, CardinalDirection direction) implements Command {}

  /** Command to move a robot forward one unit in its current facing direction */
  record MoveCommand() implements Command {}

  /** Command to rotate a robot 90° counterclockwise */
  record LeftCommand() implements Command {}

  /** Command to rotate a robot 90° clockwise */
  record RightCommand() implements Command {}

  /** Command to output a robot's current position and direction */
  record ReportCommand() implements Command {}
}
