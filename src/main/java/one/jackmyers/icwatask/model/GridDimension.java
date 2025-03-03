package one.jackmyers.icwatask.model;

/**
 * Represents the length/size of a single grid axis. Ensures that dimensions are always positive
 * integers
 */
public record GridDimension(int value) {
  /**
   * Creates a new {@link GridDimension}, validating that the given dimension size is positive
   *
   * @param value positive integer size of this dimension
   * @throws IllegalArgumentException if the value is less than 1
   */
  public GridDimension {
    if (value <= 0) {
      throw new IllegalArgumentException("GridSize must be greater than 0");
    }
  }
}
