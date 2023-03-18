package model;

import java.util.Objects;

/**
 * A class that represents a Pixel that contains:
 *  - (r, g, b, a) channels
 *  - max value of channels
 *  - the position
 */
public class Pixel {
  private int red, green, blue, alpha;
  private int maxValue;
  private Posn pos;

  /**
   * Creates a Pixel with the given Color and Position.
   *
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
      throw new IllegalArgumentException("Invalid Pixel input!");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
    this.alpha = a;
    this.pos = p;
    this.maxValue = 255; // by default
  }

  /**
   * Creates a Pixel with the given Color and Position and max value.
   *
   * @param r - red component of the pixel
   * @param g - green component of the pixel
   * @param b - blue component of the pixel
   * @param a - alpha value of pixel
   * @param p - Position of the pixel (x,y)
   * @throws IllegalArgumentException - When given null objects OR
   *                                    alpha value less than 0.
   */
  public Pixel(int r, int g, int b, int a, int max, Posn p) throws IllegalArgumentException {
    this(r,g,b,a,p);
    if(max <= 0) {
      throw new IllegalArgumentException("Invalid max value!");
    }

    this.maxValue = max;
  }

  /**
   * Combine the given previous pixel and this pixel with the given formula.
   *
   * @param prev - the previous Pixel to be combined
   * @return - the converted Pixel
   */
  public Pixel combine(Pixel prev) { // replace 255 as MAX_VALUE
    double percentAlpha = (this.alpha / this.maxValue) +
            ((prev.alpha/this.maxValue) * (1- (this.alpha / this.maxValue)));
    int new_alpha = (int) Math.round(percentAlpha * this.maxValue);
    int new_red = (int) Math.round((convertCompWithAlpha(this.red, this.alpha)
            + (convertCompWithAlpha(prev.red, prev.alpha)
            * (1 - (this.alpha/this.maxValue)))) * (1 / percentAlpha));
    int new_green = (int) Math.round((convertCompWithAlpha(this.green, this.alpha)
            + (convertCompWithAlpha(prev.green, prev.alpha)
            * (1 - (this.alpha/this.maxValue)))) * (1 / percentAlpha));
    int new_blue = (int) Math.round((convertCompWithAlpha(this.blue, this.alpha)
            + (convertCompWithAlpha(prev.blue, prev.alpha)
            * (1 - (this.alpha/this.maxValue)))) * (1 / percentAlpha));

    return new Pixel(new_red, new_green, new_blue, new_alpha, this.pos);
  }

  private double convertCompWithAlpha(int channel, int alpha) {
    return ((alpha / this.maxValue) * channel);
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

  /**
   * Applies the filter given an option.
   *
   * @param option - the option of the filter
   */
  public void applyFilter(String option) {
    switch(option) {
      case "r":
        this.green = 0;
        this.blue = 0;
        break;
      case "g":
        this.red = 0;
        this.blue = 0;
        break;
      case "b":
        this.red = 0;
        this.green = 0;
        break;
      case "normal":
        //does nothing
        break;
      default:
        //does nothing
        break;
    }
  }

  /**
   * Modifies the position of this Pixel.
   *
   * @param p - the new position
   */
  public void changePos(Posn p) {
    this.pos = p;
  }

  /**
   * Return the position of this Pixel.
   *
   * @return - the Position of this Pixel
   */
  public Posn getPos() {
    return this.pos;
  }

  /**
   * Returns the format of this Pixel as a PPM.
   *
   * @return - the string format in rgb values
   */
  public String ppmFormat() {
    return String.format("%o %o %o", this.red, this.green, this.blue);
  }

  /**
   * Returns the max value of the channels in this Pixel.
   *
   * @return - the max value of the channels
   */
  public int getMax() {
    return this.maxValue;
  }
}
