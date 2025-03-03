package one.jackmyers.icwatask.model;

/** Represents the four primary compass directions in a 2D coordinate system */
public enum CardinalDirection {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  /**
   * Returns a new direction after rotating 90° counterclockwise
   *
   * @return the {@link CardinalDirection} to the left of this one
   */
  public CardinalDirection rotateLeft() {
    return switch (this) {
      case NORTH -> WEST;
      case EAST -> NORTH;
      case SOUTH -> EAST;
      case WEST -> SOUTH;
    };
  }

  /**
   * Returns a new direction after rotating 90° clockwise
   *
   * @return the direction rotated 90° right
   */
  public CardinalDirection rotateRight() {
    return switch (this) {
      case NORTH -> EAST;
      case EAST -> SOUTH;
      case SOUTH -> WEST;
      case WEST -> NORTH;
    };
  }

  /**
   * Converts this direction into its corresponding unit vector representation
   *
   * @return a unit {@link Vector2} pointing in this direction
   */
  public Vector2 asVector() {
    return switch (this) {
      case NORTH -> Vector2.Y;
      case EAST -> Vector2.X;
      case SOUTH -> Vector2.NEG_Y;
      case WEST -> Vector2.NEG_X;
    };
  }
}
