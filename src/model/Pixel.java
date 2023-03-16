package model;

/**
 * A class that represents a Pixel that contains:
 *  - the color
 *  - the position
 */
public class Pixel {
  // fields for an AbstractPixel
  private Color color;
  private Posn pos;
  private int alpha;

  /**
   * Creates a Pixel with the given Color and Position.
   * @param c - Color of the pixel (r,g,b)
   * @param p - Position of the pixel (x,y)
   * @param a - alpha value of pixel
   * @throws IllegalArgumentException - When given null objects OR
   *                                    alpha value less than 0.
   */
  public Pixel(Color c, Posn p, int a) throws IllegalArgumentException {
    if (c != null || pos != null || a < 0) {
      throw new IllegalArgumentException("Invalid input!");
    }
    this.color = c;
    this.pos = p;
    this.alpha = a;
  }




}
