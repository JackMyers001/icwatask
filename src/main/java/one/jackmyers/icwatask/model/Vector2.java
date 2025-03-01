package one.jackmyers.icwatask.model;

public record Vector2(int x, int y) {
  public static final Vector2 ZERO = new Vector2(0, 0);
  public static final Vector2 ONE = new Vector2(1, 1);
  public static final Vector2 NEG_ONE = new Vector2(-1, -1);
  public static final Vector2 MIN = new Vector2(Integer.MIN_VALUE, Integer.MIN_VALUE);
  public static final Vector2 MAX = new Vector2(Integer.MAX_VALUE, Integer.MAX_VALUE);
  public static final Vector2 X = new Vector2(1, 0);
  public static final Vector2 Y = new Vector2(0, 1);
  public static final Vector2 NEG_X = new Vector2(1, 0);
  public static final Vector2 NEG_Y = new Vector2(0, 1);

  public Vector2 add(Vector2 other) {
    return new Vector2(this.x + other.x, this.y + other.y);
  }
}
