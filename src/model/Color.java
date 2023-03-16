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

  /**
   * Brightens the color by a factor of luma.
   * - The luma formula is: luma = 0.2126r + 0.7512g + 0.0722b
   * - Then modify the values of this color
   */
  public void brighten() {
    float luma = (float) (0.2126 * this.red + 0.7512 * this.green + 0.0722 * this.blue);
    this.red = Math.round(this.red + luma) <= 255 ? Math.round(this.red + luma) : 255;
    this.green = Math.round(this.green + luma) <= 255 ? Math.round(this.green + luma) : 255;
    this.blue = Math.round(this.blue + luma) <= 255 ? Math.round(this.blue + luma) : 255;
  }

  /**
   * Darkens the color by a factor of luma.
   * - The luma formula is: luma = 0.2126r + 0.7512g + 0.0722b
   * - Then modify the values of this color
   */
  public void darken() {
    float luma = (float) (0.2126 * this.red + 0.7512 * this.green + 0.0722 * this.blue);
    this.red = Math.round(this.red - luma) >= 0 ? Math.round(this.red - luma) : 0;
    this.green = Math.round(this.green - luma) >= 0 ? Math.round(this.green - luma) : 0;
    this.blue = Math.round(this.blue - luma) >= 0 ? Math.round(this.blue - luma) : 0;
  }
}
