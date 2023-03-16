package model;

/**
 * A class that represents a Color
 */
public class Color {
  private int red;
  private int green;
  private int blue;

  /**
   * Creates a color object with the given red, green, and blue values
   * @param r - red value of the Color
   * @param g - green value of the Color
   * @param b - blue value of the Color
   * @throws IllegalArgumentException - when the rgb values are less than 0
   */
  public Color(int r, int g, int b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Invalid input for color.");
    }
    // instantiate the rgb values
    this.red = r;
    this.green = g;
    this.blue = b;
  }


}
