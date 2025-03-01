package one.jackmyers.icwatask.model;

import java.util.Objects;

public record RobotState(Vector2 position, CardinalDirection direction) {
  public RobotState {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(direction, "Direction cannot be null");
  }

  public RobotState withPosition(Vector2 newPosition) {
    return new RobotState(newPosition, this.direction);
  }

  public RobotState withRotateLeft() {
    return new RobotState(this.position, this.direction.rotateLeft());
  }

  public RobotState withRotateRight() {
    return new RobotState(this.position, this.direction.rotateRight());
  }
}
