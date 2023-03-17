package controller;

import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.regex.Pattern;

public class ScriptController {

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    // Initialize model
    while (s.hasNext()) {
      String in = s.next();
      switch (in) {
        case "quit":
          return;
        case "new-project":
          try {
            int height = s.nextInt();
            int width = s.nextInt();
            // Create new project with h and w
            // Model should throw IllegalArgumentException for negative ints

          } catch (InputMismatchException ime) {
            System.out.println("Bad width and/or height");
          }

        case "load-project":
          try {
            String file_path = s.next("...ppm*");
            // Open PPM file and pass it to model
          } catch (InputMismatchException ime) {
            System.out.println("Invalid path or file not found");
          }

        case "save-project":
          // Save PPM File through model
        case "add-layer":
          String layer_name = s.next();
          // Add layer to project
        case "add-image-to-layer":
          String layer = s.next();
          String image = s.next("...ppm*");
          // Check if layer name exists in project
          // If not: Print "layer not found" message
          // If so:
      }
    }
  }
}
