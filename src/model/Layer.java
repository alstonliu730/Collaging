package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A class that represents a Layer.
 */
public class Layer implements IListOfPixel{
  private String name;
  private int height, width;
  private IPixel[][] matrix;
  private Filter filter;
  private List<Image> images;
  private int maxValue;

  /**
   * Creates a layer of pixels with the given height and width.
   *
   * @param name - the name of this layer
   * @param height - the height of the layer
   * @param width - the width of the layer
   * @param maxValue - the max value of the channels
   * @throws IllegalArgumentException - when the dimensions are less than or equal to zero OR
   *                                    when the name is null
   */
  public Layer(String name, int height, int width, int maxValue) throws IllegalArgumentException {
    if(Objects.isNull(name) || height <= 0 || width <= 0 || maxValue <= 0) {
      System.out.println("Height: " + height);
      System.out.println("Width: " + width);
      System.out.println("Max: " + maxValue);
      System.out.println("Name " + name);
      throw new IllegalArgumentException("Invalid Layer inputs!");
    }
    this.name = name;
    this.height = height; //row
    this.width = width; // column
    this.matrix = new Pixel[this.height][this.width];
    this.filter = Filter.NORMAL;
    this.images = new ArrayList<Image>();
    this.maxValue = maxValue;

    // creates the translucent image and set it as the background
    for (int i = 0; i < this.height; ++i) {
      for (int j = 0; j < this.width; ++j) {
        matrix[i][j] = new Pixel(this.maxValue,this.maxValue, this.maxValue,
                0, this.maxValue, new Posn(i,j));
      }
    }
  }

  /**
   * Returns the name of this layer.
   *
   * @return - the name of this layer
   */
  public String getName() {
    return this.name;
  }
  /**
   * Returns the height of this IListOfPixel
   *
   * @return - the height of this object
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the width of this IListOfPixel
   *
   * @return - the width of this object
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Sets a new filter for the Layer.
   */
  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  /**
   * Returns the filter of this layer.
   *
   * @return - the filter object
   */
  public Filter getFilter() {
    return this.filter;
  }

  /**
   * Returns the maximum value of the channels in this layer.
   *
   * @return - the max value of the channels
   */
  public int getMax() {
    return this.maxValue;
  }

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public IPixel getPixel(Posn pos) {
    int row = pos.getRow();
    int col = pos.getCol();

    return this.matrix[row][col];
  }

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public void setPixel(Posn pos, IPixel p) {
    int row = pos.getRow();
    int col = pos.getCol();

    this.matrix[row][col] = p;
  }

  /**
   * Returns the Pixels in the matrix as a 1D array
   *
   * @return - a list of the current Pixels
   */
  public List<IPixel> render() {
    List<IPixel> l = new ArrayList<IPixel>();

    // add each pixel of the layer
    for(IPixel[] row: this.matrix) {
      for(IPixel pixel: row) {
        l.add(pixel);
      }
    }

    // returns the rendered layer
    return l;
  }

  /**
   * Sets the matrix of this layer with the given matrix of pixels.
   *
   * @param pixels - the given matrix of pixels
   * @throws IllegalArgumentException - If the matrix of pixels is not the same dimension
   */
  public void setMatrix(IPixel[][] pixels) throws IllegalArgumentException{
    if (Objects.isNull(pixels) || this.height != pixels.length
            || this.width != pixels[0].length) {
      throw new IllegalArgumentException("Invalid matrix input!");
    }

    // Populate the current matrix
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        this.setPixel(new Posn(i,j), pixels[i][j]);
      }
    }
  }

  /**
   * Adds the pixels from the image to this layer.
   * - If image crosses the boundaries, then the image is cut off at the boundary.
   * - Adds the image to the list of images.
   *
   * @param img - the image to be added into the layer
   * @throws IllegalArgumentException - when the given image is null or the position is wrong
   */
  public void addImage(Image img) throws IllegalArgumentException{
    if(Objects.isNull(img)) {
      throw new IllegalArgumentException("No input on img!");
    }
    // Add image to the list
    this.images.add(img);

    // Gather information of the image
    Posn position = img.getPos();

    for(int i = 0; i < img.getHeight(); ++i) {
      for(int j = 0; j < img.getWidth(); ++j) {
        int row = (position.getRow() + i) < this.height ? (position.getRow() + i) : -1;
        int col = (position.getCol() + j) < this.width ? (position.getCol() + j) : -1;

        // if the row or col is not more than the canvas
        if(!(row < 0 || col < 0)) {
          // create the new pixel
          IPixel newPixel = img.getPixel(new Posn(i,j));
          newPixel = newPixel.combine(this.matrix[row][col]);
          newPixel.changePos(new Posn(row,col));

          // set the pixel at the row and column
          this.matrix[row][col] = newPixel;
        }
      }
    }
  }

  /**
   * Compresses the given previous layer with this layer.
   *
   * @param prev - the previous layer
   * @return - the combined layer
   * @throws IllegalArgumentException - when the previous layer has different dimensions
   */
  public Layer combine(Layer prev) throws IllegalArgumentException{
    if(this.height != prev.getHeight() || this.width != prev.getWidth()) {
      throw new IllegalArgumentException("Layer dimensions do not match!");
    }

    // Combined layer with the filter on it
    Layer compressed = new Layer("combined", this.height, this.width, this.maxValue);

    // Traverse the matrix of each layer
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        IPixel p1 = this.getPixel(new Posn(i,j));
        IPixel p2 = prev.getPixel(new Posn(i,j));
        compressed.setPixel(new Posn(i,j), p1.combine(p2));
      }
    }

    // Return the new combined layer
    return compressed;
  }

  /**
   * Applies the filter to this layer
   *
   * @param prev - the filtered previous layer
   * @return - the Layer with the applied filter
   */
  public Layer applyFilter(Layer prev) {
    if(Objects.isNull(prev)) {
      return this.applyBackgroundFilter();
    }
    Layer filtered = new Layer(this.name, this.height, this.width, this.maxValue);
    filtered.setFilter(this.filter);

    // apply the filter to each pixel
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        Posn p = new Posn(i,j);
        filtered.setPixel(p, this.filter.apply(this.getPixel(p), prev.getPixel(p)));
      }
    }

    // return the filtered layer
    return filtered;
  }

  /**
   * Applies the filter to this layer only.
   *
   * @return - the modified layer with the filter
   */
  private Layer applyBackgroundFilter() {
    // apply the filter to this Layer
    for(IPixel p: this.render()) {
      this.setPixel(p.getPos(), this.filter.apply(p));
    }
    return this;
  }
}
