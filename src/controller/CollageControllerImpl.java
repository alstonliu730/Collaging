package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import model.CollageModel;
import model.Filter;
import util.ImageUtil;
import view.TextView;

/**
 * A class that represents the controller.
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
   * @throws IllegalArgumentException if any of the given parameters are null
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
    this.printCommands();
    this.printMessage("Please input a command:\n");
    // Loop until the user quits
    while (!quit && s.hasNext()) {

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
   * Runs the given command based on the option.
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
        } catch (FileNotFoundException e) {
          this.printMessage("Invalid path or file not found");
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
        boolean saved = false;
        // write a PPM file with the given file path
        try {
          saved = ImageUtil.writeImage(this.model.saveImage(), filePath);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        if(!saved) {
          this.printMessage("Unsuccessful save. Please try again!");
        }
        break;
      case "set-filter":
        // get layer name and filter option from user
        String layerName = args[0];
        String filterOption = args[1];

        // change the filter of the given layer name
        this.model.setFilter(layerName, Filter.findByValue(filterOption));
        break;
      case "help":
        this.printCommands();
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

  /**
   * Prints the all the available command line and options.
   */
  private void printCommands() {
    String help_message = "";
    help_message += "Usage: command [arg1] [arg2] ... [args]\n";
    help_message += "new-project [width] [height]                            "
            + "Creates a new project with the given dimensions.\n";
    help_message += "load-project [file-path]                                "
            + "Loads an existing project with the given file path.\n";
    help_message += "save-project [file-path]                                "
            + "Saves the current project to the given file path.\n";
    help_message += "save-image [file-path]                                  "
            + "Saves the current project as an image (Format: .ppm) to the given file path.\n";
    help_message += "add-image-to-layer [layer-name] [file-path] [row] [col] "
            + "Gets the given file path to the image and adds the image to the layer at "
            + "a specific location (row, column).\n";
    help_message += "add-layer [layer-name]                                  "
            + "Creates a new layer with the given layer name.\n";
    help_message += "set-filter [layer-name] [filter-name]                   "
            + "Sets the given filter type of the layer associated with the given layer name.\n";
    help_message += "help                                                    "
            + "Prints out all the commands and their usage.\n";
    help_message += "quit                                                    "
            + "Exits the program and will not save any file if user has not saved.\n";
    this.printMessage(help_message);
  }
}
