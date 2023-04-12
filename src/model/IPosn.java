package model;

/**
 * An interface that represents a position in a grid.
 */
public interface IPosn {
  /**
   * Returns the row of this position.
   *
   * @return - the row of this position.
   */
  int getRow();

  /**
   * Returns the column of this position.
   *
   * @return - the column of this position
   */
  int getCol();

  /**
   * Returns the row and column in a specific format.
   *
   * @return - the row and column next to each other
   */
  @Override
  String toString();

  /**
   * The given object has the same content as this object.
   *
   * @param o - the given object
   * @return - if the object is the same
   */
  @Override
  boolean equals(Object o);

  /**
   * Returns this given object's new hash code.
   *
   * @return - the hash code of this position.
   */
  @Override
  int hashCode();
}
