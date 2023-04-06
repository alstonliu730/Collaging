package util;

import java.util.List;
import java.util.Objects;

import model.IPixel;
import model.Pixel;
import model.Posn;

/**
 * A utility class that helps with Pixel arrays.
 */
public class PixelArrayUtil {
  /**
   * Returns the max value of the Pixels in the given matrix.
   *
   * @param pixels - the matrix of Pixels
   * @return - the max value of each channel in the matrix
   */
  public static int getMaxValue(IPixel[][] pixels) {
    int maxValue = pixels[0][0].getMax();

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        if (pixels[i][j].getMax() > maxValue) {
          maxValue = pixels[i][j].getMax();
        }
      }
    }

    return maxValue;
  }

  /**
   * Converts the given list of Pixel into a matrix of Pixels.
   *
   * @param pixels - the List of Pixels to be converted
   * @return - converted matrix of Pixels
   * @throws IllegalStateException - When the size is not the same
   */
  public static IPixel[][] convertToMatrix(List<IPixel> pixels, int height, int width)
          throws IllegalArgumentException {
    if (Objects.isNull(pixels) || (height * width) != pixels.size()) {
      throw new IllegalArgumentException("Invalid matrix input!");
    }

    IPixel[][] new_matrix = new Pixel[height][width];
    for (IPixel p : pixels) {
      Posn position = p.getPos();
      new_matrix[position.getRow()][position.getCol()] = p;
    }

    return new_matrix;
  }

  /**
   * Combines the rgb array values of the current pixel and previous pixel.
   *
   * @param curr     - the current pixel's rgba array values
   * @param prev     - the previous pixel's rgba array values
   * @param maxValue - the max value of the rgba values
   * @return - the combined values of the rgba values
   */
  public static int[] combinePixel(int[] curr, int[] prev, int maxValue)
          throws IllegalArgumentException {
    if (Objects.isNull(curr) || Objects.isNull(prev)) {
      throw new IllegalArgumentException("Invalid rgb array input!");
    }

    int r = curr[0];
    int g = curr[1];
    int b = curr[2];
    int a = curr[3];
    int dr = prev[0];
    int dg = prev[1];
    int db = prev[2];
    int da = prev[3];

    double percentAlpha = (a / maxValue)
            + ((da / maxValue) * (1 - (a / maxValue)));
    int new_alpha = (int) Math.round(percentAlpha * maxValue);
    int new_red = (int) Math.round((convertCompWithAlpha(r, a, maxValue)
            + (convertCompWithAlpha(dr, da, maxValue) * (1 - (a / maxValue))))
            * (1 / percentAlpha));
    int new_green = (int) Math.round((convertCompWithAlpha(g, a, maxValue)
            + (convertCompWithAlpha(dg, da, maxValue)
            * (1 - (a / maxValue)))) * (1 / percentAlpha));
    int new_blue = (int) Math.round((convertCompWithAlpha(b, a, maxValue)
            + (convertCompWithAlpha(db, da, maxValue)
            * (1 - (a / maxValue)))) * (1 / percentAlpha));

    // if any of the values go over the maxValue then set it to the max value
    new_red = Math.min(new_red, maxValue);
    new_green = Math.min(new_green, maxValue);
    new_blue = Math.min(new_blue, maxValue);

    // create the color array value
    int[] rgba = {new_red, new_green, new_blue, new_alpha};
    // return the new Pixel
    return rgba;
  }

  /*
    Helper method to combine channels with alpha value
   */
  private static double convertCompWithAlpha(int channel, int alpha, int maxValue) {
    return ((alpha / maxValue) * channel);
  }
}
