package controller;

import org.junit.Test;

import java.io.FileNotFoundException;

import model.CollageModel;
import model.CollagePPM;
import util.ImageUtil;
import view.PPMTextViewImpl;
import view.TextView;

import static org.junit.Assert.*;

public class CollageControllerImplTest {
  CollageModel model;
  TextView view;
  Readable in;
  Appendable out;
  CollageController controller;
  public void init() {
    this.out = System.out;
    this.model = new CollagePPM();
    this.view = new PPMTextViewImpl(this.out, this.model);
  }
  
  @Test
  public void runCollage() {
    try {
      this.in = ImageUtil.removeComments("res/redSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/greenSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/blueSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/darkenSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/brightenSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/invertSunset.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/multipleLayer.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

      this.in = ImageUtil.removeComments("res/changeLoaded.txt");
      init();
      this.controller = new CollageControllerImpl(this.model, this.in, this.view);
      this.controller.runCollage();

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage() + "\n");
      e.printStackTrace();
    }
  }

  @Test
  public void testRunCommands() {

  }

  @Test
  public void testPrintMessage() {
    
  }
}