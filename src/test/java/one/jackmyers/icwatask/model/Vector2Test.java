package one.jackmyers.icwatask.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Vector2Test {

  @ParameterizedTest
  @MethodSource("addCases")
  void shouldAddSuccessfully(Vector2 a, Vector2 b, Vector2 expected) {
    // Act
    var actual = a.add(b);

    // Assert
    assertEquals(expected, actual);
  }

  static Stream<Arguments> addCases() {
    final int INT_MIN = Integer.MIN_VALUE;
    final int INT_MAX = Integer.MAX_VALUE;

    return Stream.of(
        // Zero all
        Arguments.of(Vector2.ZERO, Vector2.ZERO, Vector2.ZERO),

        // Positive A, zero B
        Arguments.of(Vector2.X, Vector2.ZERO, Vector2.X),
        Arguments.of(Vector2.Y, Vector2.ZERO, Vector2.Y),
        Arguments.of(Vector2.ONE, Vector2.ZERO, Vector2.ONE),

        // Zero A, positive B
        Arguments.of(Vector2.ZERO, Vector2.X, Vector2.X),
        Arguments.of(Vector2.ZERO, Vector2.Y, Vector2.Y),
        Arguments.of(Vector2.ZERO, Vector2.ONE, Vector2.ONE),

        // Positive A, positive B
        Arguments.of(new Vector2(1, 2), new Vector2(3, 4), new Vector2(4, 6)),

        // Negative A, zero B
        Arguments.of(Vector2.NEG_X, Vector2.ZERO, Vector2.NEG_X),
        Arguments.of(Vector2.NEG_Y, Vector2.ZERO, Vector2.NEG_Y),

        // Zero A, negative B
        Arguments.of(Vector2.ZERO, Vector2.NEG_X, Vector2.NEG_X),
        Arguments.of(Vector2.ZERO, Vector2.NEG_Y, Vector2.NEG_Y),

        // Negative A, negative B
        Arguments.of(Vector2.NEG_ONE, Vector2.NEG_ONE, new Vector2(-2, -2)),
        Arguments.of(new Vector2(-1, -2), new Vector2(-3, -4), new Vector2(-4, -6)),

        // MIN A, zero B
        Arguments.of(new Vector2(INT_MIN, 0), Vector2.ZERO, new Vector2(INT_MIN, 0)),
        Arguments.of(new Vector2(0, INT_MIN), Vector2.ZERO, new Vector2(0, INT_MIN)),
        Arguments.of(Vector2.MIN, Vector2.ZERO, Vector2.MIN),

        // Zero A, MIN B
        Arguments.of(Vector2.ZERO, new Vector2(INT_MIN, 0), new Vector2(INT_MIN, 0)),
        Arguments.of(Vector2.ZERO, new Vector2(0, INT_MIN), new Vector2(0, INT_MIN)),
        Arguments.of(Vector2.ZERO, Vector2.MIN, Vector2.MIN),

        // MIN A, MIN B
        Arguments.of(Vector2.MIN, Vector2.MIN, Vector2.ZERO),

        // MAX A, zero B
        Arguments.of(new Vector2(INT_MAX, 0), Vector2.ZERO, new Vector2(INT_MAX, 0)),
        Arguments.of(new Vector2(0, INT_MAX), Vector2.ZERO, new Vector2(0, INT_MAX)),
        Arguments.of(Vector2.MAX, Vector2.ZERO, Vector2.MAX),

        // Zero A, MAX B
        Arguments.of(Vector2.ZERO, new Vector2(INT_MAX, 0), new Vector2(INT_MAX, 0)),
        Arguments.of(Vector2.ZERO, new Vector2(0, INT_MAX), new Vector2(0, INT_MAX)),
        Arguments.of(Vector2.ZERO, Vector2.MAX, Vector2.MAX),

        // MAX A, MAX B
        Arguments.of(Vector2.MAX, Vector2.MAX, new Vector2(-2, -2)),

        // MAX A, 1 B
        Arguments.of(new Vector2(INT_MAX, 0), Vector2.X, new Vector2(INT_MIN, 0)),
        Arguments.of(new Vector2(0, INT_MAX), Vector2.Y, new Vector2(0, INT_MIN)),

        // 1 A, MAX B
        Arguments.of(Vector2.X, new Vector2(INT_MAX, 0), new Vector2(INT_MIN, 0)),
        Arguments.of(Vector2.Y, new Vector2(0, INT_MAX), new Vector2(0, INT_MIN)));
  }
}
