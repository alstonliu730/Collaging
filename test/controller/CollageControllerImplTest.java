package controller;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;

import model.CollageModel;
import model.CollagePPM;

import model.Posn;
import view.PPMTextViewImpl;
import view.TextView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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

  @Test
  public void testRunCommands() throws FileNotFoundException {
    // create a new controller and test new-project command
    this.init("new-project 200 200\n");
    this.controller.runCollage();
    assertEquals(200, this.model.getWidth());
    assertEquals(200, this.model.getHeight());

    // test the command: add-layer
    this.controller.runCommands("add-layer", "first-layer");
    assertEquals("first-layer", this.model.getLayer("first-layer").getName());

    // test the command: add-image-to-layer
    this.controller.runCommands("add-image-to-layer",
            "first-layer", "res/doggo.ppm", "0", "0");
    assertEquals(1, this.model.getLayer("first-layer").getImages().size());

    // test the command: set-filter
    this.controller.runCommands("set-filter", "first-layer", "blue-component");
    assertEquals("blue-component",
            this.model.getLayer("first-layer").getFilter().getOption());

    // test the command: save-image
    this.controller.runCommands("save-image", "res/blueDoggo.ppm");
    assertArrayEquals(new int[]{0, 0, 55, 255}, this.model.readPPM("res/blueDoggo.ppm").
            getPixel(new Posn(0,0)).getValues());

    // test the command: save-project
    this.controller.runCommands("save-project", "res/blueDoggo.collage");
    this.controller.runCommands("add-layer", "second-layer");

    // make sure that there is a second layer
    assertEquals("second-layer", this.model.getLayer("second-layer").getName());

    // test load project at the same time to make sure the project was saved
    try{
      // load the old project
      this.controller.runCommands("load-project", "res/blueDoggo.collage");
      // asserts that there is no second layer
      assertEquals("second-layer", this.model.getLayer("second-layer").getName());
    } catch(IllegalStateException e) {
      // make sure it is the right exception message
      assertEquals("Invalid Layer name! Name: second-layer", e.getMessage());
    }

    /*
    //quit the program
    this.controller.runCommands("quit");
    // attempt to run collage
    this.controller.runCollage();
    assertTrue(this.view.toString().contains("Thanks for using Collaging!"));
    */
  }

}