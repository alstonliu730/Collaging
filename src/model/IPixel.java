package model;

/**
 * An interface that represents IPixel.
 */
public interface IPixel {
  /**
   * Sets this pixels color given rgba values.
   *
   * @param values - rgba array
   * @throws IllegalArgumentException - when the given array is null or
   *                                    does not have enough values
   */
  void setColor(int[] values) throws IllegalArgumentException;

  /**
   * Return the values of the color and transparency in an array.
   *
   * @return - the values of his color
   */
  int[] getValues();

  /**
   * Combine the given previous pixel and this pixel with the given formula.
   *
   * @param prev - the previous Pixel to be combined
   * @return - the converted Pixel
   */
  IPixel combine(IPixel prev);

  /**
   * Brightens the color by Screen method using the HSL representation.
   *
   * @param prev - the previous Pixel
   */
  IPixel brighten(IPixel prev);

  /**
   * Darkens the color by the Multiply method using the HSL representation.
   *
   * @param prev - the previous Pixel
   */
  IPixel darken(IPixel prev);

  /**
   * Inverts the color using the previous pixel.
   *
   * @param prev - the previous Pixel
   */
  IPixel invert(IPixel prev);

  /**
   * Applies the filter given an option.
   *
   * @param option - the option of the filter
   */
  IPixel applyFilter(String option);

  /**
   * Modifies the position of this Pixel.
   *
   * @param p - the new position
   */
  void changePos(Posn p);

  /**
   * Return the position of this Pixel.
   *
   * @return - the Position of this Pixel
   */
  Posn getPos();

  /**
   * Return the representation of this Pixel in PPM Format.
   *
   * @return - the format of this pixel
   */
  String toString();

  /**
   * Return the representation of this Pixel in (R,G,B,A) format.
   *
   * @return - the format of this pixel
   */
  String rgbaString();

  /**
   * Return the maximum value of this Pixel.
   *
   * @return - the maximum of this value
   */
  int getMax();
}
