package controller;

import java.io.FileNotFoundException;

import javax.swing.*;

import model.CollageModel;
import model.CollagePPM;
import model.Filter;
import util.ImageUtil;
import view.gui.GUIView;

public class CollageGUI implements CollageFeatures, CollageController {
  CollageModel model;
  GUIView view;

  /**
   *
   * @param m
   * @param v
   */
  public CollageGUI(CollageModel m, GUIView v) {
    this.model = m;
    this.view = v;
  }

  /**
   * Runs the controller to take input and output using the model.
   */
  @Override
  public void runCollage() {
    this.view.addFeatures(this);

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }
  }

  /**
   * Runs the given command based on the option
   *
   * @param command - the command to execute
   * @param args
   */
  @Override
  public void runCommands(String command, String... args) {}

  /**
   * Saves an Image or Collage file based on the file path extensions.
   *
   * @param filePath - the location of the saved file
   */
  @Override
  public void saveFile(String filePath) {
    if(filePath.contains(".ppm")) {
      ImageUtil.writePPM(this.model.saveImage(), filePath);
    } else if (filePath.contains(".collage")) {
      ImageUtil.writeProject(this.model, filePath);
    } else {
      this.errorMsg("Unable to save file with this extension!", "Unknown File Extension");
    }
  }

  /**
   * Loads a Collage file given the location of that file.
   *
   * @param filePath - the location of the Collage file
   */
  @Override
  public void loadProject(String filePath) {
    try{
      this.model = ImageUtil.readProject(filePath);
    } catch(FileNotFoundException e) {
      this.errorMsg("File not found!", "Unknown File");
    }
    this.view.refresh(this.model);
  }
  /**
   * Sets the filter at the given layer.
   *
   * @param layer - name of the layer
   * @param filter - the filter name
   */
  public void setFilter(String layer, String filter) {
    try {
      this.model.setFilter(layer, Filter.findByValue(filter));
    } catch (IllegalStateException e) {
      this.warn(e.getMessage(), "Unknown Layer");
    } catch (IllegalArgumentException e) {
      this.warn(e.getMessage(), "Unknown Filter");
    }
    this.view.refresh(this.model);
  }
  /**
   * Quits the entire program.
   */
  @Override
  public void exitProgram() {
    this.view.quit();
  }

  /**
   * Given a message, display it as a warning.
   *
   * @param message - the displayed message
   */
  public void warn(String message, String title) {
    this.view.echoMessage(message, title, JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Given a message, display it as a plain message.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  public void display(String message, String title){
    this.view.echoMessage(message, title, JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Given a message, display it as an error.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  public void errorMsg(String message, String title){
    this.view.echoMessage(message, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Create a new collage project with the given height and width.
   *
   * @param height - the height of the collage
   * @param width  - the width of the collage
   */
  @Override
  public void newProject(int height, int width) {
    int max = this.model.getMax();
    this.model = new CollagePPM();
    this.model.startModel(height, width, max);
    this.view.refresh(this.model);
  }

  /**
   * Adds a new layer in the project.
   *
   * @param layer - the layer name
   */
  @Override
  public void addLayer(String layer) {
    try {
      this.model.addLayer(layer);
    } catch (IllegalArgumentException e) {
      this.warn(e.getMessage(), "Invalid Layer");
      e.printStackTrace();
    }
    this.view.refresh(this.model);
  }

  /**
   * Loads an image to the given layer and its position.
   *
   * @param layer    - the layer name
   * @param filePath - the location of the image
   * @param row      - the row of the position
   * @param col      - the column of the position
   */
  @Override
  public void addImageToLayer(String layer, String filePath, int row, int col) {
    try {
      this.model.addImageToLayer(this.model.getLayer(layer),
              ImageUtil.readPPM(filePath), row, col);
    } catch (FileNotFoundException e) {
      this.errorMsg("File could not be found. Try again!", "File not Found");
    } catch (IllegalArgumentException e) {
      this.warn("Inputs on adding an image to the layer are invalid.", "Invalid input");
    }
    this.view.refresh(this.model);
  }

  /**
   * Updates the model given a collage model.
   *
   * @param model - the model to replace
   */
  @Override
  public void updateModel(CollageModel model) {
    this.model = model;
  }
}
