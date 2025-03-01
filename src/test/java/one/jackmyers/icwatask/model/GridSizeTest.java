package one.jackmyers.icwatask.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class GridSizeTest {

  @ParameterizedTest
  @MethodSource("nullDimensionCases")
  @DisplayName("Should throw exception when dimensions are null")
  void shouldThrowExceptionWhenDimensionsAreNull(GridDimension width, GridDimension height) {
    // Act + Assert
    assertThrows(NullPointerException.class, () -> new GridSize(width, height));
  }

  static Stream<Arguments> nullDimensionCases() {
    var validDimension = new GridDimension(5);

    return Stream.of(
        Arguments.of(null, validDimension),
        Arguments.of(validDimension, null),
        Arguments.of(null, null));
  }

  @ParameterizedTest
  @CsvSource({"1, 0", "0, 1", "0, 0", "1, -1", "-1, 1", "-1, -1"})
  @DisplayName("Should throw exception when dimensions are invalid")
  void shouldThrowExceptionWhenInvalidDimension(int x, int y) {
    // Act + Assert
    assertThrows(IllegalArgumentException.class, () -> new GridSize(x, y));
  }

  @ParameterizedTest
  @CsvSource({
    "0, 0, true",
    "1, 0, false",
    "0, 1, false",
    "1, 1, false",
    "-1, 0, false",
    "0, -1, false",
    "-1, -1, false",
  })
  @DisplayName("Should work with a 1x1 grid")
  void shouldWorkWithSingleCellGrid(int x, int y, boolean expected) {
    // Arrange
    var gridSize = new GridSize(1, 1);

    // Act
    var actual = gridSize.positionIsWithinBounds(x, y);

    // Assert
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({
    "0, 0, true", // Bottom-left corner
    "0, 2, true", // Top-left corner
    "6, 2, true", // Top-right corner
    "6, 0, true", // Bottom-right corner
    "3, 1, true", // Center

    // Outside around bottom-left corner
    "0, -1, false",
    "-1, -1, false",
    "-1, 0, false",

    // Outside around top-left corner
    "-1, 2, false",
    "-1, 3, false",
    "0, 3, false",

    // Outside around top-right corner
    "6, 3, false",
    "7, 3, false",
    "7, 2, false",

    // Outside around bottom-right corner
    "7, 0, false",
    "7, -1, false",
    "6, -1, false",
  })
  @DisplayName("Should work with a non-square grid")
  void shouldWorkWithNonSquareGrid(int x, int y, boolean expected) {
    // Arrange
    var gridSize = new GridSize(7, 3);

    // Act
    var actual = gridSize.positionIsWithinBounds(x, y);

    // Assert
    assertEquals(expected, actual);
  }
}
