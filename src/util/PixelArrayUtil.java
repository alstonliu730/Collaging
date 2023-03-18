package util;

import model.Pixel;

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
  public static int getMaxValue(Pixel[][] pixels) {
    int maxValue = pixels[0][0].getMax();

    for(int i = 0; i < pixels.length; i++) {
      for(int j = 0; j < pixels[0].length; j++){
        if(pixels[i][j].getMax() > maxValue){
          maxValue = pixels[i][j].getMax();
        }
      }
    }

    return maxValue;
  }
}
