package one.jackmyers.icwatask.model;

import java.util.Objects;

/**
 * Represents the size/dimensions of a 2D grid
 *
 * @param width the horizontal dimension of the grid
 * @param height the vertical dimension of the grid
 */
public record GridSize(GridDimension width, GridDimension height) {
  /**
   * Creates a new {@link GridSize}, ensuring neither dimension is null
   *
   * @param width the horizontal dimension of the grid
   * @param height the vertical dimension of the grid
   */
  public GridSize {
    Objects.requireNonNull(width, "Width cannot be null");
    Objects.requireNonNull(height, "Height cannot be null");
  }

  /**
   * Convenience constructor that creates a {@link GridSize} from raw integer values
   *
   * @param width the horizontal size of the grid
   * @param height the vertical size of the grid
   * @throws IllegalArgumentException if either width or height is not positive
   */
  public GridSize(int width, int height) throws IllegalArgumentException {
    this(new GridDimension(width), new GridDimension(height));
  }

  /**
   * Checks whether the given coordinate is within the grid bounds
   *
   * @param x the horizontal coordinate to check
   * @param y the vertical coordinate to check
   * @return true if the position is within bounds, false otherwise
   */
  public boolean positionIsWithinBounds(int x, int y) {
    return 0 <= x && x < width.value() && 0 <= y && y < height.value();
  }

  /**
   * Checks whether the given {@link Vector2} is within the grid bounds
   *
   * @param position the coordinate to check
   * @return true if the position is within bounds, false otherwise
   */
  public boolean positionIsWithinBounds(Vector2 position) {
    return positionIsWithinBounds(position.x(), position.y());
  }
}
