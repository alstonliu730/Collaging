package controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import model.CollageModel;
import model.CollagePPM;
import view.PPMTextViewImpl;
import view.TextView;

/**
 * A mock class that runs demos for controllers.
 */
public class CollageControllerMock {
  CollageModel model;
  TextView view;
  Readable in;
  Appendable out;
  CollageController controller;

  /**
   * Method to initialize the test parameters.
   */
  public CollageControllerMock() {
    this.out = System.out;
    this.model = new CollagePPM();
    this.view = new PPMTextViewImpl(this.out, this.model);
  }

  /**
   * A helper method that refreshes the fields.
   */
  private CollageControllerMock refresh() {
    return new CollageControllerMock();
  }

  /**
   * Creates and execute the given script file.
   *
   * @param m - the controller mock
   * @param filePath - the file path to the script file
   */
  private static void runScript(CollageControllerMock m, String filePath) {
    try {
      m.refresh();
      m.in = m.removeComments(filePath);
      m.controller = new CollageControllerImpl(m.model, m.in, m.view);
      m.controller.runCollage();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage() + "\n");
      e.printStackTrace();
    }
  }

  private Readable removeComments(String filePath) throws FileNotFoundException {
    if (filePath == null) {
      throw new IllegalArgumentException("Cannot have null argument.");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filePath + " not found!");
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty() && !s.isBlank() && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //Set up a stream of input to export the readable
    InputStream stream = new ByteArrayInputStream(
            builder.toString().getBytes(StandardCharsets.UTF_8));
    return new InputStreamReader(stream);
  }

  /**
   * A main method to execute examples.
   */
  public static void main(String[] args) {
    CollageControllerMock mock = new CollageControllerMock();
    runScript(mock, "res/redSunset.txt");
    runScript(mock, "res/greenSunset.txt");
    runScript(mock, "res/blueSunset.txt");
    runScript(mock, "res/darkenSunset.txt");
    runScript(mock, "res/brightenSunset.txt");
    runScript(mock, "res/invertSunset.txt");
    runScript(mock, "res/multipleLayer.txt");
    runScript(mock, "res/changeLoaded.txt");
  }
}
