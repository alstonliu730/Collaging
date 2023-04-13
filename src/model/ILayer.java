package model;

import java.util.List;

/**
 * An interface that represents the additional methods of a Layer.
 */
public interface ILayer extends IListOfPixel {

  /**
   * Returns the name of this layer.
   *
   * @return - the name of this layer
   */
  String getName();

  /**
   * Sets a new filter for the Layer.
   *
   * @param filter - the filter to change to
   */
  void setFilter(IFilter filter);

  /**
   * Returns the filter of this layer.
   *
   * @return - the filter object
   */
  IFilter getFilter();

  /**
   * Adds the pixels from the image to this layer.
   * - If image crosses the boundaries, then the image is cut off at the boundary.
   * - Adds the image to the list of images.
   *
   * @param img - the image to be added into the layer
   * @throws IllegalArgumentException - when the given image is null or the position is wrong
   */
  void addImage(IImage img) throws IllegalArgumentException;

  /**
   * Compresses the given previous layer with this layer.
   *
   * @param prev - the previous layer
   * @return - the combined layer
   * @throws IllegalArgumentException - when the previous layer has different dimensions
   */
  ILayer combine(ILayer prev) throws IllegalArgumentException;

  /**
   * Returns the Pixel at the given coordinate.
   *
   * @param pos - the x and y position of the pixel
   */
  void setPixel(IPosn pos, IPixel p);

  /**
   * Applies the filter to this layer.
   *
   * @param prev - the filtered previous layer
   * @return - the Layer with the applied filter
   */
  ILayer applyFilter(ILayer prev);

  /**
   * Gets the images in this layer.
   *
   * @return - the list of images in this layer.
   */
  public List<IImage> getImages();
}
