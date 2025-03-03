package one.jackmyers.icwatask.model;

/** A 2-dimensional vector */
public record Vector2(int x, int y) {
  /** All zeros */
  public static final Vector2 ZERO = new Vector2(0, 0);

  /** All ones */
  public static final Vector2 ONE = new Vector2(1, 1);

  /** All negative ones */
  public static final Vector2 NEG_ONE = new Vector2(-1, -1);

  /** All {@link Integer MIN_VALUE} */
  public static final Vector2 MIN = new Vector2(Integer.MIN_VALUE, Integer.MIN_VALUE);

  /** All {@link Integer MAX_VALUE} */
  public static final Vector2 MAX = new Vector2(Integer.MAX_VALUE, Integer.MAX_VALUE);

  /** A unit vector pointing along the positive X axis */
  public static final Vector2 X = new Vector2(1, 0);

  /** A unit vector pointing along the positive Y axis */
  public static final Vector2 Y = new Vector2(0, 1);

  /** A unit vector pointing along the negative X axis */
  public static final Vector2 NEG_X = new Vector2(-1, 0);

  /** A unit vector pointing along the negative Y axis */
  public static final Vector2 NEG_Y = new Vector2(0, -1);

  /**
   * Creates a new vector representing the sum of this vector and another.
   *
   * @param other The vector to add to this one
   * @return A new {@link Vector2} with components equal to the sum of both vectors' components
   */
  public Vector2 add(Vector2 other) {
    return new Vector2(this.x + other.x, this.y + other.y);
  }
}
