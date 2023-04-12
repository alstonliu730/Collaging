package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.CollageModel;
import model.CollagePPM;
import model.Filter;
import view.gui.GUIView;

/**
 * A class that represents a controller for the GUI.
 */
public class CollageGUI implements CollageFeatures, CollageController {
  CollageModel model;
  GUIView view;

  /**
   * Creates a GUI controller with the given model and view.
   *
   * @param m - the model of the collage
   * @param v - the view of the colage
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
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the given command based on the option.
   *
   * @param command - the command to execute
   * @param args - arguments to input
   */
  @Override
  public void runCommands(String command, String... args) {
    switch (command) {
      case "quit": {
        this.exitProgram();
      }
      break;
      case "new-project": {
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        this.newProject(height, width);
      }
      break;
      case "load-project": {
        String filePath = args[0];
        // Open PPM file and pass it to model
        this.loadProject(filePath);
      }
      break;
      case "save-project":
      case "save-image": {
        String filePath = args[0];
        // Save the entire model as a project text file
        this.saveFile(filePath);
      }
        break;
      case "add-layer": {
        String layer_name = args[0];
        // Add layer to project
        this.addLayer(layer_name);
      }
      break;
      case "add-image-to-layer": {
        // get image and layer information
        String layer = args[0];
        String image = args[1];
        int row = Integer.parseInt(args[2]);
        int col = Integer.parseInt(args[3]);

        // Check if layer name exists in project
        this.addImageToLayer(layer, image, row, col);
      }
      break;
      // get file path to image
      // write a PPM file with the given file path
      case "set-filter": {
        // get layer name and filter option from user
        String layerName = args[0];
        String filterOption = args[1];

        // change the filter of the given layer name
        this.setFilter(layerName, filterOption);
      }
      break;
      default: {
        this.errorMsg("Unknown command. Try again.", "Unknown command!");
      }
      break;
    }
  }

  /**
   * Saves an Image or Collage file based on the file path extensions.
   *
   * @param filePath - the location of the saved file
   */
  @Override
  public void saveFile(String filePath) {
    if (filePath.contains(".collage")) {
      model.writeProject(this.model, filePath);
    } else {
      try {
        model.writeImage(this.model.saveImage(), filePath);
      } catch (IOException e) {
        this.errorMsg(e.getMessage(), "Error in Saving Image");
      }
    }
  }

  /**
   * Loads a Collage file given the location of that file.
   *
   * @param filePath - the location of the Collage file
   */
  @Override
  public void loadProject(String filePath) {
    try {
      this.model = model.readProject(filePath);
    } catch (FileNotFoundException e) {
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
  public void display(String message, String title) {
    this.view.echoMessage(message, title, JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * Given a message, display it as an error.
   *
   * @param message - the displayed message
   * @param title - the title of the message
   */
  public void errorMsg(String message, String title) {
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
              model.readImages(filePath), row, col);
    } catch (FileNotFoundException e) {
      this.errorMsg("File could not be found. Try again!", "File not Found");
    } catch (IllegalArgumentException e) {
      this.warn("Inputs on adding an image to the layer are invalid.", "Invalid input");
    } catch (IOException e) {
      throw new RuntimeException(e);
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
