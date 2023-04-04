package model;

import java.util.Objects;

import util.PixelArrayUtil;
import util.RepresentationConverter;

/**
 * A class that represents a Pixel that contains:
 *  - (r, g, b, a) channels
 *  - max value of channels
 *  - the position
 */
public class Pixel implements IPixel{
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
  public String toString() {
    return String.format("%d %d %d", this.red, this.green, this.blue);
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
    assert(this.pos.equals(prev.getPos()));
    // get HSL representation of the pixel
    double[] currHSL = RepresentationConverter.convertRGBtoHSL(this.red, this.green, this.blue);
    int[] prevRGBA = prev.getValues();
    double[] prevHSL = RepresentationConverter.convertRGBtoHSL(prevRGBA[0],
            prevRGBA[1], prevRGBA[2]);
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
    assert(this.pos.equals(prev.getPos()));
    // get HSL representation of the pixel
    double[] currHSL = RepresentationConverter.convertRGBtoHSL(this.red, this.green, this.blue);
    int[] prevRGBA = prev.getValues();
    double[] prevHSL = RepresentationConverter.convertRGBtoHSL(prevRGBA[0],
            prevRGBA[1], prevRGBA[2]);
    // use only the lightness value of the hsl representation
    double l = currHSL[2];
    double dl = prevHSL[2];

    // Use multiply method to brighten a pixel
    double newL = l * dl;
    int[] newRGB = RepresentationConverter.convertHSLtoRGB(currHSL[0], currHSL[1], newL);

    // return the new pixel
    return new Pixel(newRGB[0], newRGB[1], newRGB[2], this.alpha, this.maxValue, this.pos);
  }

  /**
   * Inverts the color using the previous pixel.
   *
   * @param prev - the previous Pixel
   */
  @Override
  public IPixel invert(IPixel prev) {
    assert(this.pos.equals(prev.getPos()));
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
      default:
        break;
    }

    return this;
  }

}

/* OLD BRIGHTEN CODE
   * Brightens the color by a factor of luma.
   * - The luma formula is: luma = 0.2126r + 0.7512g + 0.0722b
   * - Then modify the values of this color
   *
  public void brighten() {
    float luma = (float) (0.2126 * this.red + 0.7512 * this.green + 0.0722 * this.blue);
    this.red = Math.round(this.red + luma) <= this.maxValue ?
            Math.round(this.red + luma) : this.maxValue;
    this.green = Math.round(this.green + luma) <= this.maxValue ?
            Math.round(this.green + luma) : this.maxValue;
    this.blue = Math.round(this.blue + luma) <= this.maxValue ?
            Math.round(this.blue + luma) : this.maxValue;
  } */

  /*
   * Darkens the color by a factor of luma.
   * - The luma formula is: luma = 0.2126r + 0.7512g + 0.0722b
   * - Then modify the values of this color
  public void darken() {
    float luma = (float) (0.2126 * this.red + 0.7512 * this.green + 0.0722 * this.blue);
    this.red = Math.round(this.red - luma) >= 0 ? Math.round(this.red - luma) : 0;
    this.green = Math.round(this.green - luma) >= 0 ? Math.round(this.green - luma) : 0;
    this.blue = Math.round(this.blue - luma) >= 0 ? Math.round(this.blue - luma) : 0;
  }
  */