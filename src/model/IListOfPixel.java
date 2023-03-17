package model;

import java.util.List;

public interface IListOfPixel {

  /**
   * Returns the current Pixels in the matrix as a 1D array.
   * @return - a list of the current Pixels
   */
  public List<Pixel> render();

  /**
   *
   * @param pixels
   * @return
   * @throws IllegalStateException
   */
  public Pixel[][] convertToMatrix(List<Pixel> pixels) throws IllegalStateException;

  /**
   * Returns the Pixel at the given coordinate.
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public Pixel getPixel(Posn pos);

  /**
   * Returns the height of this IListOfPixel
   *
   * @return - the height of this object
   */
  public int getHeight();

  /**
   * Returns the width of this IListOfPixel
   *
   * @return - the width of this object
   */
  public int getWidth();
}
