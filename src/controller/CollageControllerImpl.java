package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import model.CollageModel;
import model.CollagePPM;
import model.Filter;
import util.ImageUtil;
import view.TextView;

/**
 * A class that represents the controller
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
   * @param in    - the input of the user
   * @param out   - the output from the controller
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
    // Print welcome messages
    this.printMessage("Welcome to Collaging!\n");

    // Loop until the user quits
    while (!quit && s.hasNext()) {
      this.printMessage("Please input a command:\n");
      // gather arguments from user
      String[] args = s.nextLine().split("[ \n]+");

      // case: user inputs nothing
      if (args.length == 0) {
        this.printMessage("Please input a command:\n");
      }
      // case: user inputs a command
      else {
        try {
          if (args.length > 1) {
            this.runCommands(args[0], Arrays.copyOfRange(args, 1, args.length));
          } else {
            this.runCommands(args[0]);
          }
        } catch (IllegalArgumentException e) {
          this.printMessage(e.getMessage() + "\n");
        } catch (IllegalStateException ise) {
          this.printMessage(ise.getMessage() + "\n");
        }
      }
    }

    this.printMessage("Thanks for using Collaging!\n");
  }

  /**
   * Runs the given command based on the option
   *
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
          this.model = new CollagePPM();
          this.model.startModel(height, width, this.model.getMax());
        } catch (IllegalArgumentException e) {
          this.printMessage("Invalid input on creating new projects!");
          e.printStackTrace();
        }
        break;
      case "load-project":
        try {
          String filePath = args[0];
          // Open PPM file and pass it to model
          this.model = ImageUtil.readProject(filePath);
        } catch (FileNotFoundException ime) {
          System.out.println("Invalid path or file not found");
        }
        break;
      case "save-project":
        String filePath = args[0];
        // Save the entire model as a project text file
        ImageUtil.writeProject(this.model, filePath);
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
        // get image and layer information
        String layer = args[0];
        String image = args[1];
        int row = Integer.parseInt(args[2]);
        int col = Integer.parseInt(args[3]);

        // Check if layer name exists in project
        try {
          this.model.addImageToLayer(this.model.getLayer(layer),
                  ImageUtil.readPPM(image), row, col);
        } catch (FileNotFoundException e) {
          this.printMessage("Image not found!\n");
        } catch (IllegalArgumentException iae) {
          this.printMessage(iae.getMessage());
        }
        break;
      case "save-image":
        // get file path to image
        filePath = args[0];

        // write a PPM file with the given file path
        ImageUtil.writePPM(this.model.saveImage(), filePath);
        break;
      case "set-filter":
        // get layer name and filter option from user
        String layerName = args[0];
        String filterOption = args[1];

        // change the filter of the given layer name
        this.model.setFilter(layerName, Filter.findByValue(filterOption));
        break;
      default:
        printMessage("Unknown command. Try again.");
        break;
    }
  }

  /**
   * Renders a message to the view with the given message.
   *
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
