package one.jackmyers.icwatask.model;

public enum CardinalDirection {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  public CardinalDirection rotateLeft() {
    return switch (this) {
      case NORTH -> WEST;
      case EAST -> NORTH;
      case SOUTH -> EAST;
      case WEST -> SOUTH;
    };
  }

  public CardinalDirection rotateRight() {
    return switch (this) {
      case NORTH -> EAST;
      case EAST -> SOUTH;
      case SOUTH -> WEST;
      case WEST -> NORTH;
    };
  }
}
