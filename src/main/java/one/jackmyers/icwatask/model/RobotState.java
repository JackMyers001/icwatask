package one.jackmyers.icwatask.model;

import java.util.Objects;

/**
 * Represents the current state of a toy robot
 *
 * @param position the location of the robot within a grid
 * @param direction the direction the robot is facing
 */
public record RobotState(Vector2 position, CardinalDirection direction) {
  /**
   * Creates a new {@link RobotState}, ensuring none of the parameters are null
   *
   * @param position the location of the robot within a grid
   * @param direction the direction the robot is facing
   */
  public RobotState {
    Objects.requireNonNull(position, "Position cannot be null");
    Objects.requireNonNull(direction, "Direction cannot be null");
  }

  /**
   * Creates an updated {@link RobotState} with an updated {@link RobotState position}
   *
   * @param newPosition the robot's new position
   * @return an updated version of the robot
   */
  public RobotState withPosition(Vector2 newPosition) {
    return new RobotState(newPosition, this.direction);
  }

  /**
   * Creates an updated {@link RobotState} with the {@link RobotState direction} rotated 90°
   * counterclockwise
   *
   * @return an updated version of the robot
   */
  public RobotState withRotateLeft() {
    return new RobotState(this.position, this.direction.rotateLeft());
  }

  /**
   * Creates an updated {@link RobotState} with the {@link RobotState direction} rotated 90°
   * clockwise
   *
   * @return an updated version of the robot
   */
  public RobotState withRotateRight() {
    return new RobotState(this.position, this.direction.rotateRight());
  }
}
