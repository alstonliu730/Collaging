package model;

import java.util.Objects;

/**
 * Represents a two-dimensional Posninate.
 * Rows extend downward; columns extend rightward.
 */
public final class Posn {
  public final int row;
  public final int col;

  public Posn(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public String toString() {
    return String.format("(r%d,c%d)", row, col);
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
    return row == Posn.row && col == Posn.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
