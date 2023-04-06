package controller;

import java.io.FileNotFoundException;

import model.CollageModel;
import model.CollagePPM;
import util.ImageUtil;
import view.PPMTextViewImpl;
import view.TextView;

/**
 * A mock class that runs demos for controllers
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
      m.in = ImageUtil.removeComments(filePath);
      m.controller = new CollageControllerImpl(m.model, m.in, m.view);
      m.controller.runCollage();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage() + "\n");
      e.printStackTrace();
    }
  }

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
