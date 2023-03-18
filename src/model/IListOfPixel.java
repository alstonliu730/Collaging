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
  public List<Pixel> render();

  /**
   * Converts the given list of Pixel into a matrix of Pixels.
   *
   * @param pixels - the List of Pixels to be converted
   * @return - converted matrix of Pixels
   * @throws IllegalStateException - When the size is not the same
   */
  public Pixel[][] convertToMatrix(List<Pixel> pixels) throws IllegalStateException;

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public Pixel getPixel(Posn pos);

  /**
   * Returns the height of this IListOfPixel.
   *
   * @return - the height of this object
   */
  public int getHeight();

  /**
   * Returns the width of this IListOfPixel.
   *
   * @return - the width of this object
   */
  public int getWidth();

  /**
   * Returns the max value of this IListOfPixel.
   *
   * @return - the max value of each Pixel
   */
  public int getMax();
}
