package one.jackmyers.icwatask.model;

import java.util.Objects;

public record GridSize(GridDimension width, GridDimension height) {
  public GridSize {
    Objects.requireNonNull(width, "Width cannot be null");
    Objects.requireNonNull(height, "Height cannot be null");
  }

  public GridSize(int width, int height) throws IllegalArgumentException {
    this(new GridDimension(width), new GridDimension(height));
  }

  public boolean positionIsWithinBounds(int x, int y) {
    return 0 <= x && x < width.value() && 0 <= y && y < height.value();
  }
}
