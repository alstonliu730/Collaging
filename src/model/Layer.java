package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Layer implements IListOfPixel{
  private String name;
  private int height, width;
  private Pixel[][] matrix;
  private Filter filter;
  private List<Image> images;

  /**
   * Creates a layer of pixels with the given height and width.
   *
   * @param name
   * @param height
   * @param width
   * @throws IllegalArgumentException
   */
  public Layer(String name, int height, int width) throws IllegalArgumentException {
    if(Objects.isNull(name) || height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid Layer inputs!");
    }

    this.name = name;
    this.height = height; //row
    this.width = width; // column
    this.matrix = new Pixel[this.height][this.width];
    this.filter = new Filter("normal");
    this.images = new ArrayList<Image>();
    // creates the translucent image and set it as the background
    for (int i = 0; i < this.height; ++i) {
      for (int j = 0; j < this.width; ++j) {
        matrix[i][j] = new Pixel(255,255,255,0, new Posn(i,j)); // put max value in
      }
    }
  }

  /**
   * Converts the 2D Pixel matrix into a 1D List of Pixels.
   * Additionally, applies the current filter.
   *
   * @return
   */
  public List<Pixel> render() {
    List<Pixel> image = new ArrayList<Pixel>();
    for(Pixel[] row: this.matrix) {
      for(Pixel pixel: row) {
        image.add(pixel);
      }
    }

    //applies the current Filter
    image.forEach((pixel) -> this.filter.accept(pixel));
    return image;
  }

  /**
   * @param pixels
   * @return
   * @throws IllegalStateException
   */
  public Pixel[][] convertToMatrix(List<Pixel> pixels) throws IllegalArgumentException {
    if (Objects.isNull(pixels) || (this.height * this.width) != pixels.size()) {
      throw new IllegalArgumentException("Invalid matrix input!");
    }

    Pixel[][] new_matrix = new Pixel[this.height][this.width];
    for(Pixel p : pixels) {
      Posn position = p.getPos();
      new_matrix[position.getRow()][position.getCol()] = p;
    }

    return new_matrix;
  }

  /**
   * Returns the name of this layer.
   * @return - the name of this layer
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   * @return - the Pixel at the coordinate
   */
  public Pixel getPixel(Posn pos) {
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
  public void setPixel(Posn pos, Pixel p) {
    int row = pos.getRow();
    int col = pos.getCol();

    this.matrix[row][col] = p;
  }

  public void setMatrix(Pixel[][] pixels) throws IllegalArgumentException{
    if (Objects.isNull(pixels) || this.height != pixels.length
            || this.width != pixels[0].length) {
      throw new IllegalArgumentException("Invalid matrix input!");
    }

    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        this.matrix[i][j] = pixels[i][j];
      }
    }
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
   * Adds the pixels from the image to this layer.
   * - If image crosses the boundaries, then the image is cut off at the boundary.
   * - Adds the image to the list of images.
   *
   * @param img
   * @throws
   */
  public void addImage(Image img) throws IllegalArgumentException{
    // Add image to the list
    this.images.add(img);
    // Gather information of the image
    Posn position = img.getPos();
    int startRow, startRowImg, endRowImg;
    int startCol, startColImg, endColImg;

    // Determine boundaries of image array in the row (height)
    if (position.getRow() < 0) {
      startRowImg = Math.abs(position.getRow());
      endRowImg = img.getHeight();
      startRow = 0;
    } else if (position.getRow() > this.height) {
      return;
    } else {
      startRowImg = 0;
      endRowImg = img.getHeight();
      startRow = position.getRow();
    }

    // Determine boundaries of image array in the col (width)
    if (position.getCol() < 0) {
      startColImg = Math.abs(position.getCol());
      endColImg = img.getWidth();
      startRow = 0;
      startCol = 0;
    } else if (position.getCol() > this.width) {
      return; // the image will not render on the layer
    } else {
      startColImg = 0;
      endColImg = img.getWidth();
      startCol = position.getCol();
    }

    // Go through the given image
    for(int i = startRowImg; i < endRowImg; ++i) {
      for (int j = startColImg; j < endColImg; ++j) {
        // when the current column goes pass the width boundary
        if (startCol > this.width) {
          break;
        }
        // when the current row goes pass the height boundary
        if (startRow > this.height) {
          break;
        }
        Pixel curr = matrix[startRow][startCol];
        matrix[startRow][startCol] = img.getPixel(new Posn(i, j)).combine(curr);
        matrix[startRow][startCol].changePos(new Posn(startRow, startCol));
        startCol++;
      }
      startRow++;
    }
  }

  /**
   *
   * @param prev
   * @return
   * @throws IllegalArgumentException
   */
  public Layer combine(Layer prev) throws IllegalArgumentException{
    if(this.height != prev.getHeight() || this.width != prev.getWidth()) {
      throw new IllegalArgumentException("Layer dimensions do not match!");
    }

    // Render the layer as one list
    List<Pixel> rendered1 = this.render();
    List<Pixel> rendered2 = prev.render();

    // Combined layer
    Layer combined = new Layer("Combined", this.height, this.width);
    // Traverse the matrix of each layer
    List<Pixel> newLayer = new ArrayList<Pixel>();
    for(int i = 0; i < rendered1.size(); i++) {
      newLayer.add(rendered1.get(i).combine(rendered2.get(i)));
    }

    // set the new layer's matrix as this combined matrix
    combined.setMatrix(this.convertToMatrix(newLayer));

    return combined;
  }

  /**
   *
   * @return
   */
  public Layer applyFilter() {
    this.setMatrix(this.convertToMatrix(this.render()));
    return this;
  }

  /**
   *
   * @return
   */
  public Filter getFilter() {
    return this.filter;
  }
}
