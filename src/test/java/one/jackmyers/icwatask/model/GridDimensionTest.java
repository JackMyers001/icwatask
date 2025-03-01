package one.jackmyers.icwatask.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GridDimensionTest {

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 100, Integer.MAX_VALUE})
  @DisplayName("Should accept all positive integers")
  void shouldAcceptAllPositiveIntegers(int validValue) {
    // Act
    var gridDimension = new GridDimension(validValue);

    // Assert
    assertEquals(validValue, gridDimension.value());
  }

  @Test
  @DisplayName("Should throw exception when value is zero")
  void shouldThrowExceptionWhenValueIsZero() {
    // Act + Assert
    assertThrows(IllegalArgumentException.class, () -> new GridDimension(0));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -2, -100, Integer.MIN_VALUE})
  @DisplayName("Should throw exception when value is negative")
  void shouldThrowExceptionWhenValueIsNegative(int negativeValue) {
    // Act + Assert
    assertThrows(IllegalArgumentException.class, () -> new GridDimension(negativeValue));
  }
}
