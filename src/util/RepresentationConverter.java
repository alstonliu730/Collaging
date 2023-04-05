package util;

/**
 * This class contains utility methods to convert an RGB representation
 * to HSL and back and print those results.
 * Feel free to change these methods as required.
 */
public class RepresentationConverter {

  /**
   * Converts an RGB representation in the range 0-1 into an HSL
   * representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   *
   * @param r red value of the RGB between 0 and 1
   * @param g green value of the RGB between 0 and 1
   * @param b blue value of the RGB between 0 and 1
   * @return the hsl representation in an array
   */
  public static double[] convertRGBtoHSL(double r, double g, double b) {
    double componentMax = Math.max(r, Math.max(g, b));
    double componentMin = Math.min(r, Math.min(g, b));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue, saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == r) {
        hue = (g - b) / delta;
        while (hue < 0) {
          hue += 6; //hue must be positive to find the appropriate modulus
        }
        hue = hue % 6;
      } else if (componentMax == g) {
        hue = (b - r) / delta;
        hue += 2;
      } else if (componentMax == b) {
        hue = (r - g) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }
    /*System.out.print("R: " + r + " G: " + g + " B: " + b);
    System.out.println(" --> H: " + hue + " S: " + saturation + " L: " + lightness);*/
    // create hsl array
    double[] hsl = {hue, saturation, lightness};

    // returns the hsl array representation
    return hsl;
  }


  /**
   * Converts an HSL representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-1
   *
   * @param hue        hue of the HSL representation
   * @param saturation saturation of the HSL representation
   * @param lightness  lightness of the HSL representation
   */
  public static int[] convertHSLtoRGB(double hue, double saturation, double lightness) {
    double r = convertFn(hue, saturation, lightness, 0) * 255;
    double g = convertFn(hue, saturation, lightness, 8) * 255;
    double b = convertFn(hue, saturation, lightness, 4) * 255;
    /*System.out.print("Red: " + r);
    System.out.print("Green: " + g);
    System.out.println("Blue: " + b);*/

    // create rgb array representation
    int[] rgb = {(int) Math.round(r), (int) Math.round(g), (int) Math.round(b)};

    // return rgb array representation
    return rgb;
  }

  /*
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private static double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }
}
