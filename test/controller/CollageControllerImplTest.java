package controller;

import org.junit.Test;
import java.io.StringReader;

import model.CollageModel;
import model.CollagePPM;

import view.PPMTextViewImpl;
import view.TextView;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the CollageControllerImpl class.
 */
public class CollageControllerImplTest {
  CollageModel model;
  TextView view;
  Readable in;
  Appendable out;
  CollageController controller;

  /**
   * Method to initialize the test parameters.
   *
   * @param input - simulate user input
   */
  private void init(String input) {
    this.in = new StringReader(input);
    this.out = new StringBuilder();
    this.model = new CollagePPM();
    this.view = new PPMTextViewImpl(this.out, this.model);

    this.controller = new CollageControllerImpl(this.model, this.in, this.view);
  }

  @Test
  public void testRunCollage() {
    this.init("new-project 10 10 \nquit");

    // run the collage project
    this.controller.runCollage();

    assertTrue(this.out.toString().contains("Welcome to Collaging!\n"));
  }
}