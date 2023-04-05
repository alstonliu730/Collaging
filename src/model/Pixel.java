package model;

import java.util.Objects;

import util.PixelArrayUtil;
import util.RepresentationConverter;

/**
 * A class that represents a Pixel that contains:
 * - (r, g, b, a) channels
 * - max value of channels
 * - the position.
 */
public class Pixel implements IPixel {
  private int red;
  private int green;
  private int blue;
  private int alpha;
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
   *                                  alpha value less than 0.
   */
  public Pixel(int r, int g, int b, int a, Posn p) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0 || a < 0 || Objects.isNull(p)) {
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
   *                                  alpha value less than 0.
   */
  public Pixel(int r, int g, int b, int a, int max, Posn p) throws IllegalArgumentException {
    this(r, g, b, a, p);
    if (max <= 0) {
      throw new IllegalArgumentException("Invalid max value!");
    }

    this.maxValue = max;
  }

  /**
   * Sets this pixels color given rgba values.
   *
   * @param values - rgba array
   */
  public void setColor(int[] values) throws IllegalArgumentException {
    if (Objects.isNull(values) || values.length != 4) {
      throw new IllegalArgumentException("Invalid color input!");
    }
    this.red = values[0];
    this.green = values[1];
    this.blue = values[2];
    this.alpha = values[3];
  }

  /**
   * Return the values of the color and transparency in an array.
   *
   * @return - the values of his color
   */
  @Override
  public int[] getValues() {
    return new int[]{this.red, this.green, this.blue, this.alpha};
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
  @Override
  public String toString() {
    return String.format("%d %d %d", this.red, this.green, this.blue);
  }

  /**
   * Return the representation of this Pixel in (R,G,B,A) format.
   *
   * @return - the format of this pixel
   */
  @Override
  public String rgbaString() {
    return String.format("%d %d %d %d", this.red, this.green, this.blue, this.alpha);
  }

  /**
   * Returns the max value of the channels in this Pixel.
   *
   * @return - the max value of the channels
   */
  public int getMax() {
    return this.maxValue;
  }

  /**
   * Combine the given previous pixel and this pixel with the given formula.
   *
   * @param prev - the previous Pixel to be combined
   * @return - the converted Pixel
   */
  public Pixel combine(IPixel prev) {
    int[] rgba = this.getValues();
    int[] drgba = prev.getValues();
    int[] new_rgba = PixelArrayUtil.combinePixel(rgba, drgba, this.maxValue);

    // return the new Pixel
    return new Pixel(new_rgba[0], new_rgba[1], new_rgba[2], new_rgba[3], this.pos);
  }

  /**
   * Brightens the color by Screen method using the HSL representation.
   *
   * @param prev - the previous Pixel
   * @return - the pixel with the brightened pixel values
   */
  @Override
  public IPixel brighten(IPixel prev) {
    if (Objects.isNull(prev)) {
      return this;
    }
    assert (this.pos.equals(prev.getPos()));

    // get HSL representation of the pixel
    double[] currHSL = this.getHSL(this);
    double[] prevHSL = this.getHSL(prev);

    // use only the lightness value of the hsl representation
    double l = currHSL[2];
    double dl = prevHSL[2];

    // Use screen method to brighten a pixel
    double newL = (1 - ((1 - l) * (1 - dl)));
    int[] newRGB = RepresentationConverter.convertHSLtoRGB(currHSL[0], currHSL[1], newL);

    // return the new pixel
    return new Pixel(newRGB[0], newRGB[1], newRGB[2], this.alpha, this.maxValue, this.pos);
  }

  /**
   * Darkens the color by the Multiply method using the HSL representation.
   *
   * @param prev - the previous Pixel
   */
  @Override
  public IPixel darken(IPixel prev) {
    if (Objects.isNull(prev)) {
      return this;
    }
    assert (this.pos.equals(prev.getPos()));
    // get HSL representation of the pixel
    double[] currHSL = this.getHSL(this);
    double[] prevHSL = this.getHSL(prev);

    // use only the lightness value of the hsl representation
    double l = currHSL[2];
    double dl = prevHSL[2];

    // Use multiply method to brighten a pixel
    double newL = l * dl;
    int[] newRGB = RepresentationConverter.convertHSLtoRGB(currHSL[0], currHSL[1], newL);

    // return the new pixel
    return new Pixel(newRGB[0], newRGB[1], newRGB[2], this.alpha, this.maxValue, this.pos);
  }

  /*
   * Helper function that gets the HSL values for the given IPixel.
   *
   */
  private double[] getHSL(IPixel p) {
    int[] pRGBA = p.getValues();
    return RepresentationConverter.convertRGBtoHSL((double) pRGBA[0] / p.getMax(),
            (double) pRGBA[1] / p.getMax(), (double) pRGBA[2] / p.getMax());
  }

  /**
   * Inverts the color using the previous pixel.
   *
   * @param prev - the previous Pixel
   */
  @Override
  public IPixel invert(IPixel prev) {
    if (Objects.isNull(prev)) {
      return this;
    }
    assert (this.pos.equals(prev.getPos()));
    int[] currRGBA = this.getValues();
    int[] prevRGBA = prev.getValues();

    int newR = Math.abs(currRGBA[0] - prevRGBA[0]);
    int newG = Math.abs(currRGBA[1] - prevRGBA[1]);
    int newB = Math.abs(currRGBA[2] - prevRGBA[2]);

    return new Pixel(newR, newG, newB, this.alpha, this.maxValue, this.pos);
  }

  /**
   * Applies the filter given an option.
   *
   * @param option - the option of the filter
   */
  @Override
  public IPixel applyFilter(String option) {
    IPixel pixel = new Pixel(this.red, this.green, this.blue, this.alpha, this.pos);
    int[] rgba;
    switch (option) {
      case "r":
        rgba = pixel.getValues();
        pixel.setColor(new int[]{rgba[0], 0, 0, rgba[3]});
        break;
      case "g":
        rgba = pixel.getValues();
        pixel.setColor(new int[]{0, rgba[1], 0, rgba[3]});
        break;
      case "b":
        rgba = pixel.getValues();
        pixel.setColor(new int[]{0, 0, rgba[2], rgba[3]});
        break;
      default:
        break;
    }

    return pixel;
  }

}