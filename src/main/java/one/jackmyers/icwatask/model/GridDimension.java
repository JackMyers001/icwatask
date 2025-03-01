package one.jackmyers.icwatask.model;

public record GridDimension(int value) {
  public GridDimension {
    if (value <= 0) {
      throw new IllegalArgumentException("GridSize must be greater than 0");
    }
  }
}
