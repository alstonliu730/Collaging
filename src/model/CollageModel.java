package model;

import java.util.List;

public interface CollageModel {

  /**
   * Load model with given height, width, and max value
   *
   * @param height
   * @param width
   * @param maxValue
   * @throws IllegalArgumentException
   */
  void startModel(int height, int width, int maxValue) throws IllegalArgumentException;

  /**
   * Adds given layer to the list of layers.
   *
   * @param layer - layer to be added
   */
  void addGivenLayer(Layer layer);

  /**
   * Removes the layer with the given name associated.
   * If it doesn't exist, it does nothing.
   *
   * @param name - the name of the layer
   */
  void removeLayer(String name);

  /**
   * Returns the max value of this model.
   *
   * @return - the max value of the model.
   */
  int getMax();

  /**
   * Returns the width of this model.
   *
   * @return - the width of the model
   */
  int getWidth();

  /**
   * Returns the height of this model.
   *
   * @return - the height of the model
   */
  int getHeight();

  /**
   * Returns the layer from the given name
   *
   * @param name - the name of the layer
   * @return - the Layer object
   */
  Layer getLayer(String name) throws IllegalStateException;

  /**
   * Adds a layer to this model given the name of the layer.
   *
   * @param layer - the name of the layer
   * @throws IllegalArgumentException - when the given layer name already exists in this model
   */
  void addLayer(String layer) throws IllegalArgumentException;

  /**
   * Adds an image to the given layer given the image and the position.
   *
   * @param layer - the given layer
   * @param img   - the image to be added
   * @param row   - the x-coordinate of the image
   * @param col   - the y-coordinate of the image
   */
  void addImageToLayer(Layer layer, IListOfPixel img, int row, int col);

  /**
   * Sets the Filter of the given layer.
   *
   * @param layer  - the name of the given layer
   * @param option - the filter type
   */
  void setFilter(String layer, Filter option);

  /**
   * Save the layers as one layer
   *
   * @return - the combined layers
   */
  Layer saveImage();

  /**
   * Return the list of Layers with the applied filter.
   *
   * @return - the list of Layers
   */
  List<Layer> renderLayers();

  /**
   * Return the model in a certain format.
   *
   * @return - the format of the PPM model as a String
   */
  String toString();
}
