package model;

import java.util.Objects;

/**
 * A class that represents a Pixel that contains:
 *  - the color
 *  - the position
 */
public class Pixel {
  private int red, green, blue, alpha;
  private Posn pos;

  /**
   * Creates a Pixel with the given Color and Position.
   * @param r - red component of the pixel
   * @param g - green component of the pixel
   * @param b - blue component of the pixel
   * @param a - alpha value of pixel
   * @param p - Position of the pixel (x,y)
   * @throws IllegalArgumentException - When given null objects OR
   *                                    alpha value less than 0.
   */
  public Pixel(int r, int g, int b, int a, Posn p) throws IllegalArgumentException {
    if ( r < 0|| g < 0|| b < 0 ||  a < 0 || Objects.isNull(p)) {
      throw new IllegalArgumentException("Invalid input!");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
    this.alpha = a;
    this.pos = p;
  }

  public Pixel combine(Pixel prev) {
    double percentAlpha = (this.alpha / 255) + ((prev.alpha/255) * (1- (this.alpha / 255)));
    int new_alpha = (int) Math.round(percentAlpha * 255);
    int new_red = (int) Math.round((convertCompWithAlpha(this.red, this.alpha)
            + (convertCompWithAlpha(prev.red, prev.alpha)
            * (1 - (this.alpha/255)))) * (1 / percentAlpha));
    int new_green = (int) Math.round((convertCompWithAlpha(this.green, this.alpha)
            + (convertCompWithAlpha(prev.green, prev.alpha)
            * (1 - (this.alpha/255)))) * (1 / percentAlpha));
    int new_blue = (int) Math.round((convertCompWithAlpha(this.blue, this.alpha)
            + (convertCompWithAlpha(prev.blue, prev.alpha)
            * (1 - (this.alpha/255)))) * (1 / percentAlpha));

    return new Pixel(new_red, new_green, new_blue, new_alpha, this.pos);
  }

  private double convertCompWithAlpha(int channel, int alpha) {
    return ((alpha / 255) * channel);
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
