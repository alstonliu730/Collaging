package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Image implements IListOfPixel{
  private int height, width;
  private Pixel[][] image;
  private Posn pos;

  /**
   *
   * @param height
   * @param width
   * @param pos
   * @throws IllegalArgumentException
   */
  public Image(int height, int width, Posn pos) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || Objects.isNull(pos)) {
      throw new IllegalArgumentException("Invalid Image input!");
    }

    this.height = height;
    this.width = width;
    this.pos = pos;
    this.image = new Pixel[this.height][this.width];
  }

  /**
   *
   * @param img
   * @param pos
   */
  public Image(Pixel[][] img, Posn pos) throws IllegalArgumentException {
    if(Objects.isNull(img) || Objects.isNull(pos)) {
      throw new IllegalArgumentException("Invalid input!");
    }
    this.height = img.length;
    this.width = img[0].length;
    this.pos = pos;
    this.image = img;
  }

  /**
   * Converts 2D array into 1D array of Pixel.
   *
   * @return
   */
  public List<Pixel> render() {
    List<Pixel> pixels = new ArrayList<Pixel>();

    // fill the list o
    for(Pixel[] row: this.image) {
      for(Pixel pixel: row) {
        pixels.add(pixel);
      }
    }

    return pixels;
  }

  /**
   *
   * @param pixels
   * @return
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
  @Override
  public Pixel getPixel(Posn pos) {
    return null;
  }

  /**
   * Returns the position of the image.
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
}
