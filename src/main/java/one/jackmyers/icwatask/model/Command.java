package one.jackmyers.icwatask.model;

public sealed interface Command {
  record PlaceCommand(Vector2 position, CardinalDirection direction) implements Command {}

  record MoveCommand() implements Command {}

  record LeftCommand() implements Command {}

  record RightCommand() implements Command {}

  record ReportCommand() implements Command {}
}
