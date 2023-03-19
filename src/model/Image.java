package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import util.PixelArrayUtil;

/**
 * A class that represents an Image.
 */
public class Image implements IListOfPixel{
  private int height, width;
  private Pixel[][] image;
  private Posn pos;
  private int maxValue;

  /**
   * Creates an Image object with the given height, width, and position.
   *
   * @param height - the height of the image
   * @param width - the width of the image
   * @param pos - the position of the image in the layer
   * @throws IllegalArgumentException - when the input is less than or equal to zero
   */
  public Image(int height, int width, Posn pos) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || Objects.isNull(pos)) {
      throw new IllegalArgumentException("Invalid Image input!");
    }

    this.height = height;
    this.width = width;
    this.pos = pos;
    this.image = new Pixel[this.height][this.width];
    this.maxValue = 255;
  }

  /**
   * Creates an Image object with the given height, width, max value, and position.
   *
   * @param height - the height of the image
   * @param width - the width of the image
   * @param pos - the position of the image in the layer
   * @throws IllegalArgumentException - when the input is less than or equal to zero
   */
  public Image(int height, int width, int maxValue, Posn pos) throws IllegalArgumentException {
    this(height, width, pos);
    if (maxValue <= 0) {
      throw new IllegalArgumentException("Invalid max value input!");
    }
    this.maxValue = maxValue;
  }

  /**
   * Creates an Image object with the given matrix and position.
   *
   * @param img - the matrix of pixels
   * @param pos - the position of the image in the layer
   * @throws IllegalArgumentException - when the input is less than or equal to zero
   */
  public Image(Pixel[][] img, Posn pos) throws IllegalArgumentException {
    if(Objects.isNull(img) || Objects.isNull(pos) || Arrays.asList(img).isEmpty()) {
      throw new IllegalArgumentException("Invalid input!");
    }
    this.height = img.length;
    this.width = img[0].length;
    this.pos = pos;
    this.image = img;
    this.maxValue = PixelArrayUtil.getMaxValue(this.image);
  }

  /**
   * Returns the current Pixels in the matrix as a 1D array.
   *
   * @return - a list of the current Pixels
   */
  public List<Pixel> render() {
    List<Pixel> pixels = new ArrayList<Pixel>();
    // fill the list of pixels
    for(Pixel[] row: this.image) {
      for(Pixel pixel: row) {
        pixels.add(pixel);
      }
    }

    return pixels;
  }

  /**
   * Converts the given list of Pixel into a matrix of Pixels
   *
   * @param pixels - the List of Pixels to be converted
   * @return - converted matrix of Pixels
   * @throws IllegalStateException - When the size is not the same
   */
  public Pixel[][] convertToMatrix(List<Pixel> pixels) throws IllegalStateException{
    if(pixels.size() != (this.height * this.width)) {
      throw new IllegalStateException("Size of image is not the same as the list!");
    }
    Pixel[][] matrix = new Pixel[this.height][this.width];
    for(Pixel pixel : pixels) {
      int row = pixel.getPos().getRow();
      int col = pixel.getPos().getCol();

      matrix[row][col] = pixel;
    }

    return matrix;
  }

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public Pixel getPixel(Posn pos) throws IllegalArgumentException{
    int row = pos.getRow();
    int col = pos.getCol();

    try {
      return image[row][col];
    }
    catch (NullPointerException e) {
      throw new IllegalArgumentException("Image is not initialized!");
    }
  }

  /**
   * Returns the position of this image.
   */
  public Posn getPos() {
    return this.pos;
  }

  /**
   * Returns the height of this image
   *
   * @return
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the width of this image
   *
   * @return
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the max value of this image.
   *
   * @return - the max value of each Pixel
   */
  public int getMax() {
    return this.maxValue;
  }
}
