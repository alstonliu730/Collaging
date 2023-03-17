package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.CollageModel;
import model.CollagePPM;
import model.Layer;
import model.Pixel;
import util.ImageUtil;
import view.TextView;

/**
 *
 */
public class CollageControllerImpl implements CollageController {
  private final Readable in;
  private final TextView out;
  private CollageModel model;
  private boolean quit = false;

  /**
   * Creates a controller object with the given model, input, and output.
   *
   * @param model - the model the controller will be based upon
   * @param in - the input of the user
   * @param out - the output from the controller
   * @throws IllegalArgumentException
   */
  public CollageControllerImpl(CollageModel model, Readable in, TextView out)
    throws IllegalArgumentException {
    if (Objects.isNull(model) || Objects.isNull(in) || Objects.isNull(out)) {
      throw new IllegalArgumentException("Invalid null input for controller.");
    }
    this.model = model;
    this.in = in;
    this.out = out;
  }

  /**
   * Runs the controller to take input and output using the model.
   */
  @Override
  public void runCollage() {
    // Prepare scanner
    Scanner s = new Scanner(this.in);

    while(!quit && s.hasNext()) {

    }
  }

  /**
   * Runs the given command based on the option
   * @param command - the command to execute
   */
  public void runCommands(String command, String... args) {
    switch (command) {
      case "quit":
        this.quit = true;
        break;
      case "new-project":
        try {
          int width = Integer.parseInt(args[0]);
          int height = Integer.parseInt(args[1]);
          // Create new project with h and w
          // Model should throw IllegalArgumentException for negative ints
          this.model = new CollagePPM(height, width, this.model.getMax());
        } catch (IllegalArgumentException e) {
          this.printMessage("Invalid input on creating new projects!");
          e.printStackTrace();
        }
        break;
      case "load-project":
        try {
          String file_path = args[0];
          // Open PPM file and pass it to model
          Layer new_layer = ImageUtil.readPPM(file_path);
          this.model = new CollagePPM(new_layer.getHeight(), new_layer.getWidth(), new_layer.getMax());
          this.model.addGivenLayer(new_layer);
        } catch (FileNotFoundException ime) {
          System.out.println("Invalid path or file not found");
        }

        break;
      case "save-project":
        // Save the entire model as a project text file
        break;
      case "add-layer":
        String layer_name = args[0];
        // Add layer to project
        try {
          this.model.addLayer(layer_name);
        } catch (IllegalArgumentException e) {
          this.printMessage("Layer already exists! Try again.\n");
        }
        break;
      case "add-image-to-layer":
        String layer = args[0];
        String image = args[1];
        int row = Integer.parseInt(args[2]);
        int col = Integer.parseInt(args[3]);
        // Check if layer name exists in project
        try {
          this.model.addImageToLayer(this.model.getLayer(layer),
                  ImageUtil.readPPM(image), row, col);
        } catch (FileNotFoundException e) {
          this.printMessage("Image not found!");
        } catch (IllegalArgumentException iae) {
          this.printMessage(iae.getMessage());
        }
        break;
      default:
        printMessage("Unknown command. Try again.");
        break;
    }
  }

  /**
   * Renders a message to the view with the given message.
   * @param message - the given message to transmit
   */
  public void printMessage(String message) {
    try {
      this.out.renderMessage(message);
    } catch (IOException e) {
      System.out.println("Failure in transmitting custom message!");
    }
  }
}
