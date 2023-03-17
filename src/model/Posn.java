package model;

import java.util.Objects;

/**
 * Represents a two-dimensional Position
 * x-positions extend downward; y-positions extend rightward.
 */
public final class Posn {
  private final int row;
  private final int col;

  public Posn(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  @Override
  public String toString() {
    return String.format("%d %d", this.row, this.col);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Posn pos = (Posn) o;
    return pos.row == this.row && pos.col == this.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
