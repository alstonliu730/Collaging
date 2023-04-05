package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.PixelArrayUtil;

/**
 * A class that represents a CollageModel with a PPM format.
 */
public class CollagePPM implements CollageModel{
  private int height;
  private int width;
  private int maxValue;
  private List<Layer> layers;

  /**
   * Creates a CollagePPM model and instantiates necessary items.
   */
  public CollagePPM()  {
    this.layers = new ArrayList<Layer>();
    this.maxValue = 255;
  }

  /**
   * Load model with given height, width, and max value.
   *
   * @param height - the height of the model (rows)
   * @param width - the width of the model (columns)
   * @param maxValue - the max value of the channel (max value of rgba values)
   * @throws IllegalArgumentException - when the given inputs are zero or negative.
   */
  @Override
  public void startModel(int height, int width, int maxValue) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || maxValue <= 0) {
      throw new IllegalArgumentException("Invalid PPM dimension!");
    }
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;

    //create a white background layer.
    IPixel[][] background = new Pixel[this.height][this.width];
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        background[i][j] = new Pixel(this.maxValue, this.maxValue,
                this.maxValue, this.maxValue, new Posn(i,j));
      }
    }

    // create the background layer object with a white background.
    Layer backgroundLayer = new Layer("background", this.height, this.width, this.maxValue);
    backgroundLayer.setMatrix(background);
    backgroundLayer.setFilter(Filter.NORMAL);

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
   * Removes the layer with the given name associated.
   * If it doesn't exist, it does nothing.
   *
   * @param name - the name of the layer
   */
  public void removeLayer(String name) {
    for(Iterator<Layer> iter = this.layers.iterator(); iter.hasNext();) {
      Layer l = iter.next();
      if(l.getName().equals(name)) {
        iter.remove();
      }
    }
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

    throw new IllegalStateException("Invalid Layer name! Name: " + name);
  }

  /**
   * Adds a layer to this model given the name of the layer.
   *
   * @param layer - the name of the layer
   * @throws IllegalArgumentException - when the given layer name already exists in this model
   */
  public void addLayer(String layer) throws IllegalArgumentException {
    Layer newLayer = new Layer(layer, this.height, this.width, this.maxValue);
    for(Layer l : this.layers) {
      if(l.getName().equalsIgnoreCase(layer)) {
        throw new IllegalArgumentException("Layer name already exists!");
      }
    }
    this.layers.add(newLayer);
  }

  /**
   * Adds an image to the given layer given the image and the position.
   *
   * @param layer - the given layer
   * @param img   - the image to be added
   * @param row     - the x-coordinate of the image
   * @param col     - the y-coordinate of the image
   */
  public void addImageToLayer(Layer layer, IListOfPixel img, int row, int col)
          throws IllegalArgumentException {
    layer.addImage(new Image(PixelArrayUtil.convertToMatrix(img.render(),
            img.getHeight(), img.getWidth()), new Posn(row,col)));
  }

  /**
   * Sets the Filter of the given layer.
   *
   * @param layer  - the name of the given layer
   * @param option - the filter type
   * @throws IllegalStateException - when the given layer is not found
   */
  public void setFilter(String layer, Filter option) throws IllegalStateException{
    for(Layer l: this.layers) {
      if(l.getName().equalsIgnoreCase(layer)) {
        l.setFilter(option);
        return;
      }
    }

    throw new IllegalStateException("Invalid Layer name! Name: " + layer);
  }

  /**
   * Save the layers as one layer.
   *
   * @return - the combined layers
   */
  public Layer saveImage() {
    // set the background as the first since it's the lowest
    List<Layer> filteredLayers = this.renderLayers();
    Layer combined = filteredLayers.get(0);

    // go through the list of layers
    for(int i = 1; i < filteredLayers.size(); i++) {
      combined = filteredLayers.get(i).combine(combined);
    }

    // returns the rendered combined layer
    return combined;
  }

  /**
   * Return the current list of Layers with the applied filter.
   *
   * @return - the list of Layers
   */
  public List<Layer> renderLayers() {
    // create a copy of the current layers
    List<Layer> renderedLayers = new ArrayList<Layer>();
    Layer prev = this.layers.get(0); // get the background as the first layer
    renderedLayers.add(prev.applyFilter(null)); // add the background

    // apply the filter to each layer
    if (this.layers.size() > 1) {
      for (int i = 1; i < this.layers.size(); i++) {
        Layer curr = this.layers.get(i);
        renderedLayers.add(curr.applyFilter(prev));
        prev = renderedLayers.get(i);
      }
    }
    return renderedLayers;
  }

  /**
   * Return the model in a certain format.
   *
   * @return - the format of the PPM model as a String
   */
  public String toString() {
    String project = "";

    // Adds project information
    project += (this.width + " " + this.height + "\n");
    project += (this.maxValue + "\n");

    // Adds layer information
    for(Layer l : this.renderLayers()) {
      project += (l.getName() + " " + l.getFilter().getOption() + "\n");
      for(IPixel p: l.render()) {
        project += (p.rgbaString() + " ");
      }
      project += "\n";
    }

    // return the string of the project
    return project;
  }
}
