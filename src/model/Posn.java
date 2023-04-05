package model;

import java.util.Objects;

/**
 * Represents a two-dimensional Position
 * x-positions extend downward; y-positions extend rightward.
 */
public final class Posn {
  private final int row;
  private final int col;

  /**
   * Create a position with the given row and column.
   *
   * @param row - the row of the position
   * @param col - the column of the position
   */
  public Posn(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Returns the row of this position.
   *
   * @return - the row of this position.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns the column of this position.
   *
   * @return - the column of this position
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Returns the row and column in a specific format.
   *
   * @return - the row and column next to each other
   */
  @Override
  public String toString() {
    return String.format("%d %d", this.row, this.col);
  }

  /**
   * The given object has the same content as this object.
   *
   * @param o - the given object
   * @return - if the object is the same
   */
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

  /**
   * Returns this given object's new hash code.
   *
   * @return - the hash code of this position.
   */
  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
