package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollagePPM implements CollageModel{
  private final int height;
  private final int width;
  private final int MAX_VALUE;
  private List<Layer> layers;

  /**
   *
   * @param height
   * @param width
   * @throws IllegalArgumentException
   */
  public CollagePPM(int height, int width, int MAX_VALUE) throws IllegalArgumentException{
    if (height <= 0 || width <= 0 || MAX_VALUE <= 0) {
      throw new IllegalArgumentException("Invalid PPM dimension!");
    }
    this.height = height;
    this.width = width;
    this.MAX_VALUE = MAX_VALUE;
    this.layers = new ArrayList<Layer>();

    //create a white background layer.
    Pixel[][] background = new Pixel[this.height][this.width];
    for(int i = 0; i < this.height; i++) {
      for(int j = 0; j < this.width; j++) {
        background[i][j] = new Pixel(this.MAX_VALUE,this.MAX_VALUE,
                this.MAX_VALUE, this.MAX_VALUE, new Posn(i,j));
      }
    }

    // create the layer object.
    Layer backgroundLayer = new Layer("background", this.height, this.width, this.MAX_VALUE);
    backgroundLayer.setMatrix(background);
    backgroundLayer.setFilter(new Filter("normal"));

    // add the layer object to the list.
    this.layers.add(backgroundLayer);
  }

  /**
   *
   * @param layer
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
    return this.MAX_VALUE;
  }
  /**
   * Returns the width of this model.
   *
   * @return - the width of the model
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of this model.
   *
   * @return - the height of the model
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns the layer from the given name
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
  @Override
  public void addLayer(String layer) throws IllegalArgumentException {
    for(Layer l : this.layers) {
      if(l.getName().equalsIgnoreCase(layer)) {
        throw new IllegalArgumentException("Layer name already exists!");
      }
    }
    this.layers.add(new Layer(layer,this.height,this.width, this.MAX_VALUE));
  }

  /**
   * Adds an image to the given layer given the image and the position
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
   * Sets the Filter of the given layer
   *
   * @param layer  - the given layer
   * @param option - the filter type
   */
  public void setFilter(Layer layer, Filter option) {
    layer.setFilter(option);
  }

  /**
   * Save all the layers as one List of Pixels
   * @return - the combined layers of pixels
   */
  @Override
  public List<Pixel> saveImage() {
    // set the background as the first since it's the lowest
    Layer combined = this.layers.get(0);

    // go through the list of layers
    for(int i = 1; i < this.layers.size(); i ++) {
      combined = this.layers.get(i).combine(combined);
    }

    // returns the rendered combined layer
    return combined.render();
  }

  /**
   * Return the list of Layers with the applied filter.
   * @return
   */
  public List<Layer> renderLayers() {
    List<Layer> pixelLayers = new ArrayList<Layer>();
    for(Layer l: this.layers) {
      pixelLayers.add(l.applyFilter());
    }

    return pixelLayers;
  }
}
