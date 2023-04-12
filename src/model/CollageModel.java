package model;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * An interface that represents the model of the Collage.
 */
public interface CollageModel {

  /**
   * Load model with given height, width, and max value.
   *
   * @param height   - the height of the model (rows)
   * @param width    - the width of the model (columns)
   * @param maxValue - the max value of the channel (max value of rgba values)
   * @throws IllegalArgumentException - when the given inputs are zero or negative.
   */
  void startModel(int height, int width, int maxValue) throws IllegalArgumentException;

  /**
   * Adds given layer to the list of layers.
   *
   * @param layer - layer to be added
   */
  void addGivenLayer(ILayer layer);

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
   * Returns the layer from the given name.
   *
   * @param name - the name of the layer
   * @return - the Layer object
   */
  ILayer getLayer(String name) throws IllegalStateException;

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
  void addImageToLayer(ILayer layer, IListOfPixel img, int row, int col);

  /**
   * Sets the Filter of the given layer.
   *
   * @param layer  - the name of the given layer
   * @param option - the filter type
   */
  void setFilter(String layer, IFilter option);

  /**
   * Save the layers as one layer.
   *
   * @return - the combined layers
   */
  ILayer saveImage();

  /**
   * Return the list of Layers with the applied filter.
   *
   * @return - the list of Layers
   */
  List<ILayer> renderLayers();

  /**
   * Return the model in a certain format.
   *
   * @return - the format of the PPM model as a String
   */
  String toString();

  /**
   * Read an image file in the PPM format and return the image.
   *
   * @param filename the path of the file.
   * @return - the image
   * @throws FileNotFoundException if the given file is not found
   */
  IListOfPixel readPPM(String filename) throws FileNotFoundException;

  /**
   * Reads a Collage project file to be loaded in.
   *
   * @param filename - the location of the file
   * @return - the model from the given project file
   * @throws FileNotFoundException - could not find the file given the file path
   */
  CollageModel readProject(String filename) throws FileNotFoundException;

  /**
   * Reads an image file that are supported by Java and returns the image.
   *
   * @param filename - the file location of the image
   * @return - the image
   * @throws IOException - when there's
   */
  IListOfPixel readImages(String filename) throws IOException;

  /**
   * Writes a project file given a model and the file path.
   *
   * @param model    - the given model
   * @param filename - the saved location of the file
   */
  void writeProject(CollageModel model, String filename);

  /**
   * Writes an image file given an IListOfPixel object and the file path.
   *
   * @param img - the compressed image object
   * @param filename - the saved location of the file
   * @return - returns if the image is saved properly
   * @throws IOException - Error in writing to an image file
   * @throws IllegalArgumentException - When the input is null or
   *                                    when the file extension is not supported
   */
  boolean writeImage(IListOfPixel img, String filename)
          throws IOException, IllegalArgumentException;

  /**
   * Creates a rendered image object from the given IListOfPixel object.
   *
   * @param img - the IListOfPixel object
   * @return - the rendered image
   */
  BufferedImage createImageObject(IListOfPixel img);
<<<<<<< HEAD

  /**
   * Removes the comment lines from a file. Comments start with a '#'.
   * Returns the file without the comments.
   *
   * @param filename path of the file
   * @return a Readable object with the contents of the file without comments
   * @throws IllegalArgumentException - if a null arg is passed
   * @throws FileNotFoundException    - if the file is not found
   */
  Readable removeComments(String filename)
          throws IllegalArgumentException, FileNotFoundException;
=======
>>>>>>> 772009afcc12a55b7095db166b7b62d89299a3f0
}
