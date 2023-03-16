package model;

import java.util.Objects;

/**
 * Represents a two-dimensional Position
 * x-positions extend downward; y-positions extend rightward.
 */
public final class Posn {
  public final int x;
  public final int y;

  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("%d %d", x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Posn Posn = (Posn) o;
    return x == Posn.x && y == Posn.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
