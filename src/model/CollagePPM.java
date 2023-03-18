package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a CollageModel with a PPM format.
 */
public class CollagePPM implements CollageModel{
  private final int height;
  private final int width;
  private final int maxValue;
  private List<Layer> layers;

  /**
   * Creates a CollagePPM model with the given height, width, and maxValue.
   *
   * @param height - the height of the model (rows)
   * @param width - the width of the model (columns)
   * @param maxValue - the max value of the channel (max value of rgba values)
   * @throws IllegalArgumentException - when the given inputs are zero or negative.
   */
  public CollagePPM(int height, int width, int maxValue) throws IllegalArgumentException{
    if (height <= 0 || width <= 0 || maxValue <= 0) {
      throw new IllegalArgumentException("Invalid PPM dimension!");
    }
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.layers = new ArrayList<Layer>();

    //create a white background layer.
    Pixel[][] background = new Pixel[this.height][this.width];
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        background[i][j] = new Pixel(this.maxValue,this.maxValue,
                this.maxValue, this.maxValue, new Posn(i,j));
      }
    }

    // create the layer object.
    Layer backgroundLayer = new Layer("background", this.height, this.width, this.maxValue);
    backgroundLayer.setMatrix(background);
    backgroundLayer.setFilter(new Filter("normal"));

    // add the layer object to the list.
    this.layers.add(backgroundLayer);
  }

  /**
   * Adds given layer to the list of layers.
   *
   * @param layer - layer to be added
   */
  public void addGivenLayer(Layer layer) {
    this.layers.add(layer);
  }

  /**
   * Returns the max value of this model.
   *
   * @return - the max value of the model.
   */
  public int getMax() {
    return this.maxValue;
  }

  /**
   * Returns the width of this model.
   *
   * @return - the width of the model
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of this model.
   *
   * @return - the height of the model
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the layer from the given name.
   *
   * @param name - the name of the layer
   * @return - the Layer object
   */
  public Layer getLayer(String name) throws IllegalStateException{
    for(Layer l: this.layers) {
      if(l.getName().equalsIgnoreCase(name)) {
        return l;
      }
    }

    throw new IllegalStateException("Invalid Layer name!");
  }

  /**
   * Adds a layer to this model given the name of the layer.
   *
   * @param layer - the name of the layer
   * @throws IllegalArgumentException - when the given layer name already exists in this model
   */
  public void addLayer(String layer) throws IllegalArgumentException {
    for(Layer l : this.layers) {
      if(l.getName().equalsIgnoreCase(layer)) {
        throw new IllegalArgumentException("Layer name already exists!");
      }
    }
    this.layers.add(new Layer(layer,this.height,this.width, this.maxValue));
  }

  /**
   * Adds an image to the given layer given the image and the position.
   *
   * @param layer - the given layer
   * @param img   - the image to be added
   * @param row     - the x-coordinate of the image
   * @param col     - the y-coordinate of the image
   */
  @Override
  public void addImageToLayer(Layer layer, IListOfPixel img, int row, int col)
          throws IllegalArgumentException {
    layer.addImage(new Image(img.convertToMatrix(img.render()), new Posn(row,col)));
  }

  /**
   * Sets the Filter of the given layer.
   *
   * @param layer  - the given layer
   * @param option - the filter type
   */
  public void setFilter(Layer layer, Filter option) {
    layer.setFilter(option);
  }

  /**
   * Save the layers as one layer.
   *
   * @return - the combined layers
   */
  public Layer saveImage() {
    // set the background as the first since it's the lowest
    Layer combined = this.layers.get(0);

    // go through the list of layers
    for(int i = 1; i < this.layers.size(); i ++) {
      combined = this.layers.get(i).combine(combined);
    }

    // returns the rendered combined layer
    return combined;
  }

  /**
   * Return the list of Layers with the applied filter.
   *
   * @return - the list of Layers
   */
  public List<Layer> renderLayers() {
    List<Layer> pixelLayers = new ArrayList<Layer>();
    for(Layer l: this.layers) {
      pixelLayers.add(l.applyFilter());
    }

    return pixelLayers;
  }

  /**
   * Return the model in a certain format.
   *
   * @return - the format of the PPM model as a String
   */
  public String ppmFormat() {
    String project = "";

    // Adds project information
    project += (this.width + " " + this.height + "\n");
    project += (this.maxValue + "\n");

    // Adds layer information
    for(Layer l : this.renderLayers()) {
      project += (l.getName() + " " + l.getFilter().getOption() + "\n");
      for(Pixel p: l.render()) {
        project += (p.ppmFormat() + " ");
      }
      project += "\n";
    }

    // return the string of the project
    return project;
  }
}
