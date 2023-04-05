package controller;

import model.CollageModel;

/**
 * An interface that contains the features of the GUI.
 */
public interface CollageFeatures {
  /**
   * Saves an Image or Collage file based on the file path extensions.
   *
   * @param filePath - the location of the saved file
   */
  void saveFile(String filePath);

  /**
   * Loads a Collage file given the location of that file.
   *
   * @param filePath - the location of the Collage file
   */
  void loadProject(String filePath);

  /**
   * Quits the entire program.
   */
  void exitProgram();

  /**
   * Given a message, display it as a warning.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  void warn(String message, String title);

  /**
   * Given a message, display it as a plain message.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  void display(String message, String title);

  /**
   * Given a message, display it as an error.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  void errorMsg(String message, String title);

  /**
   * Create a new collage project with the given height and width.
   *
   * @param height - the height of the collage
   * @param width - the width of the collage
   */
  void newProject(int height, int width);

  /**
   * Sets the filter at the given layer.
   *
   * @param layer - name of the layer
   * @param filter - the filter name
   */
  void setFilter(String layer, String filter);

  /**
   * Adds a new layer in the project.
   *
   * @param layer - the layer name
   */
  void addLayer(String layer);

  /**
   * Loads an image to the given layer and its position.
   *
   * @param layer - the layer name
   * @param filePath - the location of the image
   * @param row - the row of the position
   * @param col - the column of the position
   */
  void addImageToLayer(String layer, String filePath, int row, int col);

  /**
   * Updates the model given a collage model.
   *
   * @param model - the model to replace
   */
  void updateModel(CollageModel model);
}
