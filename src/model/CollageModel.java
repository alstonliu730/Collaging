package model;

import java.util.List;

public interface CollageModel {

  /**
   * Returns the width of this model.
   *
   * @return - the width of the model
   */
  public int getWidth();

  /**
   * Returns the height of this model.
   *
   * @return - the height of the model
   */
  public int getHeight();

  /**
   * Returns the layer from the given name
   * @param name - the name of the layer
   * @return - the Layer object
   */
  public Layer getLayer(String name) throws IllegalStateException;

  /**
   * Adds a layer to this model given the name of the layer.
   *
   * @param layer - the name of the layer
   */
  public void addLayer(String layer);

  /**
   * Adds an image to the given layer given the image and the position
   *
   * @param layer - the given layer
   * @param img - the image to be added
   * @param row - the x-coordinate of the image
   * @param col - the y-coordinate of the image
   */
  public void addImageToLayer(Layer layer, IListOfPixel img, int row, int col);

  /**
   * Sets the Filter of the given layer
   *
   * @param layer - the given layer
   * @param option - the filter type
   */
  public void setFilter(Layer layer, Filter option);

  /**
   * Save the image
   */
  public List<Pixel> saveImage();
}
