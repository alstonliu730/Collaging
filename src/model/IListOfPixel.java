package model;

import java.util.List;

/**
 * An interface that represents the behavior of an IListOfPixel.
 */
public interface IListOfPixel {

  /**
   * Returns the current Pixels in the matrix as a 1D array.
   *
   * @return - a list of the current Pixels
   */
  List<IPixel> render();

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  IPixel getPixel(IPosn pos);

  /**
   * Returns the height of this IListOfPixel.
   *
   * @return - the height of this object
   */
  int getHeight();

  /**
   * Returns the width of this IListOfPixel.
   *
   * @return - the width of this object
   */
  int getWidth();

  /**
   * Returns the max value of this IListOfPixel.
   *
   * @return - the max value of each Pixel
   */
  int getMax();
}
